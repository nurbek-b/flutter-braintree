package com.example.flutter_braintree;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.braintreepayments.api.core.PaymentMethodNonce;
import com.braintreepayments.api.card.CardNonce;
import com.braintreepayments.api.paypal.PayPalAccountNonce;
import com.braintreepayments.api.googlepay.GooglePayCardNonce;
import com.braintreepayments.api.core.PostalAddress;
import com.braintreepayments.api.datacollector.DataCollector;
import com.braintreepayments.api.datacollector.DataCollectorRequest;
import com.braintreepayments.api.datacollector.DataCollectorResult;

import java.util.HashMap;

public class FlutterBraintreeCustom extends AppCompatActivity {

    private Boolean started = false;
    private long creationTimestamp = -1;
    private String deviceData;
    private String authorization;
    
    private FlutterBraintreePayPalHandler payPalHandler;
    private FlutterBraintree3DSHandler treeDSHandler;
    private FlutterBraintreeGooglePayHandler googlePayHandler;
    private DataCollector dataCollector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        creationTimestamp = System.currentTimeMillis();
  
        try {
            Intent intent = getIntent();

            authorization = intent.getStringExtra("authorization");
            // if (authorization == null || authorization.isEmpty()) {
            //     throw new Exception("Authorization is required");
            // }

            String returnUrlScheme = (getPackageName() + ".return.from.braintree").replace("_", "").toLowerCase();
            Log.d("FlutterBraintreeCustom", "returnUrlScheme = " + returnUrlScheme);
            
            String type = intent.getStringExtra("type");
            setContentView(R.layout.activity_flutter_braintree_custom);
            
            // Initialize DataCollector with authorization as per v5 requirements
            dataCollector = new DataCollector(this, authorization);
            DataCollectorRequest request = new DataCollectorRequest(true);
            dataCollector.collectDeviceData(this, request, result -> {
                if (result instanceof DataCollectorResult.Success) {
                    FlutterBraintreeCustom.this.deviceData = ((DataCollectorResult.Success) result).getDeviceData();
                } else if (result instanceof DataCollectorResult.Failure) {
                    Log.e("FlutterBraintreeCustom", "Error collecting device data: " +
                        ((DataCollectorResult.Failure) result).getError().getMessage());
                    deviceData = null;
                }
            });

            if (type.equals("tokenizeCreditCard")) {
                treeDSHandler = new FlutterBraintree3DSHandler(this);
                treeDSHandler.tokenizeCreditCard();
            } else if (type.equals("requestPaypalNonce")) {
                payPalHandler = new FlutterBraintreePayPalHandler(this);
                payPalHandler.requestPaypalNonce(intent);
            } else if (type.equals("startThreeDSecureFlow")) {
                treeDSHandler = new FlutterBraintree3DSHandler(this);
                treeDSHandler.startThreeDSecureFlow();
            } else if (type.equals("startGooglePaymentFlow")) {
                googlePayHandler = new FlutterBraintreeGooglePayHandler(this);
                googlePayHandler.startGooglePaymentFlow(intent);
            } else if (type.equals("checkGooglePayReady")) {
                googlePayHandler = new FlutterBraintreeGooglePayHandler(this);
                googlePayHandler.checkGooglePayReady();
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
        handleReturnToApp(newIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handleReturnToApp(getIntent());
    }

    public void handleReturnToApp(Intent intent) {
        payPalHandler.handleReturnToApp(intent);
    }

    public void onPaymentMethodNonceCreated(PaymentMethodNonce paymentMethodNonce, HashMap<String, String> billingAddress) {
        Log.d("FlutterBraintreeCustom", "onPaymentMethodNonceCreated");
        HashMap<String, Object> nonceMap = new HashMap<String, Object>();
        nonceMap.put("nonce", paymentMethodNonce.getString());
        nonceMap.put("isDefault", paymentMethodNonce.isDefault());
        nonceMap.put("billingInfo", billingAddress);
        nonceMap.put("deviceData", deviceData);
        if (paymentMethodNonce instanceof PayPalAccountNonce) {
            PayPalAccountNonce paypalAccount = (PayPalAccountNonce) paymentMethodNonce;
            nonceMap.put("paypalPayerId", paypalAccount.getPayerId());
            nonceMap.put("typeLabel", "PayPal");
            nonceMap.put("description", paypalAccount.getEmail());
        } else if (paymentMethodNonce instanceof CardNonce) {
            CardNonce card = (CardNonce) paymentMethodNonce;
            nonceMap.put("typeLabel", card.getCardType());
            nonceMap.put("description", "ending in ••" + card.getLastTwo());
        } else if (paymentMethodNonce instanceof GooglePayCardNonce) {
            GooglePayCardNonce googlePayCard = (GooglePayCardNonce) paymentMethodNonce;
            nonceMap.put("cardType", googlePayCard.getCardType());
            nonceMap.put("typeLabel", "GooglePay");
            nonceMap.put("description", googlePayCard.getEmail());
            nonceMap.put("lastTwo", googlePayCard.getLastTwo());
        }
        DataCollectorRequest dataCollectorRequest = new DataCollectorRequest(true); 
        dataCollector.collectDeviceData(this, dataCollectorRequest, result -> {
            if (result instanceof DataCollectorResult.Success) {
                deviceData = ((DataCollectorResult.Success) result).getDeviceData();
                Log.d("FlutterBraintreeCustom", "Device data collected successfully");
            } else if (result instanceof DataCollectorResult.Failure) {
                DataCollectorResult.Failure failure = (DataCollectorResult.Failure) result;
                Log.e("FlutterBraintreeCustom", "Error collecting device data: " + failure.getError().getMessage());
                deviceData = null;
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


    public HashMap<String, String> fillBillingAddressFromNonce(PaymentMethodNonce paymentMethodNonce) {
        HashMap<String, String> billingAddressMap = createEmptyBillingAddress();
        
        if (paymentMethodNonce instanceof GooglePayCardNonce) {
            GooglePayCardNonce googlePayCard = (GooglePayCardNonce) paymentMethodNonce;
            PostalAddress googlePayBillingAddress = googlePayCard.getBillingAddress();
            
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

        if (paymentMethodNonce instanceof PayPalAccountNonce) {
            PayPalAccountNonce paypalAccount = (PayPalAccountNonce) paymentMethodNonce;
            PostalAddress paypalBillingAddress = paypalAccount.getBillingAddress();
            Log.d("FlutterBraintreeCustom", convertPayPalNonceToString(paypalAccount));
                        
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


    public HashMap<String, String> createEmptyBillingAddress() {
        HashMap<String, String> billingAddress = new HashMap<>();
        billingAddress.put("givenName", "");
        billingAddress.put("phoneNumber", "");
        billingAddress.put("streetAddress", "");
        billingAddress.put("extendedAddress", "");
        billingAddress.put("locality", "");
        billingAddress.put("region", "");
        billingAddress.put("postalCode", "");
        billingAddress.put("countryCodeAlpha2", "");
        return billingAddress;
    }

    public String convertPayPalNonceToString(PayPalAccountNonce paypalAccount) {
        if (paypalAccount == null) return "PayPalAccount is null";
        StringBuilder sb = new StringBuilder();
        sb.append("PayPalAccount Details:\n");
        sb.append("Email: ").append(paypalAccount.getEmail()).append("\n");
        sb.append("PayerId: ").append(paypalAccount.getPayerId()).append("\n");
        sb.append("First Name: ").append(paypalAccount.getFirstName()).append("\n");
        sb.append("Last Name: ").append(paypalAccount.getLastName()).append("\n");
        return sb.toString();
    }

    @Override
    protected void onDestroy() {
        Log.d("FlutterBraintreeCustom", "onDestroy");
        super.onDestroy();
        // Ensure proper cleanup
        dataCollector = null;
        payPalHandler = null;
        treeDSHandler = null;
        googlePayHandler = null;
        deviceData = null;
    }
}