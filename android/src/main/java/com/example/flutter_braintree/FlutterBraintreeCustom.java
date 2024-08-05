package com.example.flutter_braintree;

import static com.braintreepayments.api.PayPalCheckoutRequest.USER_ACTION_COMMIT;
import static com.braintreepayments.api.PayPalCheckoutRequest.USER_ACTION_DEFAULT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.braintreepayments.api.ThreeDSecureLookup;

import com.braintreepayments.api.BraintreeClient;
import com.braintreepayments.api.Card;
import com.braintreepayments.api.CardClient;
import com.braintreepayments.api.CardNonce;
import com.braintreepayments.api.CardTokenizeCallback;
import com.braintreepayments.api.PayPalAccountNonce;
import com.braintreepayments.api.PayPalCheckoutRequest;
import com.braintreepayments.api.PayPalClient;
import com.braintreepayments.api.PayPalListener;
import com.braintreepayments.api.PayPalPaymentIntent;
import com.braintreepayments.api.PayPalRequest;
import com.braintreepayments.api.PayPalVaultRequest;
import com.braintreepayments.api.PaymentMethodNonce;
import com.braintreepayments.api.ThreeDSecureV2UiCustomization;
import com.braintreepayments.api.UserCanceledException;

import com.braintreepayments.api.ThreeDSecureClient;
import com.braintreepayments.api.ThreeDSecureRequest;
import com.braintreepayments.api.ThreeDSecureResult;
import com.braintreepayments.api.ThreeDSecureResultCallback;
import com.braintreepayments.api.PaymentMethodNonce;
import com.braintreepayments.api.ThreeDSecureAdditionalInformation;
import com.braintreepayments.api.ThreeDSecurePostalAddress;
import com.braintreepayments.api.ThreeDSecureRequest;

import com.braintreepayments.api.GooglePayClient;
import com.braintreepayments.api.GooglePayRequest;
import com.braintreepayments.api.GooglePayListener;
import com.braintreepayments.api.GooglePayCardNonce;

import com.google.android.gms.wallet.TransactionInfo;
import com.google.android.gms.wallet.WalletConstants;


import java.util.HashMap;
import java.util.Arrays;

public class FlutterBraintreeCustom extends AppCompatActivity implements PayPalListener, GooglePayListener {
    private BraintreeClient braintreeClient;
    private PayPalClient payPalClient;
    private Boolean started = false;
    private long creationTimestamp = -1;
    private GooglePayClient googlePayClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        creationTimestamp = System.currentTimeMillis();

