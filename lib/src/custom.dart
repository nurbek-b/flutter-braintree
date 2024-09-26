import 'package:flutter/services.dart';

import 'request.dart';
import 'result.dart';

class Braintree {
  static const MethodChannel _kChannel =
      const MethodChannel('flutter_braintree.custom');

  const Braintree._();

  /// Tokenizes a credit card.
  ///
  /// [authorization] must be either a valid client token or a valid tokenization key.
  /// [request] should contain all the credit card information necessary for tokenization.
  ///
  /// Returns a [Future] that resolves to a [BraintreePaymentMethodNonce] if the tokenization was successful.
  static Future<BraintreePaymentMethodNonce?> tokenizeCreditCard(
    String authorization,
    BraintreeCreditCardRequest request,
  ) async {
    final result = await _kChannel.invokeMethod('tokenizeCreditCard', {
      'authorization': authorization,
      'request': request.toJson(),
    });
    if (result == null) return null;
    return BraintreePaymentMethodNonce.fromJson(result);
  }

  /// Requests a PayPal payment method nonce.
  ///
  /// [authorization] must be either a valid client token or a valid tokenization key.
  /// [request] should contain all the information necessary for the PayPal request.
  ///
  /// Returns a [Future] that resolves to a [BraintreePaymentMethodNonce] if the user confirmed the request,
  /// or `null` if the user canceled the Vault or Checkout flow.
  static Future<BraintreePaymentMethodNonce?> requestPaypalNonce(
    String authorization,
    BraintreePayPalRequest request,
  ) async {
    final result = await _kChannel.invokeMethod('requestPaypalNonce', {
      'authorization': authorization,
      'request': request.toJson(),
    });
    if (result == null) return null;
    return BraintreePaymentMethodNonce.fromJson(result);
  }

  /// Starts threeDSecureFlow for credit card payment.
  ///
  /// [authorization] must be either a valid client token or a valid tokenization key.
  /// [request] should contain all information necessary for 3dSecure flow.
  ///
  /// Returns a [Future] that resolves to a [BraintreePaymentMethodNonce] if the tokenization was successful.
  static Future<BraintreePaymentMethodNonce?> startThreeDSecureFlow(
    String authorization,
    BraintreeThreeDSecureRequest request,
  ) async {
    final result = await _kChannel.invokeMethod('startThreeDSecureFlow', {
      'authorization': authorization,
      'request': request.toJson(),
    });
    if (result == null) return null;
    return BraintreePaymentMethodNonce.fromJson(result);
  }

  /// Checks if Google Pay is ready for the given authorization.
  ///
  /// [authorization] must be either a valid client token or a valid tokenization key.
  ///
  /// Returns a [Future] that resolves to a [bool] indicating if Google Pay is ready.
  static Future<bool> checkGooglePayReady(String authorization) async {
    final result = await _kChannel.invokeMethod('checkGooglePayReady', {
      'authorization': authorization,
    });
    return result;
  }

  /// Initiates the Google Payment flow.
  ///
  /// [authorization] must be either a valid client token or a valid tokenization key.
  /// [paymentRequest] should contain all information necessary for the Google Payment flow.
  ///
  /// Returns a [Future] that resolves to a [BraintreePaymentMethodNonce] if the payment was successful.
  static Future<BraintreePaymentMethodNonce?> startGooglePaymentFlow(
    String authorization,
    BraintreeGooglePaymentRequest paymentRequest,
  ) async {
    dynamic result;
    try {
      result = await _kChannel.invokeMethod('startGooglePaymentFlow', {
        'authorization': authorization,
        'request': paymentRequest.toJson(),
      });
    } on PlatformException catch (e) {
      throw 'Failed to start Google Payment flow: ${e.message}';
    }
    print('startGooglePaymentFlow result: $result');
    if (result == null) return null;

    return BraintreePaymentMethodNonce.fromJson(result);
  }

  /// Checks if Apple Pay is ready for the given authorization.
  ///
  /// [authorization] must be either a valid client token or a valid tokenization key.
  ///
  /// Returns a [Future] that resolves to a [bool] indicating if Apple Pay is ready.
  static Future<bool> checkApplePayReady(String authorization) async {
    final result = await _kChannel.invokeMethod('checkApplePayReady', {
      'authorization': authorization,
    });
    return result;
  }

  /// Initiates the Apple Payment flow.
  ///
  /// [authorization] must be either a valid client token or a valid tokenization key.
  /// [paymentRequest] should contain all information necessary for the Apple Payment flow.
  ///
  /// Returns a [Future] that resolves to a [BraintreePaymentMethodNonce] if the payment was successful.
  static Future<BraintreePaymentMethodNonce?> startApplePaymentFlow(
    String authorization,
    BraintreeApplePayRequest paymentRequest,
  ) async {
    dynamic result;
    try {
      result = await _kChannel.invokeMethod('startApplePaymentFlow', {
        'authorization': authorization,
        'request': paymentRequest.toJson(),
      });
    } on PlatformException catch (e) {
      throw 'Failed to start Apple Pay flow: ${e.message}';
    }
    if (result == null) return null;
    print(result);
    return BraintreePaymentMethodNonce.fromJson(result);
  }
}
