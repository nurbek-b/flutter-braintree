package com.example.flutter_braintree;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;

import com.google.gson.Gson;
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
    
    private final long creationTimestamp;
    private static final String PREFS_NAME = "FlutterBraintreePayPalHandlerPrefs";
    private static final String PENDING_KEY = "paypal_pending";
    private static final String PENDING_REQUEST_KEY = "paypal_pending_request";
    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    private PayPalClient payPalClient;


    public FlutterBraintreePayPalHandler(FlutterBraintreeCustom activity) {

        Log.d("FlutterBraintreePayPalHandler", "constructed");

        this.activity = activity;

        // Initialize Gson
        this.gson = new Gson();

        // Create PayPalLauncher
        this.payPalLauncher = new PayPalLauncher();

        // Initialize shared preferences
        this.sharedPreferences = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
 
        this.creationTimestamp = System.currentTimeMillis();
    }

    public PayPalClient initializePayPalClient (Intent intent) {

        String authorization = intent.getStringExtra("authorization");
        Map<String, Object> request = (Map<String, Object>) intent.getSerializableExtra("request");

        if (request == null) {
            throw new Exception("request parameters are required for PayPal integration");
        }

        String returnUrl = (String) request.get("returnUrl");
        if (returnUrl == null) {
            throw new Exception("returnUrl is required for PayPal v5 integration");
        }
        
        return new PayPalClient(
            activity,
            authorization,
            Uri.parse(returnUrl)
        );
    }

    public void handleReturnToApp(Intent intent) {
        if (hasPendingRequest()) {

            PayPalPendingRequest.Started  pendingRequest = (PayPalPendingRequest.Started)  getPendingRequestFromPersistentStore();

            Log.d("FlutterBraintreePayPalHandler", "Processing PayPal return with pending request");
            PayPalPaymentAuthResult result = payPalLauncher.handleReturnToApp(pendingRequest, intent);
            
            if (result instanceof PayPalPaymentAuthResult.Success) {
                Log.d("FlutterBraintreePayPalHandler", "PayPal flow completed successfully");
                completePayPalFlow((PayPalPaymentAuthResult.Success) result);
            } else if (result instanceof PayPalPaymentAuthResult.NoResult) {
                Log.d("FlutterBraintreePayPalHandler", "User returned without completing PayPal flow");
                activity.onCancel(); // Notify about cancellation
            } else if (result instanceof PayPalPaymentAuthResult.Failure) {
                Log.e("FlutterBraintreePayPalHandler", "PayPal flow failed", 
                    ((PayPalPaymentAuthResult.Failure) result).getError());
                activity.onError(((PayPalPaymentAuthResult.Failure) result).getError());
            }
            
            clearPendingRequest();
            Log.d("FlutterBraintreePayPalHandler", "Cleared pending request");
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
                PayPalPendingRequest pendingRequest = payPalLauncher.launch(
                    activity, 
                    (PayPalPaymentAuthRequest.ReadyToLaunch) paymentAuthRequest
                );
                if (pendingRequest instanceof PayPalPendingRequest.Started) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(PENDING_KEY, true);
                    editor.apply();
                    Log.d("FlutterBraintreePayPalHandler", "Stored pending request");
                } else if (pendingRequest instanceof PayPalPendingRequest.Failure) {
                    PayPalPendingRequest.Failure failure = (PayPalPendingRequest.Failure) pendingRequest;
                    activity.onError(failure.getError());
                } else {
                    Log.e("FlutterBraintreePayPalHandler", "Unexpected pending request result");
                    activity.onError(new Exception("Unexpected pending request result"));
                }
            } else if (paymentAuthRequest instanceof PayPalPaymentAuthRequest.Failure) {
                PayPalPaymentAuthRequest.Failure failure = 
                    (PayPalPaymentAuthRequest.Failure) paymentAuthRequest;
                activity.onError(failure.getError());
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
        
        return request;
    }


    private PayPalPaymentIntent getPaymentIntent(Intent intent) {
        String paymentIntent = intent.getStringExtra("payPalPaymentIntent");
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
        payPalClient.tokenize(paymentAuthResult, result -> {
            if (result instanceof PayPalResult.Success) {
                PayPalResult.Success success = (PayPalResult.Success) result;
                activity.onPaymentMethodNonceCreated(success.getNonce(), activity.createEmptyBillingAddress());
            } 
            else if (result instanceof PayPalResult.Failure) {
                PayPalResult.Failure failure = (PayPalResult.Failure) result;
                activity.onError(failure.getError());
            }
            else if (result instanceof PayPalResult.Cancel) {
                activity.onCancel();
            }
            clearPendingRequest();
        });
    }

    // Store pending request

    private void storePendingRequest(PayPalPendingRequest request) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(request);
        editor.putString(PENDING_REQUEST_KEY, json);
        editor.apply();
    }

    private PayPalPendingRequest getPendingRequestFromPersistentStore() {
        String json = sharedPreferences.getString(PENDING_REQUEST_KEY, null);
        if (json != null) {
            return gson.fromJson(json, PayPalPendingRequest.class);
        }
        return null;
    }

    private boolean hasPendingRequest() {
        return sharedPreferences.getBoolean(PENDING_KEY, false);
    }

    private void clearPendingRequest() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(PENDING_REQUEST_KEY);
        editor.apply();
    }

}