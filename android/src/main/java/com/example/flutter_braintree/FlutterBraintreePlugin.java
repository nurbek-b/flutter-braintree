package com.example.flutter_braintree;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.Map;
import java.util.HashMap;
import android.util.Log;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import io.flutter.plugin.common.PluginRegistry.ActivityResultListener;

public class FlutterBraintreePlugin implements FlutterPlugin, ActivityAware, MethodCallHandler, ActivityResultListener {
    private static final int CUSTOM_ACTIVITY_REQUEST_CODE = 0x420;

    private Activity activity;
    private Result activeResult;
    private String currentMethod = "";

    public static void registerWith(Registrar registrar) {
        Log.d("FlutterBraintreePlugin", "registerWith called");
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_braintree.custom");
        FlutterBraintreePlugin plugin = new FlutterBraintreePlugin();
        plugin.activity = registrar.activity();
        registrar.addActivityResultListener(plugin);
        channel.setMethodCallHandler(plugin);
    }

    @Override
    public void onAttachedToEngine(FlutterPluginBinding binding) {
        Log.d("FlutterBraintreePlugin", "onAttachedToEngine called");
        final MethodChannel channel = new MethodChannel(binding.getBinaryMessenger(), "flutter_braintree.custom");
        channel.setMethodCallHandler(this);
    }

    @Override
    public void onDetachedFromEngine(FlutterPluginBinding binding) {
        Log.d("FlutterBraintreePlugin", "onDetachedFromEngine called");
    }

