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
import com.google.android.gms.wallet.WalletConstants.BillingAddressFormat;

import com.braintreepayments.api.DataCollector;
import com.braintreepayments.api.DataCollectorCallback;
import com.braintreepayments.api.PostalAddress;

import java.util.HashMap;
import java.util.Arrays;
import java.util.List;

public class FlutterBraintreeCustom extends AppCompatActivity implements PayPalListener, GooglePayListener {
    private BraintreeClient braintreeClient;
    private PayPalClient payPalClient;
    private Boolean started = false;
    private long creationTimestamp = -1;
    private GooglePayClient googlePayClient;
    private DataCollector dataCollector;
    private String deviceData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        creationTimestamp = System.currentTimeMillis();
  
        try {
            Intent intent = getIntent();
            String returnUrlScheme = (getPackageName() + ".return.from.braintree").replace("_", "").toLowerCase();
            Log.d("FlutterBraintreeCustom", "returnUrlScheme = " + returnUrlScheme);
            braintreeClient = new BraintreeClient(this, intent.getStringExtra("authorization"), returnUrlScheme);
            googlePayClient = new GooglePayClient(this, braintreeClient);
            googlePayClient.setListener(this);              
            
            String type = intent.getStringExtra("type");
            setContentView(R.layout.activity_flutter_braintree_custom);

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
            } else if (type.equals("checkGooglePayReady")) {
                checkGooglePayReady();
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
        Log.d("FlutterBraintreeCustom", "onNewIntent");
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
                onPaymentMethodNonceCreated(cardNonce, createEmptyBillingAddress());
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
            Log.d("FlutterBraintreeCustom", "requestPaypalNonce Checkout flow");
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

    public void onPaymentMethodNonceCreated(PaymentMethodNonce paymentMethodNonce, HashMap<String, String> billingAddress) {
        Log.d("FlutterBraintreeCustom", "onPaymentMethodNonceCreated");
        HashMap<String, Object> nonceMap = new HashMap<String, Object>();
        nonceMap.put("nonce", paymentMethodNonce.getString());
        nonceMap.put("isDefault", paymentMethodNonce.isDefault());
        nonceMap.put("billingInfo", billingAddress);
        nonceMap.put("deviceData", deviceData);
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
         dataCollector = new DataCollector(braintreeClient);
            dataCollector.collectDeviceData(this, new DataCollectorCallback() {
                @Override
                public void onResult(@Nullable String deviceData, @Nullable Exception error) {
                    if (error != null) {
                        Log.e("FlutterBraintreeCustom", "Error collecting device data: " + error.getMessage());
                        deviceData = null;
                    } else {
                        deviceData = deviceData;
                        Log.d("FlutterBraintreeCustom", "Device data collected successfully");
                    }

                    nonceMap.put("deviceData", deviceData);
                    nonceMap.put("billingInfo", billingAddress);                    

                    Intent result = new Intent();
                    result.putExtra("type", "paymentMethodNonce");
                    result.putExtra("paymentMethodNonce", nonceMap);
                    setResult(RESULT_OK, result);
                    finish();
                }
            });

    }

    public void onCancel() {
        Log.d("FlutterBraintreeCustom", "onCancel");
        Intent result = new Intent();
        result.putExtra("error", new Exception("User canceled the operation"));
        setResult(2, result);
        finish();
    }

    public void onError(Exception error) {
        Log.d("FlutterBraintreeCustom", "onError");
        Intent result = new Intent();
        result.putExtra("error", error);
        setResult(2, result);
        finish();
    }

    @Override
    public void onPayPalSuccess(@NonNull PayPalAccountNonce payPalAccountNonce) {
        Log.d("FlutterBraintreeCustom", "onPayPalSuccess payPalAccountNonce = " + payPalAccountNonce.getString());
        onPaymentMethodNonceCreated(payPalAccountNonce, fillBillingAddressFromNonce(payPalAccountNonce));
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
                    Log.d("ThreeDSecureLookup", 
                            "version: " + lookup.getThreeDSecureVersion() +
                            ", acsUrl: " + lookup.getAcsUrl() +
                            ", md: " + lookup.getMd());

                    // optional: inspect the lookup result and prepare UI if a challenge is required
                    threeDSecureClient.continuePerformVerification(FlutterBraintreeCustom.this, request, threeDSecureResult);

                    onPaymentMethodNonceCreated(cardNonce, createEmptyBillingAddress());
                } else {
                    Log.e("FlutterBraintreeCustom", "Error in 3D Secure flow", error);
                    onError(error);
                }
            }
        });

    }


    private void startGooglePaymentFlow() {
        Intent intent = getIntent();
        Log.d("FlutterBraintreeCustom", "startGooglePaymentFlow");
        String totalPrice = intent.getStringExtra("totalPrice");

        Log.d("FlutterBraintreeCustom", "startGooglePaymentFlow totalPrice = " + totalPrice);
        Log.d("FlutterBraintreeCustom", "startGooglePaymentFlow googlePayClient = " + googlePayClient);

        GooglePayRequest googlePayRequest = new GooglePayRequest();
        googlePayRequest.setTransactionInfo(TransactionInfo.newBuilder()
            .setTotalPriceStatus(WalletConstants.TOTAL_PRICE_STATUS_FINAL)
            .setTotalPrice(totalPrice)
            .setCurrencyCode("USD")
            .build());

        googlePayRequest.setBillingAddressRequired(true);
        googlePayRequest.setBillingAddressFormat(WalletConstants.BILLING_ADDRESS_FORMAT_FULL);
        googlePayRequest.setPhoneNumberRequired(true);

        Log.d("FlutterBraintreeCustom", "startGooglePaymentFlow googlePayRequest = " + googlePayRequest);
        Log.d("FlutterBraintreeCustom", "startGooglePaymentFlow lifecycle = " + getLifecycle().getCurrentState());

        try {

            googlePayClient.isReadyToPay(this, (isReadyToPay, error) -> {
                Intent result = new Intent();
                Log.d("FlutterBraintreeCustom", "startGooglePaymentFlow isReadyToPay = " + isReadyToPay);
                if (isReadyToPay) {

                    googlePayClient.requestPayment(this, googlePayRequest);
                }
            });

        } catch (Exception e) {
            Log.e("FlutterBraintreeCustom", "startGooglePaymentFlow Error in Google Pay flow", e);
            onError(e);
        }
            
    };
    

    // New method to check if Google Pay is ready
    public void checkGooglePayReady() {

        Log.d("FlutterBraintreeCustom", "checkGooglePayReady");
        googlePayClient.isReadyToPay(this, (isReadyToPay, error) -> {
            Intent result = new Intent();
            if (isReadyToPay) {
                if (error != null) {
                    result.putExtra("error", error.getMessage());
                    setResult(RESULT_CANCELED, result);
                } else {
                    result.putExtra("type", "isReadyToPay");
                    result.putExtra("isReadyToPay", isReadyToPay);
                    setResult(RESULT_OK, result);                            
                }
            }
            finish();
        });
    }

    @Override
    public void onGooglePaySuccess(@NonNull PaymentMethodNonce paymentMethodNonce) {
        Log.d("FlutterBraintreeCustom", "onGooglePaySuccess paymentMethodNonce = " + paymentMethodNonce.getString());
        onPaymentMethodNonceCreated(paymentMethodNonce, fillBillingAddressFromNonce(paymentMethodNonce));
    }

    @Override
    public void onGooglePayFailure(@NonNull Exception error) {
        Log.e("FlutterBraintreeCustom", "onGooglePayFailure Error in Google Pay flow", error);
        if (error instanceof UserCanceledException) {
            onCancel() ;
        } else {
            onError(error);
        }
    }
    private HashMap<String, String> createEmptyBillingAddress() {
        HashMap<String, String> billingAddressMap = new HashMap<>();
        billingAddressMap.put("givenName", "");
        billingAddressMap.put("surname", "");
        billingAddressMap.put("phoneNumber", "");
        billingAddressMap.put("streetAddress", "");
        billingAddressMap.put("extendedAddress", "");
        billingAddressMap.put("locality", "");
        billingAddressMap.put("region", "");
        billingAddressMap.put("postalCode", "");
        billingAddressMap.put("countryCodeAlpha2", "");
        return billingAddressMap;
    }

    private HashMap<String, String> fillBillingAddressFromNonce(PaymentMethodNonce paymentMethodNonce) {
        HashMap<String, String> billingAddressMap = createEmptyBillingAddress();
        
        if (paymentMethodNonce instanceof GooglePayCardNonce) {
            GooglePayCardNonce googlePayCardNonce = (GooglePayCardNonce) paymentMethodNonce;
            PostalAddress googlePayBillingAddress = googlePayCardNonce.getBillingAddress();
            
            if (googlePayBillingAddress != null) {
                billingAddressMap.put("givenName", googlePayBillingAddress.getRecipientName());
                billingAddressMap.put("phoneNumber", googlePayBillingAddress.getPhoneNumber());
                billingAddressMap.put("streetAddress", googlePayBillingAddress.getStreetAddress());
                billingAddressMap.put("extendedAddress", googlePayBillingAddress.getExtendedAddress());
                billingAddressMap.put("locality", googlePayBillingAddress.getLocality());
                billingAddressMap.put("region", googlePayBillingAddress.getRegion());
                billingAddressMap.put("postalCode", googlePayBillingAddress.getPostalCode());
                billingAddressMap.put("countryCodeAlpha2", googlePayBillingAddress.getCountryCodeAlpha2());
            }
        }

        if (paymentMethodNonce instanceof PayPalAccountNonce){


            
            PayPalAccountNonce paypalNonce = (PayPalAccountNonce) paymentMethodNonce;
            PostalAddress paypalBillingAddress = paypalNonce.getBillingAddress();
            Log.d("FlutterBraintreeCustom", convertPayPalNonceToString(paypalNonce));
                        
            if (paypalBillingAddress != null) {
                billingAddressMap.put("givenName", paypalBillingAddress.getRecipientName());
                billingAddressMap.put("phoneNumber", paypalBillingAddress.getPhoneNumber());
                billingAddressMap.put("streetAddress", paypalBillingAddress.getStreetAddress());
                billingAddressMap.put("extendedAddress", paypalBillingAddress.getExtendedAddress());
                billingAddressMap.put("locality", paypalBillingAddress.getLocality());
                billingAddressMap.put("region", paypalBillingAddress.getRegion());
                billingAddressMap.put("postalCode", paypalBillingAddress.getPostalCode());
                billingAddressMap.put("countryCodeAlpha2", paypalBillingAddress.getCountryCodeAlpha2());
            }
        }
        
        // Log the billing address
        Log.d("FlutterBraintreeCustom", "fillBillingAddressFromNonceMap");
        Log.d("FlutterBraintreeCustom", "Given Name: " + billingAddressMap.get("givenName"));
        Log.d("FlutterBraintreeCustom", "Phone Number: " + billingAddressMap.get("phoneNumber"));
        Log.d("FlutterBraintreeCustom", "Street Address: " + billingAddressMap.get("streetAddress"));
        Log.d("FlutterBraintreeCustom", "Extended Address: " + billingAddressMap.get("extendedAddress"));
        Log.d("FlutterBraintreeCustom", "Locality: " + billingAddressMap.get("locality"));
        Log.d("FlutterBraintreeCustom", "Region: " + billingAddressMap.get("region"));
        Log.d("FlutterBraintreeCustom", "Postal Code: " + billingAddressMap.get("postalCode"));
        Log.d("FlutterBraintreeCustom", "Country Code Alpha2: " + billingAddressMap.get("countryCodeAlpha2"));

        return billingAddressMap;
    }

    @Override
    protected void onDestroy() {
        Log.d("FlutterBraintreeCustom", "onDestroy");
        super.onDestroy();
        // Ensure proper cleanup of the GooglePayClient or any other resources
        googlePayClient = null;
        braintreeClient = null;
        payPalClient = null;
        dataCollector = null;        
    }

    private static String convertPayPalNonceToString(PayPalAccountNonce nonce) {
    return "First Name: " + nonce.getFirstName() + "\n" +
        "Last Name: " + nonce.getLastName() + "\n" +
        "Email: " + nonce.getEmail() + "\n" +
        "Phone: " + nonce.getPhone() + "\n" +
        "Payer ID: " + nonce.getPayerId() + "\n" +
        "Client Metadata ID: " + nonce.getClientMetadataId() + "\n" +
        "Billing Address: " + formatPayPalAddress(nonce.getBillingAddress()) + "\n" +
        "Shipping Address: " + formatPayPalAddress(nonce.getShippingAddress());
    }
    private static String formatPayPalAddress(PostalAddress address) {
        String addressString = "";
        List<String> addresses = Arrays.asList(
                address.getRecipientName(),
                address.getStreetAddress(),
                address.getExtendedAddress(),
                address.getLocality(),
                address.getRegion(),
                address.getPostalCode(),
                address.getCountryCodeAlpha2()
        );

        for (String line : addresses) {
            if (line == null) {
                addressString += "null";
            } else {
                addressString += line;
            }
            addressString += " ";
        }

        return addressString;
    }
}