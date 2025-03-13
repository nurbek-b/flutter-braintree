package com.example.flutter_braintree;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;

import java.util.Map;

import com.braintreepayments.api.paypal.PayPalClient;
import com.braintreepayments.api.paypal.PayPalLauncher;
import com.braintreepayments.api.paypal.PayPalRequest;
import com.braintreepayments.api.paypal.PayPalVaultRequest;
import com.braintreepayments.api.paypal.PayPalCheckoutRequest;
import com.braintreepayments.api.paypal.PayPalPaymentIntent;
import com.braintreepayments.api.paypal.PayPalPaymentAuthRequest;
import com.braintreepayments.api.paypal.PayPalPendingRequest;
import com.braintreepayments.api.paypal.PayPalPaymentAuthResult;
import com.braintreepayments.api.paypal.PayPalResult;
import com.braintreepayments.api.paypal.PayPalPaymentUserAction;


public class FlutterBraintreePayPalHandler {

    private final FlutterBraintreeCustom activity;
    private final PayPalLauncher payPalLauncher;
    
    private static final String PREFS_NAME = "FlutterBraintreePayPalHandlerPrefs";
    private static final String PENDING_KEY = "paypal_pending";
    private static final String PENDING_REQUEST_KEY = "paypal_pending_request_string";
    private final SharedPreferences sharedPreferences;
    private PayPalClient payPalClient;