    @Override
    public void onAttachedToActivity(ActivityPluginBinding binding) {
        Log.d("FlutterBraintreePlugin", "onAttachedToActivity called");
        activity = binding.getActivity();
        binding.addActivityResultListener(this);
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {
        Log.d("FlutterBraintreePlugin", "onDetachedFromActivityForConfigChanges called");
        activity = null;
    }

    @Override
    public void onReattachedToActivityForConfigChanges(ActivityPluginBinding binding) {
        Log.d("FlutterBraintreePlugin", "onReattachedToActivityForConfigChanges called");
        activity = binding.getActivity();
        binding.addActivityResultListener(this);
    }

    @Override
    public void onDetachedFromActivity() {
        Log.d("FlutterBraintreePlugin", "onDetachedFromActivity called");
        activity = null;
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        Log.d("FlutterBraintreePlugin", "onMethodCall called with method: " + call.method);
        if (activeResult != null) {
            result.error("already_running", "Cannot launch another custom activity while one is already running.", null);
            return;
        }
        activeResult = result;
        currentMethod = call.method; 

        switch (call.method) {
            case "tokenizeCreditCard":
                Intent tokenizeCreditCardIntent = new Intent(activity, FlutterBraintreeCustom.class);
                tokenizeCreditCardIntent.putExtra("type", "tokenizeCreditCard");
                tokenizeCreditCardIntent.putExtra("authorization", (String) call.argument("authorization"));
                assert (call.argument("request") instanceof Map);
                Map tokenizeCreditCardRequest = (Map) call.argument("request");
                tokenizeCreditCardIntent.putExtra("cardNumber", (String) tokenizeCreditCardRequest.get("cardNumber"));
                tokenizeCreditCardIntent.putExtra("expirationMonth", (String) tokenizeCreditCardRequest.get("expirationMonth"));
                tokenizeCreditCardIntent.putExtra("expirationYear", (String) tokenizeCreditCardRequest.get("expirationYear"));
                tokenizeCreditCardIntent.putExtra("cvv", (String) tokenizeCreditCardRequest.get("cvv"));
                tokenizeCreditCardIntent.putExtra("cardholderName", (String) tokenizeCreditCardRequest.get("cardholderName"));
                activity.startActivityForResult(tokenizeCreditCardIntent, CUSTOM_ACTIVITY_REQUEST_CODE);
                break;
            case "requestPaypalNonce":
                Intent requestPaypalNonceIntent = new Intent(activity, FlutterBraintreeCustom.class);
                requestPaypalNonceIntent.putExtra("type", "requestPaypalNonce");
                requestPaypalNonceIntent.putExtra("authorization", (String) call.argument("authorization"));
                assert (call.argument("request") instanceof Map);
                Map requestPaypalNonceRequest = (Map) call.argument("request");
                requestPaypalNonceIntent.putExtra("amount", (String) requestPaypalNonceRequest.get("amount"));
                requestPaypalNonceIntent.putExtra("currencyCode", (String) requestPaypalNonceRequest.get("currencyCode"));
                requestPaypalNonceIntent.putExtra("displayName", (String) requestPaypalNonceRequest.get("displayName"));
                requestPaypalNonceIntent.putExtra("payPalPaymentIntent", (String) requestPaypalNonceRequest.get("payPalPaymentIntent"));
                requestPaypalNonceIntent.putExtra("payPalPaymentUserAction", (String) requestPaypalNonceRequest.get("payPalPaymentUserAction"));
                requestPaypalNonceIntent.putExtra("billingAgreementDescription", (String) requestPaypalNonceRequest.get("billingAgreementDescription"));
                requestPaypalNonceIntent.putExtra("returnUrl", (String) requestPaypalNonceRequest.get("returnUrl"));
                activity.startActivityForResult(requestPaypalNonceIntent, CUSTOM_ACTIVITY_REQUEST_CODE);
                break;
            case "startThreeDSecureFlow":
                Intent startThreeDSecureFlowIntent = new Intent(activity, FlutterBraintreeCustom.class);
                startThreeDSecureFlowIntent.putExtra("type", "startThreeDSecureFlow");
                startThreeDSecureFlowIntent.putExtra("authorization", (String) call.argument("authorization"));
                assert (call.argument("request") instanceof Map);
                Map startThreeDSecureFlowRequest = (Map) call.argument("request");
                startThreeDSecureFlowIntent.putExtra("nonce", (String) startThreeDSecureFlowRequest.get("nonce"));
                startThreeDSecureFlowIntent.putExtra("amount", (String) startThreeDSecureFlowRequest.get("amount"));
                startThreeDSecureFlowIntent.putExtra("email", (String) startThreeDSecureFlowRequest.get("email"));
                startThreeDSecureFlowIntent.putExtra("surname", (String) startThreeDSecureFlowRequest.get("surname"));
                startThreeDSecureFlowIntent.putExtra("givenName", (String) startThreeDSecureFlowRequest.get("givenName"));
                Map billingAddress = (Map) startThreeDSecureFlowRequest.get("billingAddress");
                startThreeDSecureFlowIntent.putExtra("billingAddress", new HashMap<>(billingAddress));
                activity.startActivityForResult(startThreeDSecureFlowIntent, CUSTOM_ACTIVITY_REQUEST_CODE);
                break;
            case "startGooglePaymentFlow":
                Intent startGooglePaymentFlowIntent = new Intent(activity, FlutterBraintreeCustom.class);
                startGooglePaymentFlowIntent.putExtra("type", "startGooglePaymentFlow");
                startGooglePaymentFlowIntent.putExtra("authorization", (String) call.argument("authorization"));
                assert (call.argument("request") instanceof Map);
                Map startGooglePaymentFlowRequest = (Map) call.argument("request");
                startGooglePaymentFlowIntent.putExtra("totalPrice", (String) startGooglePaymentFlowRequest.get("totalPrice"));
                activity.startActivityForResult(startGooglePaymentFlowIntent, CUSTOM_ACTIVITY_REQUEST_CODE);
                break;
            case "checkGooglePayReady":
                Intent checkGooglePayReadyIntent = new Intent(activity, FlutterBraintreeCustom.class);
                checkGooglePayReadyIntent.putExtra("type", "checkGooglePayReady");
                checkGooglePayReadyIntent.putExtra("authorization", (String) call.argument("authorization"));
                activity.startActivityForResult(checkGooglePayReadyIntent, CUSTOM_ACTIVITY_REQUEST_CODE);
                break;
            default:
                result.notImplemented();
                activeResult = null;
                break;
        }
    }

    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (currentMethod == null) {
            currentMethod = "undefined";
        }

        Log.d("FlutterBraintreePlugin", "onActivityResult called with requestCode: " + requestCode + ", resultCode: " + resultCode);

        if (activeResult == null) {
            Log.w("FlutterBraintreePlugin", "activeResult is null, cannot handle activity result");
            return false;
        }

        if (data == null) {
            Log.w("FlutterBraintreePlugin", "Intent data is null");
            activeResult.error("error", "Intent data is null in method: " + currentMethod, null);
            activeResult = null;
            return true;
        }

        switch (requestCode) {
            case CUSTOM_ACTIVITY_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    String type = data.getStringExtra("type");
                    if (type == null) {
                        Log.w("FlutterBraintreePlugin", "Type is null in Intent data");
                        activeResult.error("error", "Type is null in method: " + currentMethod, null);
                    } else if (type.equals("paymentMethodNonce")) {
                        Log.d("FlutterBraintreePlugin", "Received paymentMethodNonce");
                        activeResult.success(data.getSerializableExtra("paymentMethodNonce"));
                    } else if (type.equals("isReadyToPay")) {
                        Log.d("FlutterBraintreePlugin", "Received isReadyToPay");
                        activeResult.success(data.getBooleanExtra("isReadyToPay", false));
                    } else {
                        Log.w("FlutterBraintreePlugin", "Invalid activity result type: " + type);
                        Exception error = new Exception("Invalid activity result type.");
                        activeResult.error("error", error.getMessage() + " in method: " + currentMethod, null);
                    }
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    String error = data.getStringExtra("error");
                    if (error != null) {
                        Log.w("FlutterBraintreePlugin", "Activity canceled with error: " + error);
                        activeResult.error("error", error + " in method: " + currentMethod, null);
                    } else {
                        Log.d("FlutterBraintreePlugin", "Activity canceled without error");
                        activeResult.success(null);
                    }
                } else {
                    Exception error = (Exception) data.getSerializableExtra("error");
                    if (error != null) {
                        Log.w("FlutterBraintreePlugin", "Activity failed with error: " + error.getMessage());
                        activeResult.error("error", error.getMessage() + " in method: " + currentMethod, null);
                    } else {
                        Log.w("FlutterBraintreePlugin", "Activity failed with unknown error");
                        activeResult.error("error", "Unknown error in method: " + currentMethod, null);
                    }
                }
                activeResult = null;
                return true;
            default:
                Log.w("FlutterBraintreePlugin", "Unhandled requestCode: " + requestCode);
                return false;
        }
    }
}
