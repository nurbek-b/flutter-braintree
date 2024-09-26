import 'dart:io';

class BraintreeDropInResult {
  const BraintreeDropInResult({
    required this.paymentMethodNonce,
    required this.deviceData,
  });

  factory BraintreeDropInResult.fromJson(dynamic source) {
    return BraintreeDropInResult(
      paymentMethodNonce:
          BraintreePaymentMethodNonce.fromJson(source['paymentMethodNonce']),
      deviceData: source['deviceData'],
    );
  }

  /// The payment method nonce containing all relevant information for the payment.
  final BraintreePaymentMethodNonce paymentMethodNonce;

  /// String of device data. `null`, if `collectDeviceData` was set to false.
  final String? deviceData;
}

class BraintreePaymentMethodNonce {
  const BraintreePaymentMethodNonce({
    required this.nonce,
    required this.typeLabel,
    required this.description,
    required this.isDefault,
    this.paypalPayerId,
    this.deviceData,
    this.billingInfo,
  });

  factory BraintreePaymentMethodNonce.fromJson(dynamic source) {
    // Safely cast to Map<String, Object?> and ensure all keys are strings
    final Map<String, Object?> data = (source as Map<Object?, Object?>)
        .map((key, value) => MapEntry(key.toString(), value));

    // Android billingInfo: [phoneNumber, streetAddress, surname, givenName, postalCode, locality, extendedAddress, region, countryCodeAlpha2]
    // IOS billingInfo: [address: {street, city, postalCode, state, country}, name]

    Map<String, dynamic> resultBillingInfo = {};
    if (Platform.isAndroid) {
      resultBillingInfo = ((data['billingInfo'] as Map<Object?, Object?>?)
              ?.map((key, value) => MapEntry(key.toString(), value))) ??
          {};
    } else if (Platform.isIOS) {
      final iosInfo = ((data['billingInfo'] as Map<Object?, Object?>?)
          ?.map((key, value) => MapEntry(key.toString(), value)));
      if (((iosInfo?['name'] as String?) ?? '').isNotEmpty) {
        resultBillingInfo = {};
        final nameParts = (iosInfo?['name'] as String?)?.split(' ') ?? [];
        resultBillingInfo['givenName'] = nameParts.firstOrNull;
        if (nameParts.length > 1) {
          resultBillingInfo['surname'] =
              nameParts.getRange(1, nameParts.length).join(' ');
        }
      }
      if (iosInfo?['address'] != null) {
        final iosAddress = (iosInfo?['address'] as Map<Object?, Object?>?)
            ?.map((key, value) => MapEntry(key.toString(), value));
        resultBillingInfo['locality'] = iosAddress?['city'];
        resultBillingInfo['region'] = iosAddress?['state'];
        resultBillingInfo['postalCode'] = iosAddress?['postalCode'];
        resultBillingInfo['countryCodeAlpha2'] = iosAddress?['country'];
        resultBillingInfo['streetAddress'] = iosAddress?['street'];
      }
    } else {
      resultBillingInfo = {};
    }

    return BraintreePaymentMethodNonce(
      nonce: (data['nonce'] as String?) ?? '',
      typeLabel: (data['typeLabel'] as String?) ?? '',
      description: (data['description'] as String?) ?? '',
      isDefault: data['isDefault'] == true,
      paypalPayerId: data['paypalPayerId'] as String?,
      deviceData: data['deviceData'] as String?,
      billingInfo: resultBillingInfo,
    );
  }

  /// The nonce generated for this payment method by the Braintree gateway. The nonce will represent
  /// this PaymentMethod for the purposes of creating transactions and other monetary actions.
  final String nonce;

  /// The type of this PaymentMethod for displaying to a customer, e.g. 'Visa'. Can be used for displaying appropriate logos, etc.
  final String typeLabel;

  /// The description of this PaymentMethod for displaying to a customer, e.g. 'Visa ending in...'.
  final String description;

  /// True if this payment method is the default for the current customer, false otherwise.
  final bool isDefault;

  /// PayPal payer id if requesting for paypal nonce
  final String? paypalPayerId;

  /// String of device data.
  final String? deviceData;

  /// Billing info for the payment method
  final Map<String, dynamic>? billingInfo;
}
