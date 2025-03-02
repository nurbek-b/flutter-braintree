package com.example.flutter_braintree;

import android.content.Intent;
import android.util.Log;
import androidx.annotation.NonNull;

import com.braintreepayments.api.card.Card;
import com.braintreepayments.api.card.CardClient;
import com.braintreepayments.api.card.CardResult;
import com.braintreepayments.api.threedsecure.ThreeDSecureClient;
import com.braintreepayments.api.threedsecure.ThreeDSecureLauncher;
import com.braintreepayments.api.threedsecure.ThreeDSecureRequest;
import com.braintreepayments.api.threedsecure.ThreeDSecureResult;
import com.braintreepayments.api.threedsecure.ThreeDSecurePaymentAuthRequest;
import com.braintreepayments.api.threedsecure.ThreeDSecurePostalAddress;

import java.util.HashMap;

public class FlutterBraintree3DSHandler {

    private final FlutterBraintreeCustom activity;
    private final Intent intent;
    private final ThreeDSecureClient threeDSecureClient;
    private final CardClient cardClient;
    private final ThreeDSecureLauncher threeDSecureLauncher;

    public FlutterBraintree3DSHandler(FlutterBraintreeCustom activity) {
        Log.d("FlutterBraintree3DSHandler", "FlutterBraintree3DSHandler");

        this.activity = activity;
        this.intent = activity.getIntent();
        String authorization = intent.getStringExtra("authorization");
        
        // Initialize clients without BraintreeClient
        this.cardClient = new CardClient(activity, authorization);
        this.threeDSecureClient = new ThreeDSecureClient(activity, authorization);
        
        // Initialize launcher with callback
        this.threeDSecureLauncher = new ThreeDSecureLauncher(activity, paymentAuthResult -> {
            Log.d("FlutterBraintree3DSHandler", "ThreeDSecureLauncher paymentAuthResult = " + paymentAuthResult);
            threeDSecureClient.tokenize(paymentAuthResult, result -> {
                Log.d("FlutterBraintree3DSHandler", "ThreeDSecureLauncher result = " + result);
                if (result instanceof ThreeDSecureResult.Success) {
                    ThreeDSecureResult.Success success = (ThreeDSecureResult.Success) result;
                    activity.onPaymentMethodNonceCreated(success.getNonce(), activity.createEmptyBillingAddress());
                } 
                else if (result instanceof ThreeDSecureResult.Failure) {
                    ThreeDSecureResult.Failure failure = (ThreeDSecureResult.Failure) result;
                    activity.onError(failure.getError());
                }
                else if (result instanceof ThreeDSecureResult.Cancel) {
                    activity.onCancel();
                }
            });
        });
    }

    protected void tokenizeCreditCard() {
        Log.d("FlutterBraintree3DSHandler", "tokenizeCreditCard");

        Card card = new Card();
        card.setExpirationMonth(intent.getStringExtra("expirationMonth"));
        card.setExpirationYear(intent.getStringExtra("expirationYear"));
        card.setCvv(intent.getStringExtra("cvv"));
        card.setCardholderName(intent.getStringExtra("cardholderName"));
        card.setNumber(intent.getStringExtra("cardNumber"));

        cardClient.tokenize(card, (cardResult) -> {
            Log.d("FlutterBraintree3DSHandler", "tokenizeCreditCard cardResult = " + cardResult);
            if (cardResult instanceof CardResult.Success) {
                activity.onPaymentMethodNonceCreated(((CardResult.Success) cardResult).getNonce(), activity.createEmptyBillingAddress());
            } else if (cardResult instanceof CardResult.Failure) {
                activity.onError(((CardResult.Failure) cardResult).getError());
            }
        });
    }

