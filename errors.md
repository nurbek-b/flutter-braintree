Launching lib/main.dart on sdk gphone64 arm64 in debug mode...
/Users/airm1/work/overseas/flutter-braintree/android/src/main/java/com/example/flutter_braintree/FlutterBraintreeCustom.java:137: error: incompatible types: incompatible parameter types in lambda expression
        dataCollector.collectDeviceData(this, dataCollectorRequest, (@Nullable String collectedDeviceData, @Nullable Exception error) -> {
                                                                    ^
/Users/airm1/work/overseas/flutter-braintree/android/src/main/java/com/example/flutter_braintree/FlutterBraintreePayPalHandler.java:84: error: incompatible types: PayPalPendingRequest cannot be converted to Started
            PayPalPaymentAuthResult result = payPalLauncher.handleReturnToApp(pendingRequest, intent);
                                                                              ^
/Users/airm1/work/overseas/flutter-braintree/android/src/main/java/com/example/flutter_braintree/FlutterBraintreePayPalHandler.java:116: error: variable request is already defined in method requestPaypalNonce(Intent)
        PayPalRequest request = createPayPalRequest(intent);
                      ^
/Users/airm1/work/overseas/flutter-braintree/android/src/main/java/com/example/flutter_braintree/FlutterBraintreePayPalHandler.java:190: error: cannot find symbol
        request.setMerchantName(displayName);
               ^
  symbol:   method setMerchantName(String)
  location: variable request of type PayPalCheckoutRequest
/Users/airm1/work/overseas/flutter-braintree/android/src/main/java/com/example/flutter_braintree/FlutterBraintreePayPalHandler.java:193: error: cannot find symbol
        request.setPaymentIntent(getPaymentIntent(intent));
               ^
  symbol:   method setPaymentIntent(String)
  location: variable request of type PayPalCheckoutRequest
/Users/airm1/work/overseas/flutter-braintree/android/src/main/java/com/example/flutter_braintree/FlutterBraintreePayPalHandler.java:203: error: incompatible types: PayPalPaymentIntent cannot be converted to String
                return PayPalPaymentIntent.ORDER;
                                          ^
/Users/airm1/work/overseas/flutter-braintree/android/src/main/java/com/example/flutter_braintree/FlutterBraintreePayPalHandler.java:205: error: incompatible types: PayPalPaymentIntent cannot be converted to String
                return PayPalPaymentIntent.SALE;
                                          ^
/Users/airm1/work/overseas/flutter-braintree/android/src/main/java/com/example/flutter_braintree/FlutterBraintreePayPalHandler.java:207: error: incompatible types: PayPalPaymentIntent cannot be converted to String
                return PayPalPaymentIntent.AUTHORIZE;
                                          ^
/Users/airm1/work/overseas/flutter-braintree/android/src/main/java/com/example/flutter_braintree/FlutterBraintree3DSHandler.java:65: error: package CardResult does not exist
            if (cardResult instanceof CardResult.Success) {
                                                ^
/Users/airm1/work/overseas/flutter-braintree/android/src/main/java/com/example/flutter_braintree/FlutterBraintree3DSHandler.java:66: error: package CardResult does not exist
                activity.onPaymentMethodNonceCreated(((CardResult.Success) cardResult).getNonce(), activity.createEmptyBillingAddress());
                                                                 ^
/Users/airm1/work/overseas/flutter-braintree/android/src/main/java/com/example/flutter_braintree/FlutterBraintree3DSHandler.java:67: error: package CardResult does not exist
            } else if (cardResult instanceof CardResult.Failure) {
                                                       ^
/Users/airm1/work/overseas/flutter-braintree/android/src/main/java/com/example/flutter_braintree/FlutterBraintree3DSHandler.java:68: error: package CardResult does not exist
                activity.onError(((CardResult.Failure) cardResult).getError());
                                             ^
/Users/airm1/work/overseas/flutter-braintree/android/src/main/java/com/example/flutter_braintree/FlutterBraintree3DSHandler.java:76: error: method createPaymentAuthRequest in class ThreeDSecureClient cannot be applied to given types;
        threeDSecureClient.createPaymentAuthRequest(request, paymentAuthRequest -> {
                          ^
  required: Context,ThreeDSecureRequest,ThreeDSecurePaymentAuthRequestCallback
  found:    ThreeDSecureRequest,(paymentAu[...]; } }
  reason: actual and formal argument lists differ in length
/Users/airm1/work/overseas/flutter-braintree/android/src/main/java/com/example/flutter_braintree/FlutterBraintree3DSHandler.java:76: error: incompatible types: ThreeDSecureRequest cannot be converted to Context
        threeDSecureClient.createPaymentAuthRequest(request, paymentAuthRequest -> {
                                                    ^
/Users/airm1/work/overseas/flutter-braintree/android/src/main/java/com/example/flutter_braintree/FlutterBraintree3DSHandler.java:129: error: cannot find symbol
        request.setVersionRequested(ThreeDSecureRequest.VERSION_2);
                                                       ^
  symbol:   variable VERSION_2
  location: class ThreeDSecureRequest
/Users/airm1/work/overseas/flutter-braintree/android/src/main/java/com/example/flutter_braintree/FlutterBraintreeGooglePayHandler.java:82: error: incompatible types: int cannot be converted to GooglePayTotalPriceStatus
            WalletConstants.TOTAL_PRICE_STATUS_FINAL
                           ^
/Users/airm1/work/overseas/flutter-braintree/android/src/main/java/com/example/flutter_braintree/FlutterBraintreeGooglePayHandler.java:87: error: cannot find symbol
        request.setBillingAddressFormat(GooglePayRequest.BILLING_ADDRESS_FORMAT_FULL);
                                                        ^
  symbol:   variable BILLING_ADDRESS_FORMAT_FULL
  location: class GooglePayRequest
Note: Some input files use or override a deprecated API.
Note: Recompile with -Xlint:deprecation for details.
Note: Some input files use unchecked or unsafe operations.
Note: Recompile with -Xlint:unchecked for details.
Note: Some messages have been simplified; recompile with -Xdiags:verbose to get full output
17 errors

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':flutter_braintree:compileDebugJavaWithJavac'.
> Compilation failed; see the compiler error output for details.

* Try:
> Run with --info option to get more log output.
> Run with --scan to get full insights.

BUILD FAILED in 6s
