import android.content.Intent;
import android.util.Log;
import androidx.annotation.NonNull;

import com.braintreepayments.api.GooglePayClient;
import com.braintreepayments.api.GooglePayLauncher;
import com.braintreepayments.api.GooglePayRequest;
import com.braintreepayments.api.GooglePayPaymentAuthRequest;
import com.braintreepayments.api.GooglePayResult;
import com.braintreepayments.api.GooglePayReadinessResult;
import com.braintreepayments.api.WalletConstants;

public class FlutterBraintreeGooglePayHandler {
    private final FlutterBraintreeCustom activity;
    private final GooglePayClient googlePayClient;
    private final GooglePayLauncher googlePayLauncher;
    

    public FlutterBraintreeGooglePayHandler(FlutterBraintreeCustom activity) {
        this.activity = activity;

        String authorization = activity.getIntent().getStringExtra("authorization");

        this.googlePayClient = new GooglePayClient(activity, braintreeClient);
        this.googlePayLauncher = new GooglePayLauncher(activity, paymentAuthResult -> {
            googlePayClient.tokenize(paymentAuthResult, result -> {
                if (result instanceof GooglePayResult.Success) {
                    GooglePayResult.Success success = (GooglePayResult.Success) result;
                    activity.onPaymentMethodNonceCreated(success.getNonce(), activity.createEmptyBillingAddress());
                } else if (result instanceof GooglePayResult.Failure) {
                    GooglePayResult.Failure failure = (GooglePayResult.Failure) result;
                    activity.onError(failure.getError());
                } else if (result instanceof GooglePayResult.Cancel) {
                    activity.onCancel();
                }
            });
        });
    }

    public void startGooglePaymentFlow(Intent intent) {

        Log.d("FlutterBraintreeGooglePayHandler", "startGooglePaymentFlow");
        GooglePayRequest googlePayRequest = createGooglePayRequest(intent);

        try {
            googlePayClient.isReadyToPay(activity, readinessResult -> {
                Log.e("FlutterBraintreeGooglePayHandler", "startGooglePaymentFlow readinessResult = " + readinessResult);
                if (readinessResult instanceof GooglePayReadinessResult.ReadyToPay) {
                    googlePayClient.createPaymentAuthRequest(request, paymentAuthRequest -> {
                        Log.e("FlutterBraintreeGooglePayHandler", "startGooglePaymentFlow paymentAuthRequest = " + paymentAuthRequest);
                        if (paymentAuthRequest instanceof GooglePayPaymentAuthRequest.ReadyToLaunch) {
                            googlePayLauncher.launch(
                                (GooglePayPaymentAuthRequest.ReadyToLaunch) paymentAuthRequest
                            );
                        } else if (paymentAuthRequest instanceof GooglePayPaymentAuthRequest.Failure) {
                            GooglePayPaymentAuthRequest.Failure failure = 
                                (GooglePayPaymentAuthRequest.Failure) paymentAuthRequest;
                            activity.onError(failure.getError());
                        } else {
                            activity.onError(new Exception("Unexpected paymentAuthRequest type"));
                        }
                    });
                } else {
                    activity.onError(new Exception("Google Pay is not ready"));
                }
            });
        } catch (Exception e) {
            Log.e("FlutterBraintreeGooglePayHandler", "startGooglePaymentFlow Error in Google Pay flow", e);
            activity.onError(e);
        }
    }

    private GooglePayRequest createGooglePayRequest(Intent intent) {
        String totalPrice = intent.getStringExtra("totalPrice");
        Log.d("FlutterBraintreeGooglePayHandler", "totalPrice = " + totalPrice);

        GooglePayRequest request = new GooglePayRequest();
        request.setCurrencyCode("USD");
        request.setTotalPrice(totalPrice);
        request.setTotalPriceStatus(WalletConstants.TOTAL_PRICE_STATUS_FINAL);

        request.setBillingAddressRequired(true);
        request.setPhoneNumberRequired(true);
        request.setBillingAddressFormat(GooglePayRequest.BILLING_ADDRESS_FORMAT_FULL);

        return request;
    }

    public void checkGooglePayReady() {
        Log.d("FlutterBraintreeCustom", "checkGooglePayReady");
        googlePayClient.isReadyToPay(activity, readinessResult -> {
            Intent result = new Intent();
            if (readinessResult instanceof GooglePayReadinessResult.ReadyToPay) {
                // Success case - ready to pay
                result.putExtra("type", "isReadyToPay");
                result.putExtra("isReadyToPay", true);
                activity.setResult(FlutterBraintreeCustom.RESULT_OK, result);
            } else {
                // Not ready case
                result.putExtra("error", "Google Pay is not ready");
                activity.setResult(FlutterBraintreeCustom.RESULT_CANCELED, result);
            }
            activity.finish();
        });
    }

    @Override
    public void onGooglePaySuccess(@NonNull PaymentMethodNonce paymentMethodNonce) {
        Log.d("FlutterBraintreeCustom", "onGooglePaySuccess paymentMethodNonce = " + paymentMethodNonce.getString());
        
        activity.onPaymentMethodNonceCreated(paymentMethodNonce, activity.fillBillingAddressFromNonce(paymentMethodNonce));
    }

    @Override
    public void onGooglePayFailure(@NonNull Exception error) {
                Log.e("FlutterBraintreeCustom", "onGooglePayFailure Error in Google Pay flow", error);
        if (error instanceof UserCanceledException) {
            activity.onCancel();
        } else {
            activity.onError(error);
        }
    }
    
}