    public void startThreeDSecureFlow() {
        Log.d("FlutterBraintree3DSHandler", "startThreeDSecureFlow");

        ThreeDSecureRequest request = createThreeDSecureRequest(intent);

        threeDSecureClient.createPaymentAuthRequest(activity, request, paymentAuthRequest -> {
            if (paymentAuthRequest instanceof ThreeDSecurePaymentAuthRequest.ReadyToLaunch) {
                Log.d("FlutterBraintree3DSHandler", "startThreeDSecureFlow ReadyToLaunch");
                threeDSecureLauncher.launch(
                    (ThreeDSecurePaymentAuthRequest.ReadyToLaunch) paymentAuthRequest
                );
            }
            else if (paymentAuthRequest instanceof ThreeDSecurePaymentAuthRequest.LaunchNotRequired) {
                Log.d("FlutterBraintree3DSHandler", "startThreeDSecureFlow LaunchNotRequired");
                // No additional authentication needed
                ThreeDSecurePaymentAuthRequest.LaunchNotRequired noAuth = 
                    (ThreeDSecurePaymentAuthRequest.LaunchNotRequired) paymentAuthRequest;
                activity.onPaymentMethodNonceCreated(noAuth.getNonce(), activity.createEmptyBillingAddress());
            }
            else if (paymentAuthRequest instanceof ThreeDSecurePaymentAuthRequest.Failure) {
                Log.d("FlutterBraintree3DSHandler", "startThreeDSecureFlow Failure");
                activity.onError(((ThreeDSecurePaymentAuthRequest.Failure) paymentAuthRequest).getError());
            } else {
                Log.d("FlutterBraintree3DSHandler", "startThreeDSecureFlow Unknown");
                activity.onError(new Exception("startThreeDSecureFlow Unknown"));
            }
        });
    }

    private ThreeDSecureRequest createThreeDSecureRequest(Intent intent) {
        Log.d("FlutterBraintree3DSHandler", "createThreeDSecureRequest");

        // Extract data from intent
        String nonce = intent.getStringExtra("nonce");
        String amount = intent.getStringExtra("amount");
        String email = intent.getStringExtra("email");
        Log.d("FlutterBraintree3DSHandler", "createThreeDSecureRequest nonce = " + nonce);
        Log.d("FlutterBraintree3DSHandler", "createThreeDSecureRequest amount = " + amount);
        Log.d("FlutterBraintree3DSHandler", "createThreeDSecureRequest email = " + email);

        HashMap<String, String> billingAddressMap = (HashMap<String, String>) intent.getSerializableExtra("billingAddress");
        String surname = billingAddressMap.get("surname");
        String givenName = billingAddressMap.get("givenName");
        String phoneNumber = billingAddressMap.get("phoneNumber");
        String streetAddress = billingAddressMap.get("streetAddress");
        String extendedAddress = billingAddressMap.get("extendedAddress");
        String locality = billingAddressMap.get("locality");
        String region = billingAddressMap.get("region");
        String postalCode = billingAddressMap.get("postalCode");
        String countryCodeAlpha2 = billingAddressMap.get("countryCodeAlpha2");

        ThreeDSecurePostalAddress billingAddress = new ThreeDSecurePostalAddress();
        billingAddress.setGivenName(givenName);
        billingAddress.setSurname(surname);
        billingAddress.setPhoneNumber(phoneNumber);
        billingAddress.setStreetAddress(streetAddress);
        billingAddress.setExtendedAddress(extendedAddress);
        billingAddress.setLocality(locality);
        billingAddress.setRegion(region);
        billingAddress.setPostalCode(postalCode);
        billingAddress.setCountryCodeAlpha2(countryCodeAlpha2);

        ThreeDSecureRequest request = new ThreeDSecureRequest();
        request.setNonce(nonce);
        request.setAmount(amount);
        request.setEmail(email);
        request.setBillingAddress(billingAddress);
        request.setChallengeRequested(true);
        request.setDataOnlyRequested(false);

        Log.d("FlutterBraintree3DSHandler", "createThreeDSecureRequest request = " + request);
        
        return request;
    }

}