        setContentView(R.layout.activity_flutter_braintree_custom);
        try {
            Intent intent = getIntent();
            String returnUrlScheme = (getPackageName() + ".return.from.braintree").replace("_", "").toLowerCase();
            Log.d("FlutterBraintreeCustom", "returnUrlScheme = " + returnUrlScheme);
            braintreeClient = new BraintreeClient(this, intent.getStringExtra("authorization"), returnUrlScheme);
            String type = intent.getStringExtra("type");
            if (type.equals("tokenizeCreditCard")) {
                tokenizeCreditCard();
            } else if (type.equals("requestPaypalNonce")) {
                payPalClient = new PayPalClient(this, braintreeClient);
                payPalClient.setListener(this);
                requestPaypalNonce();
            } else if (type.equals("startThreeDSecureFlow")) {
                startThreeDSecureFlow();        
            } else if (type.equals("startGooglePaymentFlow")) {
                startGooglePaymentFlow();
            } else {
                throw new Exception("Invalid request type: " + type);
            }
        } catch (Exception e) {
            Intent result = new Intent();
            result.putExtra("error", e);
            setResult(2, result);
            finish();
            return;
        }
    }

    @Override
    protected void onNewIntent(Intent newIntent) {
        super.onNewIntent(newIntent);
        setIntent(newIntent);
    }

    protected void tokenizeCreditCard() {
        Intent intent = getIntent();
        Card card = new Card();
        card.setExpirationMonth(intent.getStringExtra("expirationMonth"));
        card.setExpirationYear(intent.getStringExtra("expirationYear"));
        card.setCvv(intent.getStringExtra("cvv"));
        card.setCardholderName(intent.getStringExtra("cardholderName"));
        card.setNumber(intent.getStringExtra("cardNumber"));


        CardClient cardClient = new CardClient(braintreeClient);
        CardTokenizeCallback callback = (cardNonce, error) -> {
            if (cardNonce != null) {
                onPaymentMethodNonceCreated(cardNonce);
            }
            if (error != null) {
                onError(error);
            }
        };
        cardClient.tokenize(card, callback);
    }

    protected void requestPaypalNonce() {
        Intent intent = getIntent();
        if (intent.getStringExtra("amount") == null) {
            // Vault flow
            PayPalVaultRequest vaultRequest = new PayPalVaultRequest();
            vaultRequest.setDisplayName(intent.getStringExtra("displayName"));
            vaultRequest.setBillingAgreementDescription(intent.getStringExtra("billingAgreementDescription"));
            payPalClient.tokenizePayPalAccount(this, vaultRequest);
        } else {
            // Checkout flow
            PayPalCheckoutRequest checkOutRequest = new PayPalCheckoutRequest(intent.getStringExtra("amount"));
            checkOutRequest.setCurrencyCode(intent.getStringExtra("currencyCode"));
            checkOutRequest.setDisplayName(intent.getStringExtra("displayName"));
            checkOutRequest.setBillingAgreementDescription(intent.getStringExtra("billingAgreementDescription"));

            String userAction;
            switch (intent.getStringExtra("payPalPaymentUserAction")) {
                case "commit":
                    userAction = USER_ACTION_COMMIT;
                    break;
                default:
                    userAction = USER_ACTION_DEFAULT;
            }
            checkOutRequest.setUserAction(userAction);

            String paymentIntent;
            switch (intent.getStringExtra("payPalPaymentIntent")) {
                case "order":
                    paymentIntent = PayPalPaymentIntent.ORDER;
                    break;
                case "sale":
                    paymentIntent = PayPalPaymentIntent.SALE;
                    break;
                default:
                    paymentIntent = PayPalPaymentIntent.AUTHORIZE;
            }
            checkOutRequest.setIntent(paymentIntent);

            payPalClient.tokenizePayPalAccount(this, checkOutRequest);
        }
    }

    public void onPaymentMethodNonceCreated(PaymentMethodNonce paymentMethodNonce) {
        HashMap<String, Object> nonceMap = new HashMap<String, Object>();
        nonceMap.put("nonce", paymentMethodNonce.getString());
        nonceMap.put("isDefault", paymentMethodNonce.isDefault());
        if (paymentMethodNonce instanceof PayPalAccountNonce) {
            PayPalAccountNonce paypalAccountNonce = (PayPalAccountNonce) paymentMethodNonce;
            nonceMap.put("paypalPayerId", paypalAccountNonce.getPayerId());
            nonceMap.put("typeLabel", "PayPal");
            nonceMap.put("description", paypalAccountNonce.getEmail());
        } else if (paymentMethodNonce instanceof CardNonce) {
            CardNonce cardNonce = (CardNonce) paymentMethodNonce;
            nonceMap.put("typeLabel", cardNonce.getCardType());
            nonceMap.put("description", "ending in ••" + cardNonce.getLastTwo());
        } else if (paymentMethodNonce instanceof GooglePayCardNonce) {
            GooglePayCardNonce googlePayCardNonce = (GooglePayCardNonce) paymentMethodNonce;
            nonceMap.put("cardType", googlePayCardNonce.getCardType());
            nonceMap.put("typeLabel", "GooglePay");
            nonceMap.put("description", googlePayCardNonce.getEmail());
            nonceMap.put("lastTwo", googlePayCardNonce.getLastTwo());
        }
        Intent result = new Intent();
        result.putExtra("type", "paymentMethodNonce");
        result.putExtra("paymentMethodNonce", nonceMap);
        setResult(RESULT_OK, result);
        finish();
    }

    public void onCancel() {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void onError(Exception error) {
        Intent result = new Intent();
        result.putExtra("error", error);
        setResult(2, result);
        finish();
    }

    @Override
    public void onPayPalSuccess(@NonNull PayPalAccountNonce payPalAccountNonce) {
        onPaymentMethodNonceCreated(payPalAccountNonce);
    }

    @Override
    public void onPayPalFailure(@NonNull Exception error) {
        if (error instanceof UserCanceledException) {
            if (((UserCanceledException) error).isExplicitCancelation() || System.currentTimeMillis() - creationTimestamp > 500) {
                // PayPal sometimes sends a UserCanceledException early for no reason: filter it out
                // Otherwise take every cancellation event
                onCancel();
            }
        } else {
            onError(error);
        }
    }

    public void startThreeDSecureFlow() {
        Intent intent = getIntent();

        // Extract data from intent
        String nonce = intent.getStringExtra("nonce");
        String amount = intent.getStringExtra("amount");
        String email = intent.getStringExtra("email");

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

        // Create ThreeDSecureRequest and set parameters
        ThreeDSecureRequest request = new ThreeDSecureRequest();
        request.setNonce(nonce);
        request.setAmount(amount);
        request.setEmail(email);

        request.setBillingAddress(billingAddress);

        request.setVersionRequested(ThreeDSecureRequest.VERSION_2);

        // Start ThreeDSecure flow
        ThreeDSecureClient threeDSecureClient = new ThreeDSecureClient(braintreeClient);
      
        threeDSecureClient.performVerification(FlutterBraintreeCustom.this, request, new ThreeDSecureResultCallback() {
            @Override
            public void onResult(@Nullable ThreeDSecureResult threeDSecureResult, @Nullable Exception error) {
                Log.d("FlutterBraintreeCustom", "3D Secure flow completed");

                if (threeDSecureResult != null) {
                    CardNonce cardNonce = threeDSecureResult.getTokenizedCard();
                    ThreeDSecureLookup lookup = threeDSecureResult.getLookup();
                    System.out.println("ThreeDSecureLookup: " +
                            "version: " + lookup.getThreeDSecureVersion() +
                            ", acsUrl: " + lookup.getAcsUrl() +
                            ", md: " + lookup.getMd() +
                            // ", pareq: " + lookup.getPareq() +
                            // ", termUrl: " + lookup.getTermUrl() +
                        "");

                    // optional: inspect the lookup result and prepare UI if a challenge is required
                    threeDSecureClient.continuePerformVerification(FlutterBraintreeCustom.this, request, threeDSecureResult);

                    onPaymentMethodNonceCreated(cardNonce);
                } else {
                    Log.e("FlutterBraintreeCustom", "Error in 3D Secure flow", error);
                    onError(error);
                }
            }
        });

    }


    private void startGooglePaymentFlow() {
        Intent intent = getIntent();
        String totalPrice = intent.getStringExtra("totalPrice");
        
        googlePayClient = new GooglePayClient(this, braintreeClient);
        googlePayClient.setListener(this);

        // ReadyForGooglePayRequest request = new ReadyForGooglePayRequest.Builder()
        //         .setExistingPaymentMethodRequired(true)
        //         .build();

        //    googlePayClient.isReadyToPay(this, request, (isReadyToPay, error) -> {        
            
        googlePayClient.isReadyToPay(this, (isReadyToPay, error) -> {
        if (isReadyToPay) {
            GooglePayRequest googlePayRequest = new GooglePayRequest();
            googlePayRequest.setTransactionInfo(TransactionInfo.newBuilder()
                .setTotalPriceStatus(WalletConstants.TOTAL_PRICE_STATUS_FINAL)
                .setTotalPrice(totalPrice)
                .setCurrencyCode("USD")
                .build());

        googlePayClient.requestPayment(this, googlePayRequest);
            
        }else{
            onError(new Exception("Google Pay is not ready to pay."));
        }
        });


        
    }

    @Override
    public void onGooglePaySuccess(@NonNull PaymentMethodNonce paymentMethodNonce) {
        Log.d("onGooglePaySuccess", "paymentMethodNonce = " + paymentMethodNonce.getString());
        onPaymentMethodNonceCreated(paymentMethodNonce);
    }

    @Override
    public void onGooglePayFailure(@NonNull Exception error) {
        if (error instanceof UserCanceledException) {
            onCancel() ;
        // user canceled
        } else {
        onError(error);
        }
 
    }


}