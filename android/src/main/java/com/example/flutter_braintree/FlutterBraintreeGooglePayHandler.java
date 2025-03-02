package com.example.flutter_braintree;

import android.content.Intent;
import android.util.Log;
import androidx.annotation.NonNull;

import com.braintreepayments.api.googlepay.GooglePayClient;
import com.braintreepayments.api.googlepay.GooglePayLauncher;
import com.braintreepayments.api.googlepay.GooglePayRequest;
import com.braintreepayments.api.googlepay.GooglePayPaymentAuthRequest;
import com.braintreepayments.api.googlepay.GooglePayResult;
import com.braintreepayments.api.googlepay.GooglePayReadinessResult;
import com.braintreepayments.api.core.PaymentMethodNonce;
import com.braintreepayments.api.core.UserCanceledException;
import com.braintreepayments.api.googlepay.GooglePayTotalPriceStatus;
import com.braintreepayments.api.googlepay.GooglePayBillingAddressFormat;

import com.google.android.gms.wallet.WalletConstants;

public class FlutterBraintreeGooglePayHandler {
    private final FlutterBraintreeCustom activity;
    private final GooglePayClient googlePayClient;
    private final GooglePayLauncher googlePayLauncher;
    

    public FlutterBraintreeGooglePayHandler(FlutterBraintreeCustom activity) {
        Log.d("FlutterBraintreeGooglePayHandler", "FlutterBraintreeGooglePayHandler");

        this.activity = activity;

        String authorization = activity.getIntent().getStringExtra("authorization");

        this.googlePayClient = new GooglePayClient(activity, authorization);
        this.googlePayLauncher = new GooglePayLauncher(activity, paymentAuthResult -> {
            Log.d("FlutterBraintreeGooglePayHandler", "GooglePayLauncher paymentAuthResult = " + paymentAuthResult);
            googlePayClient.tokenize(paymentAuthResult, result -> {
                if (result instanceof GooglePayResult.Success) {
                    Log.d("FlutterBraintreeGooglePayHandler", "GooglePayLauncher result = " + result);
                    GooglePayResult.Success success = (GooglePayResult.Success) result;
                    activity.onPaymentMethodNonceCreated(success.getNonce(), activity.createEmptyBillingAddress());
                } else if (result instanceof GooglePayResult.Failure) {
                    Log.d("FlutterBraintreeGooglePayHandler", "GooglePayLauncher result = " + result);
                    GooglePayResult.Failure failure = (GooglePayResult.Failure) result;
                    activity.onError(failure.getError());
                } else if (result instanceof GooglePayResult.Cancel) {
                    Log.d("FlutterBraintreeGooglePayHandler", "GooglePayLauncher result = " + result);
                    activity.onCancel();
                } else {
                    Log.d("FlutterBraintreeGooglePayHandler", "GooglePayLauncher result = " + result);
                    activity.onError(new Exception("Unexpected Google Pay result type"));
                }
            });
        });
    }

    public void startGooglePaymentFlow(Intent intent) {
        Log.d("FlutterBraintreeGooglePayHandler", "startGooglePaymentFlow");
        try {
            googlePayClient.isReadyToPay(activity, readinessResult -> {
                if (readinessResult instanceof GooglePayReadinessResult.ReadyToPay) {
                    Log.d("FlutterBraintreeGooglePayHandler", "startGooglePaymentFlow Google Pay is ready");
                    GooglePayRequest request = createGooglePayRequest(intent);
                    googlePayClient.createPaymentAuthRequest(request, paymentAuthRequest -> {
                        Log.e("FlutterBraintreeGooglePayHandler", "startGooglePaymentFlow paymentAuthRequest = " + paymentAuthRequest);
                        if (paymentAuthRequest instanceof GooglePayPaymentAuthRequest.ReadyToLaunch) {
                            Log.d("FlutterBraintreeGooglePayHandler", "startGooglePaymentFlow paymentAuthRequest = ReadyToLaunch");
                            googlePayLauncher.launch(
                                (GooglePayPaymentAuthRequest.ReadyToLaunch) paymentAuthRequest
                            );
                        } else if (paymentAuthRequest instanceof GooglePayPaymentAuthRequest.Failure) {
                            Log.d("FlutterBraintreeGooglePayHandler", "startGooglePaymentFlow paymentAuthRequest = Failure");
                            GooglePayPaymentAuthRequest.Failure failure =
                                (GooglePayPaymentAuthRequest.Failure) paymentAuthRequest;
                            activity.onError(failure.getError());
                        } else {
                            Log.d("FlutterBraintreeGooglePayHandler", "startGooglePaymentFlow paymentAuthRequest = Unexpected");
                            activity.onError(new Exception("Unexpected paymentAuthRequest type"));
                        }
                    });
                } else {
                    Log.d("FlutterBraintreeGooglePayHandler", "startGooglePaymentFlow Google Pay is not ready");
                    activity.onError(new Exception("Google Pay is not ready"));
                }
            });
        } catch (Exception e) {
            Log.e("FlutterBraintreeGooglePayHandler", "startGooglePaymentFlow Error in Google Pay flow", e);
            activity.onError(e);
        }
    }

    private GooglePayRequest createGooglePayRequest(Intent intent) {
        Log.d("FlutterBraintreeGooglePayHandler", "createGooglePayRequest");

        String totalPrice = intent.getStringExtra("totalPrice");
        Log.d("FlutterBraintreeGooglePayHandler", "totalPrice = " + totalPrice);

        GooglePayRequest request = new GooglePayRequest(
            "USD", 
            totalPrice, 
            GooglePayTotalPriceStatus.TOTAL_PRICE_STATUS_FINAL
        );

        request.setBillingAddressRequired(true);
        request.setPhoneNumberRequired(true);
        request.setBillingAddressFormat(GooglePayBillingAddressFormat.FULL);

        Log.d("FlutterBraintreeGooglePayHandler", "createGooglePayRequest request = " + request);
        return request;
    }

    public void checkGooglePayReady() {
        Log.d("FlutterBraintreeCustom", "checkGooglePayReady");
        googlePayClient.isReadyToPay(activity, readinessResult -> {
            Intent result = new Intent();
            if (readinessResult instanceof GooglePayReadinessResult.ReadyToPay) {
                Log.d("FlutterBraintreeCustom", "checkGooglePayReady Google Pay is ready");
                result.putExtra("type", "isReadyToPay");
                result.putExtra("isReadyToPay", true);
                activity.setResult(FlutterBraintreeCustom.RESULT_OK, result);
            } else {
                Log.d("FlutterBraintreeCustom", "checkGooglePayReady Google Pay is not ready");
                result.putExtra("error", "Google Pay is not ready");
                activity.setResult(FlutterBraintreeCustom.RESULT_CANCELED, result);
            }
            activity.finish();
        });
    }

    
}