    public FlutterBraintreePayPalHandler(FlutterBraintreeCustom activity) {
        Log.d("FlutterBraintreePayPalHandler", "constructed");
        this.activity = activity;
        this.payPalLauncher = new PayPalLauncher();
        this.sharedPreferences = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public PayPalClient initializePayPalClient (Intent intent) {

        Log.d("FlutterBraintreePayPalHandler", "initializePayPalClient");
        Log.d("FlutterBraintreePayPalHandler", "Extracting authorization and returnUrl from intent");

        String authorization = intent.getStringExtra("authorization");
        String returnUrl =  intent.getStringExtra("returnUrl");

        Log.d("FlutterBraintreePayPalHandler", "Initializing PayPalClient with authorization: " + authorization + " and returnUrl: " + returnUrl);    
        return new PayPalClient(
            activity,
            authorization,
            Uri.parse(returnUrl)
        );

    }

    public void handleReturnToApp(Intent intent) {
        Log.d("FlutterBraintreePayPalHandler", "handleReturnToApp");

        if (!hasPendingRequest()) {
            Log.d("FlutterBraintreePayPalHandler", "No pending request");
            activity.onCancel();
            return;
        }

        PayPalPendingRequest.Started pendingRequest = getPendingRequestFromPersistentStore();
        clearPendingRequest();
        if (pendingRequest == null) {
            Log.e("FlutterBraintreePayPalHandler", "Could not restore pending request");
            activity.onError(new Exception("Could not restore pending request"));
            return;
        }
        Log.d("FlutterBraintreePayPalHandler", "Pending request restored" + pendingRequest.getPendingRequestString());

        PayPalPaymentAuthResult result = payPalLauncher.handleReturnToApp(intent);
        
        if (result instanceof PayPalPaymentAuthResult.Success) {
            Log.d("FlutterBraintreePayPalHandler", "PayPal flow completed successfully");
            completePayPalFlow((PayPalPaymentAuthResult.Success) result);
        } else if (result instanceof PayPalPaymentAuthResult.NoResult) {
            Log.d("FlutterBraintreePayPalHandler", "User returned without completing PayPal flow");
            activity.onCancel();
        } else if (result instanceof PayPalPaymentAuthResult.Failure) {
            Log.e("FlutterBraintreePayPalHandler", "PayPal flow failed",
                ((PayPalPaymentAuthResult.Failure) result).getError());
            activity.onError(((PayPalPaymentAuthResult.Failure) result).getError());
        } else {
            Log.e("FlutterBraintreePayPalHandler", "Unexpected PayPal flow result");
            activity.onError(new Exception("Unexpected PayPal flow result"));
        }
        
    }

    public void requestPaypalNonce(Intent intent) {
        Log.d("FlutterBraintreePayPalHandler", "requestPaypalNonce");

        if (intent == null) {
            activity.onError(new Exception("Intent cannot be null"));
            return;
        }

        this.payPalClient = initializePayPalClient(intent);
        PayPalRequest request = createPayPalRequest(intent);

        payPalClient.createPaymentAuthRequest(activity, request, paymentAuthRequest -> {
            if (paymentAuthRequest instanceof PayPalPaymentAuthRequest.ReadyToLaunch) {
                Log.d("FlutterBraintreePayPalHandler", "Ready to launch PayPal flow");
                PayPalPendingRequest result = payPalLauncher.launch(
                    activity,
                    (PayPalPaymentAuthRequest.ReadyToLaunch) paymentAuthRequest
                );
                
                if (result instanceof PayPalPendingRequest.Started) {
                    Log.d("FlutterBraintreePayPalHandler", "PayPal flow started");
                    storePendingRequest((PayPalPendingRequest.Started) result);
                } else if (result instanceof PayPalPendingRequest.Failure) {
                    Log.e("FlutterBraintreePayPalHandler", "PayPal flow failed",
                        ((PayPalPendingRequest.Failure) result).getError());
                    activity.onError(((PayPalPendingRequest.Failure) result).getError());
                } else {
                    Log.e("FlutterBraintreePayPalHandler", "Unexpected pending request result");
                    activity.onError(new Exception("Unexpected pending request result"));
                }
            } else if (paymentAuthRequest instanceof PayPalPaymentAuthRequest.Failure) {
                Log.e("FlutterBraintreePayPalHandler", "PayPal flow failed",
                    ((PayPalPaymentAuthRequest.Failure) paymentAuthRequest).getError());
                activity.onError(((PayPalPaymentAuthRequest.Failure) paymentAuthRequest).getError());
            } else {
                Log.e("FlutterBraintreePayPalHandler", "Unexpected payment auth request result");
                activity.onError(new Exception("Unexpected payment auth request result"));
            }
        });
    }

    private PayPalRequest createPayPalRequest(Intent intent) {
        String amount = intent.getStringExtra("amount");
        Log.d("FlutterBraintreePayPalHandler", "amount: " + amount);

        if (amount == null) {
            return createVaultRequest(intent);
        } else {
            return createCheckoutRequest(intent);
        }
    }

    private PayPalVaultRequest createVaultRequest(Intent intent) {
        Log.d("FlutterBraintreePayPalHandler", "Creating Vault flow request");
        
        String displayName = intent.getStringExtra("displayName");
        String billingAgreementDescription = intent.getStringExtra("billingAgreementDescription");
        
        Log.d("FlutterBraintreePayPalHandler", "displayName: " + displayName);
        Log.d("FlutterBraintreePayPalHandler", "billingAgreementDescription: " + billingAgreementDescription);

        PayPalVaultRequest request = new PayPalVaultRequest(false);
        request.setDisplayName(displayName);
        request.setBillingAgreementDescription(billingAgreementDescription);

        Log.d("FlutterBraintreePayPalHandler", "Created Vault flow request = " + request);
        return request;
    }

    private PayPalCheckoutRequest createCheckoutRequest(Intent intent) {
        Log.d("FlutterBraintreePayPalHandler", "Creating Checkout flow request");
        
        String amount = intent.getStringExtra("amount");
        String currencyCode = intent.getStringExtra("currencyCode");
        String displayName = intent.getStringExtra("displayName");
        String billingAgreementDescription = intent.getStringExtra("billingAgreementDescription");

        Log.d("FlutterBraintreePayPalHandler", "displayName: " + displayName);
        Log.d("FlutterBraintreePayPalHandler", "billingAgreementDescription: " + billingAgreementDescription);
        Log.d("FlutterBraintreePayPalHandler", "currencyCode: " + currencyCode);

        PayPalCheckoutRequest request = new PayPalCheckoutRequest(amount, false);

        request.setIntent(getPaymentIntent(intent));
        request.setCurrencyCode(currencyCode);
        request.setDisplayName(displayName);
        request.setBillingAgreementDescription(billingAgreementDescription);
        
        Log.d("FlutterBraintreePayPalHandler", "Created Checkout flow request = " + request);
        return request;
    }


    private PayPalPaymentIntent getPaymentIntent(Intent intent) {
        Log.d("FlutterBraintreePayPalHandler", "getPaymentIntent");

        String paymentIntent = intent.getStringExtra("payPalPaymentIntent");
        Log.d("FlutterBraintreePayPalHandler", "paymentIntent: " + paymentIntent);
        switch (paymentIntent) {
            case "order":
                return PayPalPaymentIntent.ORDER;
            case "sale":
                return PayPalPaymentIntent.SALE;
            default:
                return PayPalPaymentIntent.AUTHORIZE;
        }
    }

    private void completePayPalFlow(PayPalPaymentAuthResult.Success paymentAuthResult) {
        Log.d("FlutterBraintreePayPalHandler", "completePayPalFlow");

        payPalClient.tokenize(paymentAuthResult, result -> {
            if (result instanceof PayPalResult.Success) {
                Log.d("FlutterBraintreePayPalHandler", "PayPal flow completed successfully");
                PayPalResult.Success success = (PayPalResult.Success) result;
                activity.onPaymentMethodNonceCreated(success.getNonce(), activity.createEmptyBillingAddress());
            } else if (result instanceof PayPalResult.Failure) {
                Log.e("FlutterBraintreePayPalHandler", "PayPal flow failed", 
                    ((PayPalResult.Failure) result).getError());
                PayPalResult.Failure failure = (PayPalResult.Failure) result;
                activity.onError(failure.getError());
            } else if (result instanceof PayPalResult.Cancel) {
                Log.d("FlutterBraintreePayPalHandler", "User cancelled PayPal flow");
                activity.onCancel();
            } else {
                Log.e("FlutterBraintreePayPalHandler", "Unexpected PayPal flow result");
                activity.onError(new Exception("Unexpected PayPal flow result"));
            }
            clearPendingRequest();
        });
    }

    private void storePendingRequest(PayPalPendingRequest.Started request) {
        Log.d("FlutterBraintreePayPalHandler", "storePendingRequest");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PENDING_REQUEST_KEY, request.getPendingRequestString());
        editor.putBoolean(PENDING_KEY, true);
        editor.apply();
        Log.d("FlutterBraintreePayPalHandler", "storePendingRequest success");
    }

    private PayPalPendingRequest.Started getPendingRequestFromPersistentStore() {
        Log.d("FlutterBraintreePayPalHandler", "getPendingRequestFromPersistentStore");
        String pendingRequestString = sharedPreferences.getString(PENDING_REQUEST_KEY, null);
        if (pendingRequestString != null) {
            return PayPalPendingRequest.Started.fromString(pendingRequestString);
        }
        Log.e("FlutterBraintreePayPalHandler", "No pending request found in persistent store");
        return null;
    }

    private boolean hasPendingRequest() {
        Log.d("FlutterBraintreePayPalHandler", "hasPendingRequest");
        return sharedPreferences.getBoolean(PENDING_KEY, false);
    }

    private void clearPendingRequest() {
        Log.d("FlutterBraintreePayPalHandler", "clearPendingRequest");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(PENDING_REQUEST_KEY);
        editor.remove(PENDING_KEY);
        editor.apply();
    }

}