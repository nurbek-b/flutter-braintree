file:///Users/airm1/work/overseas/braintree_android/v5_MIGRATION_GUIDE.md

onnline documentation
https://developer.paypal.com/braintree/docs/guides/overview

ls -R /Users/airm1/work/overseas/braintree_android/BraintreeCore/src/main/java/com/braintreepayments/api/core
AnalyticsClient.kt                      BraintreeApiConfiguration.kt            ConfigurationCache.kt                   GetReturnLinkUseCase.kt                 PaymentMethodNonce.kt
AnalyticsDatabase.kt                    BraintreeClient.kt                      ConfigurationCallback.kt                GooglePayConfiguration.kt               PostalAddress.kt
AnalyticsEvent.kt                       BraintreeError.kt                       ConfigurationException.kt               GraphQLConfiguration.kt                 PostalAddressParser.kt
AnalyticsEventBlob.kt                   BraintreeException.kt                   ConfigurationLoader.kt                  GraphQLConstants.kt                     SdkComponent.kt
AnalyticsEventBlobDao.kt                BraintreeGraphQLClient.kt               ConfigurationLoaderCallback.kt          IntegrationType.kt                      TLSCertificatePinning.kt
AnalyticsEventParams.kt                 BraintreeGraphQLResponseParser.kt       ConfigurationLoaderResult.kt            InvalidArgumentException.kt             TokenizationKey.kt
AnalyticsParamRepository.kt             BraintreeHttpClient.kt                  CoreAnalytics.kt                        InvalidAuthorization.kt                 TokenizeCallback.kt
AnalyticsUploadWorker.kt                BraintreeHttpResponseParser.kt          CrashReporter.kt                        LinkType.kt                             UUIDHelper.kt
AnalyticsWriteToDbWorker.kt             BraintreeRequestCodes.kt                DeviceInspector.kt                      MerchantRepository.kt                   UserCanceledException.kt
ApiClient.kt                            CardConfiguration.kt                    DeviceMetadata.kt                       MetadataBuilder.kt                      VenmoConfiguration.kt
AppSwitchNotAvailableException.kt       ClientToken.kt                          ErrorWithResponse.kt                    PayPalConfiguration.kt                  VisaCheckoutConfiguration.kt
Authorization.kt                        Configuration.kt                        ExperimentalBetaApi.kt                  PaymentMethod.kt

-a-d-d-r-e-s-s_-o-v-e-r-r-i-d-e_-k-e-y.html                     -e-x-p-e-r-i-e-n-c-e_-p-r-o-f-i-l-e_-k-e-y.html                 -p-a-y-e-r_-e-m-a-i-l_-k-e-y.html
-a-m-o-u-n-t_-k-e-y.html                                        -i-n-t-e-n-t_-k-e-y.html                                        -r-e-q-u-e-s-t_-b-i-l-l-i-n-g_-a-g-r-e-e-m-e-n-t_-k-e-y.html
-a-u-t-h-o-r-i-z-a-t-i-o-n_-f-i-n-g-e-r-p-r-i-n-t_-k-e-y.html   -l-a-n-d-i-n-g_-p-a-g-e_-t-y-p-e_-k-e-y.html                    -r-e-t-u-r-n_-u-r-l_-k-e-y.html
-b-i-l-l-i-n-g_-a-g-r-e-e-m-e-n-t_-d-e-t-a-i-l-s_-k-e-y.html    -l-i-n-e_-i-t-e-m-s_-k-e-y.html                                 -s-h-i-p-p-i-n-g_-a-d-d-r-e-s-s_-k-e-y.html
-c-a-n-c-e-l_-u-r-l_-k-e-y.html                                 -l-o-c-a-l-e_-c-o-d-e_-k-e-y.html                               -t-o-k-e-n-i-z-a-t-i-o-n_-k-e-y.html
-c-o-r-r-e-l-a-t-i-o-n_-i-d_-k-e-y.html                         -m-e-r-c-h-a-n-t_-a-c-c-o-u-n-t_-i-d.html                       -u-s-e-r_-a-c-t-i-o-n_-k-e-y.html
-c-u-r-r-e-n-c-y_-i-s-o_-c-o-d-e_-k-e-y.html                    -n-o_-s-h-i-p-p-i-n-g_-k-e-y.html                               index.html
-d-e-s-c-r-i-p-t-i-o-n_-k-e-y.html                              -o-f-f-e-r_-c-r-e-d-i-t_-k-e-y.html
-d-i-s-p-l-a-y_-n-a-m-e_-k-e-y.html                             -o-f-f-e-r_-p-a-y_-l-a-t-e-r_-k-e-y.html

/Users/airm1/work/overseas/braintree_android/docs//PayPal/com.braintreepayments.api.paypal/-pay-pal-result:
-cancel         -failure        -success        index.html

/Users/airm1/work/overseas/braintree_android/docs//PayPal/com.braintreepayments.api.paypal/-pay-pal-result/-cancel:
index.html

/Users/airm1/work/overseas/braintree_android/docs//PayPal/com.braintreepayments.api.paypal/-pay-pal-result/-failure:
-failure.html   error.html      index.html

/Users/airm1/work/overseas/braintree_android/docs//PayPal/com.braintreepayments.api.paypal/-pay-pal-result/-success:
-success.html   index.html      nonce.html

/Users/airm1/work/overseas/braintree_android/docs//PayPal/com.braintreepayments.api.paypal/-pay-pal-tokenize-callback:
index.html              on-pay-pal-result.html

/Users/airm1/work/overseas/braintree_android/docs//PayPal/com.braintreepayments.api.paypal/-pay-pal-vault-request:
-c-r-e-a-t-o-r.html                     display-name.html                       is-shipping-address-required.html       recurring-billing-details.html          user-authentication-email.html
-pay-pal-vault-request.html             enable-pay-pal-app-switch.html          landing-page-type.html                  recurring-billing-plan-type.html        user-phone-number.html
billing-agreement-description.html      has-user-location-consent.html          line-items.html                         risk-correlation-id.html                write-to-parcel.html
create-request-body.html                index.html                              locale-code.html                        shipping-address-override.html
describe-contents.html                  is-shipping-address-editable.html       merchant-account-id.html                should-offer-credit.html

/Users/airm1/work/overseas/braintree_android/docs//PayPalDataCollector:
com.braintreepayments.api       index.html                      navigation.html

/Users/airm1/work/overseas/braintree_android/docs//PayPalDataCollector/com.braintreepayments.api:
-pay-pal-data-collector                 -pay-pal-data-collector-callback        -pay-pal-data-collector-request         index.html

/Users/airm1/work/overseas/braintree_android/docs//PayPalDataCollector/com.braintreepayments.api/-pay-pal-data-collector:
-pay-pal-data-collector.html    collect-device-data.html        index.html

/Users/airm1/work/overseas/braintree_android/docs//PayPalDataCollector/com.braintreepayments.api/-pay-pal-data-collector-callback:
index.html      on-result.html

/Users/airm1/work/overseas/braintree_android/docs//PayPalDataCollector/com.braintreepayments.api/-pay-pal-data-collector-request:
-pay-pal-data-collector-request.html    has-user-location-consent.html          index.html                              risk-correlation-id.html

/Users/airm1/work/overseas/braintree_android/docs//PayPalNativeCheckout:
com.braintreepayments.api       index.html                      navigation.html

/Users/airm1/work/overseas/braintree_android/docs//PayPalNativeCheckout/com.braintreepayments.api:
-pay-pal-native-checkout-account-nonce                  -pay-pal-native-checkout-internal-client-callback       -pay-pal-native-checkout-request                        index.html
-pay-pal-native-checkout-client                         -pay-pal-native-checkout-line-item                      -pay-pal-native-checkout-result-callback
-pay-pal-native-checkout-credit-financing               -pay-pal-native-checkout-listener                       -pay-pal-native-checkout-vault-request
-pay-pal-native-checkout-credit-financing-amount        -pay-pal-native-checkout-payment-intent                 -pay-pal-native-request

/Users/airm1/work/overseas/braintree_android/docs//PayPalNativeCheckout/com.braintreepayments.api/-pay-pal-native-checkout-account-nonce:
-c-r-e-a-t-o-r.html     billing-address.html    credit-financing.html   first-name.html         last-name.html          phone.html              shipping-address.html
authenticate-url.html   client-metadata-id.html email.html              index.html              payer-id.html           set-payer-info.html     write-to-parcel.html

/Users/airm1/work/overseas/braintree_android/docs//PayPalNativeCheckout/com.braintreepayments.api/-pay-pal-native-checkout-client:
-pay-pal-native-checkout-client.html    index.html                              launch-native-checkout.html             set-listener.html                       tokenize-pay-pal-account.html

/Users/airm1/work/overseas/braintree_android/docs//PayPalNativeCheckout/com.braintreepayments.api/-pay-pal-native-checkout-credit-financing:
-c-r-e-a-t-o-r.html             has-payer-acceptance.html       is-card-amount-immutable.html   term.html                       total-interest.html
describe-contents.html          index.html                      monthly-payment.html            total-cost.html                 write-to-parcel.html

/Users/airm1/work/overseas/braintree_android/docs//PayPalNativeCheckout/com.braintreepayments.api/-pay-pal-native-checkout-credit-financing-amount:
-c-r-e-a-t-o-r.html     currency.html           describe-contents.html  index.html              to-string.html          value.html              write-to-parcel.html

/Users/airm1/work/overseas/braintree_android/docs//PayPalNativeCheckout/com.braintreepayments.api/-pay-pal-native-checkout-internal-client-callback:
index.html      on-result.html

/Users/airm1/work/overseas/braintree_android/docs//PayPalNativeCheckout/com.braintreepayments.api/-pay-pal-native-checkout-line-item:
-c-r-e-a-t-o-r.html                     -u-p-c_-t-y-p-e_-c.html                 description.html                        quantity.html                           url.html
-k-i-n-d_-c-r-e-d-i-t.html              -u-p-c_-t-y-p-e_-d.html                 image-url.html                          to-json.html                            write-to-parcel.html
-k-i-n-d_-d-e-b-i-t.html                -u-p-c_-t-y-p-e_-e.html                 index.html                              unit-amount.html
-pay-pal-native-checkout-line-item.html -u-p-c_-t-y-p-e_2.html                  kind.html                               unit-tax-amount.html
-u-p-c_-t-y-p-e_-a.html                 -u-p-c_-t-y-p-e_5.html                  name.html                               upc-code.html
-u-p-c_-t-y-p-e_-b.html                 describe-contents.html                  product-code.html                       upc-type.html

/Users/airm1/work/overseas/braintree_android/docs//PayPalNativeCheckout/com.braintreepayments.api/-pay-pal-native-checkout-listener:
index.html              on-pay-pal-failure.html on-pay-pal-success.html

/Users/airm1/work/overseas/braintree_android/docs//PayPalNativeCheckout/com.braintreepayments.api/-pay-pal-native-checkout-payment-intent:
-a-u-t-h-o-r-i-z-e.html -o-r-d-e-r.html         -s-a-l-e.html           index.html

/Users/airm1/work/overseas/braintree_android/docs//PayPalNativeCheckout/com.braintreepayments.api/-pay-pal-native-checkout-request:
-c-r-e-a-t-o-r.html                             amount.html                                     intent.html                                     write-to-parcel.html
-pay-pal-native-checkout-request.html           currency-code.html                              should-offer-pay-later.html
-u-s-e-r_-a-c-t-i-o-n_-c-o-m-m-i-t.html         describe-contents.html                          should-request-billing-agreement.html
-u-s-e-r_-a-c-t-i-o-n_-d-e-f-a-u-l-t.html       index.html                                      user-action.html

/Users/airm1/work/overseas/braintree_android/docs//PayPalNativeCheckout/com.braintreepayments.api/-pay-pal-native-checkout-result-callback:
index.html      on-result.html

/Users/airm1/work/overseas/braintree_android/docs//PayPalNativeCheckout/com.braintreepayments.api/-pay-pal-native-checkout-vault-request:
-c-r-e-a-t-o-r.html                             describe-contents.html                          should-offer-credit.html
-pay-pal-native-checkout-vault-request.html     index.html                                      write-to-parcel.html

/Users/airm1/work/overseas/braintree_android/docs//PayPalNativeCheckout/com.braintreepayments.api/-pay-pal-native-request:
-pay-pal-native-request.html            has-user-location-consent.html          line-items.html                         risk-correlation-id.html                set-shipping-address-required.html
billing-agreement-description.html      index.html                              locale-code.html                        set-line-items.html                     shipping-address-override.html
describe-contents.html                  is-shipping-address-editable.html       merchant-account-id.html                set-shipping-address-editable.html      user-authentication-email.html
display-name.html                       is-shipping-address-required.html       return-url.html                         set-shipping-address-override.html      write-to-parcel.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit:
com.braintreepayments.api                       com.braintreepayments.api.sepadirectdebit       index.html                                      navigation.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api:
-s-e-p-a-direct-debit-client            -s-e-p-a-direct-debit-mandate-type      -s-e-p-a-direct-debit-request
-s-e-p-a-direct-debit-listener          -s-e-p-a-direct-debit-nonce             index.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api/-s-e-p-a-direct-debit-client:
-s-e-p-a-direct-debit-client.html       index.html                              set-listener.html                       tokenize.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api/-s-e-p-a-direct-debit-listener:
index.html                              on-s-e-p-a-direct-debit-failure.html    on-s-e-p-a-direct-debit-success.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api/-s-e-p-a-direct-debit-mandate-type:
-o-n-e_-o-f-f           -r-e-c-u-r-r-e-n-t      index.html              to-string.html          value-of.html           values.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api/-s-e-p-a-direct-debit-mandate-type/-o-n-e_-o-f-f:
index.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api/-s-e-p-a-direct-debit-mandate-type/-r-e-c-u-r-r-e-n-t:
index.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api/-s-e-p-a-direct-debit-nonce:
-c-r-e-a-t-o-r.html     customer-id.html        iban-last-four.html     index.html              mandate-type.html       write-to-parcel.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api/-s-e-p-a-direct-debit-request:
-s-e-p-a-direct-debit-request.html      billing-address.html                    iban.html                               locale.html                             merchant-account-id.html
account-holder-name.html                customer-id.html                        index.html                              mandate-type.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api.sepadirectdebit:
-create-mandate-result                                  -s-e-p-a-direct-debit-nonce                             -s-e-p-a-direct-debit-payment-auth-result               -s-e-p-a-direct-debit-result
-s-e-p-a-direct-debit-client                            -s-e-p-a-direct-debit-payment-auth-request              -s-e-p-a-direct-debit-payment-auth-result-info          -s-e-p-a-direct-debit-tokenize-callback
-s-e-p-a-direct-debit-launcher                          -s-e-p-a-direct-debit-payment-auth-request-callback     -s-e-p-a-direct-debit-pending-request                   index.html
-s-e-p-a-direct-debit-mandate-type                      -s-e-p-a-direct-debit-payment-auth-request-params       -s-e-p-a-direct-debit-request

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api.sepadirectdebit/-create-mandate-result:
approval-url.html               bank-reference-token.html       customer-id.html                iban-last-four.html             index.html                      mandate-type.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api.sepadirectdebit/-s-e-p-a-direct-debit-client:
-companion                              -s-e-p-a-direct-debit-client.html       create-payment-auth-request.html        index.html                              tokenize.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api.sepadirectdebit/-s-e-p-a-direct-debit-client/-companion:
index.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api.sepadirectdebit/-s-e-p-a-direct-debit-launcher:
-s-e-p-a-direct-debit-launcher.html     handle-return-to-app-from-browser.html  handle-return-to-app.html               index.html                              launch.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api.sepadirectdebit/-s-e-p-a-direct-debit-mandate-type:
-o-n-e_-o-f-f           -r-e-c-u-r-r-e-n-t      entries.html            index.html              to-string.html          value-of.html           values.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api.sepadirectdebit/-s-e-p-a-direct-debit-mandate-type/-o-n-e_-o-f-f:
index.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api.sepadirectdebit/-s-e-p-a-direct-debit-mandate-type/-r-e-c-u-r-r-e-n-t:
index.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api.sepadirectdebit/-s-e-p-a-direct-debit-nonce:
-c-r-e-a-t-o-r.html     -companion              customer-id.html        iban-last-four.html     index.html              is-default.html         mandate-type.html       string.html             write-to-parcel.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api.sepadirectdebit/-s-e-p-a-direct-debit-nonce/-companion:
from-j-s-o-n.html       index.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api.sepadirectdebit/-s-e-p-a-direct-debit-payment-auth-request:
-failure                -launch-not-required    -ready-to-launch        index.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api.sepadirectdebit/-s-e-p-a-direct-debit-payment-auth-request/-failure:
-failure.html   error.html      index.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api.sepadirectdebit/-s-e-p-a-direct-debit-payment-auth-request/-launch-not-required:
-launch-not-required.html       index.html                      nonce.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api.sepadirectdebit/-s-e-p-a-direct-debit-payment-auth-request/-ready-to-launch:
-ready-to-launch.html   index.html              request-params.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api.sepadirectdebit/-s-e-p-a-direct-debit-payment-auth-request-callback:
index.html                                              on-s-e-p-a-direct-debit-payment-auth-result.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api.sepadirectdebit/-s-e-p-a-direct-debit-payment-auth-request-params:
browser-switch-options.html     index.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api.sepadirectdebit/-s-e-p-a-direct-debit-payment-auth-result:
-failure        -no-result      -success        index.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api.sepadirectdebit/-s-e-p-a-direct-debit-payment-auth-result/-failure:
-failure.html   error.html      index.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api.sepadirectdebit/-s-e-p-a-direct-debit-payment-auth-result/-no-result:
index.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api.sepadirectdebit/-s-e-p-a-direct-debit-payment-auth-result/-success:
-success.html           index.html              payment-auth-info.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api.sepadirectdebit/-s-e-p-a-direct-debit-payment-auth-result-info:
-s-e-p-a-direct-debit-payment-auth-result-info.html     browser-switch-success.html                             index.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api.sepadirectdebit/-s-e-p-a-direct-debit-pending-request:
-failure        -started        index.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api.sepadirectdebit/-s-e-p-a-direct-debit-pending-request/-failure:
-failure.html   error.html      index.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api.sepadirectdebit/-s-e-p-a-direct-debit-pending-request/-started:
-started.html                   index.html                      pending-request-string.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api.sepadirectdebit/-s-e-p-a-direct-debit-request:
-s-e-p-a-direct-debit-request.html      billing-address.html                    iban.html                               locale.html                             merchant-account-id.html
account-holder-name.html                customer-id.html                        index.html                              mandate-type.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api.sepadirectdebit/-s-e-p-a-direct-debit-result:
-cancel         -failure        -success        index.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api.sepadirectdebit/-s-e-p-a-direct-debit-result/-cancel:
index.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api.sepadirectdebit/-s-e-p-a-direct-debit-result/-failure:
-failure.html   error.html      index.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api.sepadirectdebit/-s-e-p-a-direct-debit-result/-success:
-success.html   index.html      nonce.html

/Users/airm1/work/overseas/braintree_android/docs//SEPADirectDebit/com.braintreepayments.api.sepadirectdebit/-s-e-p-a-direct-debit-tokenize-callback:
index.html                              on-s-e-p-a-direct-debit-result.html

/Users/airm1/work/overseas/braintree_android/docs//SamsungPay:
com.braintreepayments.api       index.html                      navigation.html

/Users/airm1/work/overseas/braintree_android/docs//SamsungPay/com.braintreepayments.api:
-build-custom-sheet-payment-info-callback       -samsung-pay-error                              -samsung-pay-listener                           index.html
-samsung-pay-activate-callback                  -samsung-pay-exception                          -samsung-pay-nonce
-samsung-pay-client                             -samsung-pay-is-ready-to-pay-callback           -samsung-pay-update-callback

/Users/airm1/work/overseas/braintree_android/docs//SamsungPay/com.braintreepayments.api/-build-custom-sheet-payment-info-callback:
index.html      on-result.html

/Users/airm1/work/overseas/braintree_android/docs//SamsungPay/com.braintreepayments.api/-samsung-pay-activate-callback:
index.html      on-result.html

/Users/airm1/work/overseas/braintree_android/docs//SamsungPay/com.braintreepayments.api/-samsung-pay-client:
-samsung-pay-client.html                build-custom-sheet-payment-info.html    is-ready-to-pay.html                    update-custom-sheet.html
activate-samsung-pay.html               index.html                              start-samsung-pay.html                  update-samsung-pay.html

/Users/airm1/work/overseas/braintree_android/docs//SamsungPay/com.braintreepayments.api/-samsung-pay-error:
-s-a-m-s-u-n-g_-p-a-y_-a-p-p_-n-e-e-d-s_-u-p-d-a-t-e.html                       -s-a-m-s-u-n-g_-p-a-y_-n-o_-s-u-p-p-o-r-t-e-d_-c-a-r-d-s_-i-n_-w-a-l-l-e-t.html
-s-a-m-s-u-n-g_-p-a-y_-e-r-r-o-r_-u-n-k-n-o-w-n.html                            -s-a-m-s-u-n-g_-p-a-y_-s-e-t-u-p_-n-o-t_-c-o-m-p-l-e-t-e-d.html
-s-a-m-s-u-n-g_-p-a-y_-n-o-t_-r-e-a-d-y.html                                    index.html
-s-a-m-s-u-n-g_-p-a-y_-n-o-t_-s-u-p-p-o-r-t-e-d.html

/Users/airm1/work/overseas/braintree_android/docs//SamsungPay/com.braintreepayments.api/-samsung-pay-exception:
error-code.html index.html

/Users/airm1/work/overseas/braintree_android/docs//SamsungPay/com.braintreepayments.api/-samsung-pay-is-ready-to-pay-callback:
index.html      on-result.html

/Users/airm1/work/overseas/braintree_android/docs//SamsungPay/com.braintreepayments.api/-samsung-pay-listener:
index.html                              on-samsung-pay-card-info-updated.html   on-samsung-pay-start-error.html         on-samsung-pay-start-success.html

/Users/airm1/work/overseas/braintree_android/docs//SamsungPay/com.braintreepayments.api/-samsung-pay-nonce:
-c-r-e-a-t-o-r.html     bin-data.html           card-type.html          index.html              last-four.html          write-to-parcel.html

/Users/airm1/work/overseas/braintree_android/docs//SamsungPay/com.braintreepayments.api/-samsung-pay-update-callback:
index.html      on-result.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights:
com.braintreepayments.api                       com.braintreepayments.api.shopperinsights       index.html                                      navigation.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api:
-shopper-insights-buyer-phone   -shopper-insights-callback      -shopper-insights-client        -shopper-insights-info          -shopper-insights-request       -shopper-insights-result        index.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api/-shopper-insights-buyer-phone:
-shopper-insights-buyer-phone.html      country-code.html                       index.html                              national-number.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api/-shopper-insights-callback:
index.html      on-result.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api/-shopper-insights-client:
-companion                              get-recommended-payment-methods.html    send-pay-pal-presented-event.html       send-venmo-presented-event.html
-shopper-insights-client.html           index.html                              send-pay-pal-selected-event.html        send-venmo-selected-event.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api/-shopper-insights-client/-companion:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api/-shopper-insights-info:
-shopper-insights-info.html             index.html                              is-eligible-in-pay-pal-network.html     is-pay-pal-recommended.html             is-venmo-recommended.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api/-shopper-insights-request:
-shopper-insights-request.html  email.html                      index.html                      phone.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api/-shopper-insights-result:
-failure        -success        index.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api/-shopper-insights-result/-failure:
-failure.html   error.html      index.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api/-shopper-insights-result/-success:
-success.html   index.html      response.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights:
-button-order                   -experiment-type                -presentment-details            -shopper-insights-callback      -shopper-insights-info          -shopper-insights-result
-button-type                    -page-type                      -shopper-insights-buyer-phone   -shopper-insights-client        -shopper-insights-request       index.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-button-order:
-eighth         -f-i-f-t-h      -f-i-r-s-t      -f-o-u-r-t-h    -o-t-h-e-r      -s-e-c-o-n-d    -s-e-v-e-n-t-h  -s-i-x-t-h      -t-h-i-r-d      entries.html    index.html      value-of.html   values.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-button-order/-eighth:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-button-order/-f-i-f-t-h:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-button-order/-f-i-r-s-t:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-button-order/-f-o-u-r-t-h:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-button-order/-o-t-h-e-r:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-button-order/-s-e-c-o-n-d:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-button-order/-s-e-v-e-n-t-h:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-button-order/-s-i-x-t-h:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-button-order/-t-h-i-r-d:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-button-type:
-o-t-h-e-r      -p-a-y-p-a-l    -v-e-n-m-o      entries.html    index.html      value-of.html   values.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-button-type/-o-t-h-e-r:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-button-type/-p-a-y-p-a-l:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-button-type/-v-e-n-m-o:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-experiment-type:
-c-o-n-t-r-o-l  -t-e-s-t        entries.html    index.html      value-of.html   values.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-experiment-type/-c-o-n-t-r-o-l:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-experiment-type/-t-e-s-t:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-page-type:
-a-b-o-u-t                              -h-o-m-e-p-a-g-e                        -o-r-d-e-r_-r-e-v-i-e-w                 -p-r-o-d-u-c-t_-d-e-t-a-i-l-s           index.html
-c-h-e-c-k-o-u-t                        -m-i-n-i_-c-a-r-t                       -o-t-h-e-r                              -s-e-a-r-c-h                            value-of.html
-c-o-n-t-a-c-t                          -o-r-d-e-r_-c-o-n-f-i-r-m-a-t-i-o-n     -p-r-o-d-u-c-t_-c-a-t-e-g-o-r-y         entries.html                            values.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-page-type/-a-b-o-u-t:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-page-type/-c-h-e-c-k-o-u-t:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-page-type/-c-o-n-t-a-c-t:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-page-type/-h-o-m-e-p-a-g-e:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-page-type/-m-i-n-i_-c-a-r-t:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-page-type/-o-r-d-e-r_-c-o-n-f-i-r-m-a-t-i-o-n:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-page-type/-o-r-d-e-r_-r-e-v-i-e-w:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-page-type/-o-t-h-e-r:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-page-type/-p-r-o-d-u-c-t_-c-a-t-e-g-o-r-y:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-page-type/-p-r-o-d-u-c-t_-d-e-t-a-i-l-s:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-page-type/-s-e-a-r-c-h:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-presentment-details:
-presentment-details.html       button-order.html               index.html                      page-type.html                  type.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-shopper-insights-buyer-phone:
-shopper-insights-buyer-phone.html      country-code.html                       index.html                              national-number.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-shopper-insights-callback:
index.html      on-result.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-shopper-insights-client:
-companion                              index.html                              send-pay-pal-presented-event.html       send-selected-event.html
-shopper-insights-client.html           is-pay-pal-app-installed.html           send-pay-pal-selected-event.html        send-venmo-presented-event.html
get-recommended-payment-methods.html    is-venmo-app-installed.html             send-presented-event.html               send-venmo-selected-event.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-shopper-insights-client/-companion:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-shopper-insights-info:
-shopper-insights-info.html             index.html                              is-eligible-in-pay-pal-network.html     is-pay-pal-recommended.html             is-venmo-recommended.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-shopper-insights-request:
-shopper-insights-request.html  email.html                      index.html                      phone.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-shopper-insights-result:
-failure        -success        index.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-shopper-insights-result/-failure:
-failure.html   error.html      index.html

/Users/airm1/work/overseas/braintree_android/docs//ShopperInsights/com.braintreepayments.api.shopperinsights/-shopper-insights-result/-success:
-success.html   index.html      response.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure:
com.braintreepayments.api               com.braintreepayments.api.threedsecure  index.html                              navigation.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api:
-three-d-secure-activity                        -three-d-secure-postal-address                  -three-d-secure-shipping-method                 -three-d-secure-v2-text-box-customization
-three-d-secure-additional-information          -three-d-secure-prepare-lookup-callback         -three-d-secure-v1-ui-customization             -three-d-secure-v2-toolbar-customization
-three-d-secure-client                          -three-d-secure-request                         -three-d-secure-v2-base-customization           -three-d-secure-v2-ui-customization
-three-d-secure-listener                        -three-d-secure-result                          -three-d-secure-v2-button-customization         index.html
-three-d-secure-lookup                          -three-d-secure-result-callback                 -three-d-secure-v2-label-customization

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api/-three-d-secure-activity:
-three-d-secure-activity.html   index.html                      on-validated.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api/-three-d-secure-additional-information:
-c-r-e-a-t-o-r.html                             authentication-indicator.html                   payment-account-age.html                        shipping-address.html
-three-d-secure-additional-information.html     delivery-email.html                             payment-account-indicator.html                  shipping-method-indicator.html
account-age-indicator.html                      delivery-timeframe.html                         preorder-date.html                              shipping-name-indicator.html
account-change-date.html                        describe-contents.html                          preorder-indicator.html                         tax-amount.html
account-change-indicator.html                   fraud-activity.html                             product-code.html                               to-json.html
account-create-date.html                        gift-card-amount.html                           purchase-date.html                              transaction-count-day.html
account-id.html                                 gift-card-count.html                            recurring-end.html                              transaction-count-year.html
account-purchases.html                          gift-card-currency-code.html                    recurring-frequency.html                        user-agent.html
account-pwd-change-date.html                    index.html                                      reorder-indicator.html                          work-phone-number.html
account-pwd-change-indicator.html               installment.html                                sdk-max-timeout.html                            write-to-parcel.html
add-card-attempts.html                          ip-address.html                                 shipping-address-usage-date.html
address-match.html                              order-description.html                          shipping-address-usage-indicator.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api/-three-d-secure-client:
-three-d-secure-client.html                     initialize-challenge-with-lookup-response.html  perform-verification.html
continue-perform-verification.html              on-activity-result.html                         prepare-lookup.html
index.html                                      on-browser-switch-result.html                   set-listener.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api/-three-d-secure-listener:
index.html                      on-three-d-secure-failure.html  on-three-d-secure-success.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api/-three-d-secure-lookup:
-c-r-e-a-t-o-r.html                     describe-contents.html                  pareq.html                              three-d-secure-version.html
-three-d-secure-lookup.html             index.html                              requires-user-authentication.html       transaction-id.html
acs-url.html                            md.html                                 term-url.html                           write-to-parcel.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api/-three-d-secure-postal-address:
-c-r-e-a-t-o-r.html                     extended-address.html                   locality.html                           street-address.html
-three-d-secure-postal-address.html     given-name.html                         phone-number.html                       surname.html
country-code-alpha2.html                index.html                              postal-code.html                        to-json.html
describe-contents.html                  line3.html                              region.html                             write-to-parcel.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api/-three-d-secure-prepare-lookup-callback:
index.html      on-result.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api/-three-d-secure-request:
-b-o-t-h.html                                           -s-e-c-u-r-e_-c-o-r-p-o-r-a-t-e.html                    build.html                                              requested-exemption-type.html
-c-r-e-a-t-o-r.html                                     -s-i-n-g-l-e_-s-e-l-e-c-t.html                          describe-contents.html                                  set-card-add-challenge-requested.html
-c-r-e-d-i-t.html                                       -t-r-a-n-s-a-c-t-i-o-n_-r-i-s-k_-a-n-a-l-y-s-i-s.html   email.html                                              set-challenge-requested.html
-d-e-b-i-t.html                                         -t-r-u-s-t-e-d_-b-e-n-e-f-i-c-i-a-r-y.html              index.html                                              set-data-only-requested.html
-h-t-m-l.html                                           -three-d-secure-request.html                            is-card-add-challenge-requested.html                    set-exemption-requested.html
-l-o-w_-v-a-l-u-e.html                                  -v-e-r-s-i-o-n_1.html                                   is-challenge-requested.html                             shipping-method.html
-m-u-l-t-i_-s-e-l-e-c-t.html                            -v-e-r-s-i-o-n_2.html                                   is-data-only-requested.html                             ui-type.html
-n-a-t-i-v-e.html                                       account-type.html                                       is-exemption-requested.html                             v1-ui-customization.html
-o-o-b.html                                             additional-information.html                             mobile-phone-number.html                                v2-ui-customization.html
-o-t-p.html                                             amount.html                                             nonce.html                                              version-requested.html
-r-e-n-d-e-r_-h-t-m-l.html                              billing-address.html                                    render-types.html                                       write-to-parcel.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api/-three-d-secure-result:
-c-r-e-a-t-o-r.html     describe-contents.html  error-message.html      index.html              lookup.html             tokenized-card.html     write-to-parcel.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api/-three-d-secure-result-callback:
index.html      on-result.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api/-three-d-secure-shipping-method:
-e-l-e-c-t-r-o-n-i-c_-d-e-l-i-v-e-r-y.html      -g-r-o-u-n-d.html                               -s-a-m-e_-d-a-y.html                            index.html
-e-x-p-e-d-i-t-e-d.html                         -p-r-i-o-r-i-t-y.html                           -s-h-i-p_-t-o_-s-t-o-r-e.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api/-three-d-secure-v1-ui-customization:
-c-r-e-a-t-o-r.html                             describe-contents.html                          redirect-button-text.html                       write-to-parcel.html
-three-d-secure-v1-ui-customization.html        index.html                                      redirect-description.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api/-three-d-secure-v2-base-customization:
-c-r-e-a-t-o-r.html     describe-contents.html  index.html              text-color.html         text-font-name.html     text-font-size.html     write-to-parcel.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api/-three-d-secure-v2-button-customization:
-c-r-e-a-t-o-r.html                             get-corner-radius.html                          index.html                                      set-text-font-name.html
-three-d-secure-v2-button-customization.html    get-text-color.html                             set-background-color.html                       set-text-font-size.html
describe-contents.html                          get-text-font-name.html                         set-corner-radius.html                          write-to-parcel.html
get-background-color.html                       get-text-font-size.html                         set-text-color.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api/-three-d-secure-v2-label-customization:
-c-r-e-a-t-o-r.html                             get-heading-text-font-size.html                 set-heading-text-color.html                     set-text-font-size.html
-three-d-secure-v2-label-customization.html     get-text-color.html                             set-heading-text-font-name.html                 write-to-parcel.html
describe-contents.html                          get-text-font-name.html                         set-heading-text-font-size.html
get-heading-text-color.html                     get-text-font-size.html                         set-text-color.html
get-heading-text-font-name.html                 index.html                                      set-text-font-name.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api/-three-d-secure-v2-text-box-customization:
-c-r-e-a-t-o-r.html                             get-corner-radius.html                          set-border-color.html                           set-text-font-size.html
-three-d-secure-v2-text-box-customization.html  get-text-color.html                             set-border-width.html                           write-to-parcel.html
describe-contents.html                          get-text-font-name.html                         set-corner-radius.html
get-border-color.html                           get-text-font-size.html                         set-text-color.html
get-border-width.html                           index.html                                      set-text-font-name.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api/-three-d-secure-v2-toolbar-customization:
-c-r-e-a-t-o-r.html                             get-header-text.html                            set-background-color.html                       set-text-font-size.html
-three-d-secure-v2-toolbar-customization.html   get-text-color.html                             set-button-text.html                            write-to-parcel.html
describe-contents.html                          get-text-font-name.html                         set-header-text.html
get-background-color.html                       get-text-font-size.html                         set-text-color.html
get-button-text.html                            index.html                                      set-text-font-name.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api/-three-d-secure-v2-ui-customization:
-b-u-t-t-o-n_-t-y-p-e_-c-a-n-c-e-l.html         -b-u-t-t-o-n_-t-y-p-e_-v-e-r-i-f-y.html         describe-contents.html                          text-box-customization.html
-b-u-t-t-o-n_-t-y-p-e_-c-o-n-t-i-n-u-e.html     -c-r-e-a-t-o-r.html                             index.html                                      toolbar-customization.html
-b-u-t-t-o-n_-t-y-p-e_-n-e-x-t.html             -three-d-secure-v2-ui-customization.html        label-customization.html                        write-to-parcel.html
-b-u-t-t-o-n_-t-y-p-e_-r-e-s-e-n-d.html         button-customization.html                       set-button-customization.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure:
-three-d-secure-account-type                    -three-d-secure-nonce                           -three-d-secure-render-type                     -three-d-secure-v2-button-type
-three-d-secure-activity                        -three-d-secure-params                          -three-d-secure-request                         -three-d-secure-v2-label-customization
-three-d-secure-additional-information          -three-d-secure-payment-auth-request            -three-d-secure-requested-exemption-type        -three-d-secure-v2-text-box-customization
-three-d-secure-client                          -three-d-secure-payment-auth-request-callback   -three-d-secure-result                          -three-d-secure-v2-toolbar-customization
-three-d-secure-info                            -three-d-secure-payment-auth-result             -three-d-secure-shipping-method                 -three-d-secure-v2-ui-customization
-three-d-secure-launcher                        -three-d-secure-postal-address                  -three-d-secure-tokenize-callback               index.html
-three-d-secure-launcher-callback               -three-d-secure-prepare-lookup-callback         -three-d-secure-ui-type
-three-d-secure-lookup                          -three-d-secure-prepare-lookup-result           -three-d-secure-v2-button-customization

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-account-type:
-c-r-e-d-i-t            -d-e-b-i-t              entries.html            index.html              string-value.html       value-of.html           values.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-account-type/-c-r-e-d-i-t:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-account-type/-d-e-b-i-t:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-activity:
-three-d-secure-activity.html   index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-additional-information:
-c-r-e-a-t-o-r.html                             authentication-indicator.html                   payment-account-age.html                        shipping-address.html
-three-d-secure-additional-information.html     delivery-email.html                             payment-account-indicator.html                  shipping-method-indicator.html
account-age-indicator.html                      delivery-timeframe.html                         preorder-date.html                              shipping-name-indicator.html
account-change-date.html                        describe-contents.html                          preorder-indicator.html                         tax-amount.html
account-change-indicator.html                   fraud-activity.html                             product-code.html                               to-json.html
account-create-date.html                        gift-card-amount.html                           purchase-date.html                              transaction-count-day.html
account-id.html                                 gift-card-count.html                            recurring-end.html                              transaction-count-year.html
account-purchases.html                          gift-card-currency-code.html                    recurring-frequency.html                        user-agent.html
account-pwd-change-date.html                    index.html                                      reorder-indicator.html                          work-phone-number.html
account-pwd-change-indicator.html               installment.html                                sdk-max-timeout.html                            write-to-parcel.html
add-card-attempts.html                          ip-address.html                                 shipping-address-usage-date.html
address-match.html                              order-description.html                          shipping-address-usage-indicator.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-client:
-three-d-secure-client.html                     index.html                                      prepare-lookup.html                             tokenize.html
create-payment-auth-request.html                initialize-challenge-with-lookup-response.html  send-analytics-and-callback-result.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-info:
-c-r-e-a-t-o-r.html                             describe-contents.html                          is-liability-shifted.html                       three-d-secure-authentication-id.html
-companion                                      ds-transaction-id.html                          liability-shift-possible.html                   three-d-secure-server-transaction-id.html
-three-d-secure-info.html                       eci-flag.html                                   liability-shifted.html                          three-d-secure-version.html
acs-transaction-id.html                         enrolled.html                                   lookup-transaction-status-reason.html           was-verified.html
authentication-transaction-status-reason.html   from-json.html                                  lookup-transaction-status.html                  write-to-parcel.html
authentication-transaction-status.html          index.html                                      pares-status.html                               xid.html
cavv.html                                       is-liability-shift-possible.html                status.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-info/-companion:
from-json.html  index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-launcher:
-companion                      -three-d-secure-launcher.html   activity-launcher.html          index.html                      launch.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-launcher/-companion:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-launcher-callback:
index.html                                      on-three-d-secure-payment-auth-result.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-lookup:
-c-r-e-a-t-o-r.html                     describe-contents.html                  pareq.html                              three-d-secure-version.html
-companion                              index.html                              requires-user-authentication.html       transaction-id.html
acs-url.html                            md.html                                 term-url.html                           write-to-parcel.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-lookup/-companion:
from-json.html  index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-nonce:
-c-r-e-a-t-o-r.html             bin-data.html                   cardholder-name.html            from-j-s-o-n.html               last-four.html                  three-d-secure-info.html
-companion                      bin.html                        expiration-month.html           index.html                      last-two.html                   write-to-parcel.html
authentication-insight.html     card-type.html                  expiration-year.html            is-default.html                 string.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-nonce/-companion:
from-j-s-o-n.html       index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-params:
-c-r-e-a-t-o-r.html             -three-d-secure-params.html     error-message.html              index.html                      three-d-secure-nonce.html
-companion                      describe-contents.html          has-error.html                  lookup.html                     write-to-parcel.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-params/-companion:
from-json.html  index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-payment-auth-request:
-failure                -launch-not-required    -ready-to-launch        index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-payment-auth-request/-failure:
-failure.html   error.html      index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-payment-auth-request/-launch-not-required:
-launch-not-required.html       index.html                      nonce.html                      three-d-secure-lookup.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-payment-auth-request/-ready-to-launch:
-ready-to-launch.html   index.html              request-params.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-payment-auth-request-callback:
index.html                                      on-three-d-secure-payment-auth-request.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-payment-auth-result:
error.html                      index.html                      jwt.html                        three-d-secure-params.html      validate-response.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-postal-address:
-c-r-e-a-t-o-r.html                     describe-contents.html                  line3.html                              region.html                             write-to-parcel.html
-companion                              extended-address.html                   locality.html                           street-address.html
-three-d-secure-postal-address.html     given-name.html                         phone-number.html                       surname.html
country-code-alpha2.html                index.html                              postal-code.html                        to-json.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-postal-address/-companion:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-prepare-lookup-callback:
index.html                      on-prepare-lookup-result.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-prepare-lookup-result:
-failure        -success        index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-prepare-lookup-result/-failure:
-failure.html   error.html      index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-prepare-lookup-result/-success:
-success.html           client-data.html        index.html              request.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-render-type:
-m-u-l-t-i_-s-e-l-e-c-t         -o-t-p                          -s-i-n-g-l-e_-s-e-l-e-c-t       index.html                      values.html
-o-o-b                          -r-e-n-d-e-r_-h-t-m-l           entries.html                    value-of.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-render-type/-m-u-l-t-i_-s-e-l-e-c-t:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-render-type/-o-o-b:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-render-type/-o-t-p:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-render-type/-r-e-n-d-e-r_-h-t-m-l:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-render-type/-s-i-n-g-l-e_-s-e-l-e-c-t:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-request:
-b-o-t-h.html                                           -s-i-n-g-l-e_-s-e-l-e-c-t.html                          data-only-requested.html                                requested-exemption-type.html
-c-r-e-a-t-o-r.html                                     -t-r-a-n-s-a-c-t-i-o-n_-r-i-s-k_-a-n-a-l-y-s-i-s.html   describe-contents.html                                  requestor-app-url.html
-c-r-e-d-i-t.html                                       -t-r-u-s-t-e-d_-b-e-n-e-f-i-c-i-a-r-y.html              email.html                                              set-card-add-challenge-requested.html
-d-e-b-i-t.html                                         -three-d-secure-request.html                            exemption-requested.html                                set-challenge-requested.html
-h-t-m-l.html                                           account-type.html                                       index.html                                              set-data-only-requested.html
-l-o-w_-v-a-l-u-e.html                                  additional-information.html                             is-card-add-challenge-requested.html                    set-exemption-requested.html
-m-u-l-t-i_-s-e-l-e-c-t.html                            amount.html                                             is-challenge-requested.html                             shipping-method.html
-n-a-t-i-v-e.html                                       billing-address.html                                    is-data-only-requested.html                             ui-type.html
-o-o-b.html                                             build.html                                              is-exemption-requested.html                             v2-ui-customization.html
-o-t-p.html                                             card-add-challenge-requested.html                       mobile-phone-number.html                                write-to-parcel.html
-r-e-n-d-e-r_-h-t-m-l.html                              challenge-requested.html                                nonce.html
-s-e-c-u-r-e_-c-o-r-p-o-r-a-t-e.html                    custom-fields.html                                      render-types.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-requested-exemption-type:
-l-o-w_-v-a-l-u-e                                       -t-r-u-s-t-e-d_-b-e-n-e-f-i-c-i-a-r-y                   string-value.html
-s-e-c-u-r-e_-c-o-r-p-o-r-a-t-e                         entries.html                                            value-of.html
-t-r-a-n-s-a-c-t-i-o-n_-r-i-s-k_-a-n-a-l-y-s-i-s        index.html                                              values.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-requested-exemption-type/-l-o-w_-v-a-l-u-e:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-requested-exemption-type/-s-e-c-u-r-e_-c-o-r-p-o-r-a-t-e:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-requested-exemption-type/-t-r-a-n-s-a-c-t-i-o-n_-r-i-s-k_-a-n-a-l-y-s-i-s:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-requested-exemption-type/-t-r-u-s-t-e-d_-b-e-n-e-f-i-c-i-a-r-y:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-result:
-cancel         -failure        -success        index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-result/-cancel:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-result/-failure:
-failure.html   error.html      index.html      nonce.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-result/-success:
-success.html   index.html      nonce.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-shipping-method:
-e-l-e-c-t-r-o-n-i-c_-d-e-l-i-v-e-r-y           -g-r-o-u-n-d                                    -s-a-m-e_-d-a-y                                 entries.html
-e-l-e-c-t-r-o-n-i-c_-d-e-l-i-v-e-r-y.html      -g-r-o-u-n-d.html                               -s-a-m-e_-d-a-y.html                            index.html
-e-x-p-e-d-i-t-e-d                              -p-r-i-o-r-i-t-y                                -s-h-i-p_-t-o_-s-t-o-r-e                        value-of.html
-e-x-p-e-d-i-t-e-d.html                         -p-r-i-o-r-i-t-y.html                           -s-h-i-p_-t-o_-s-t-o-r-e.html                   values.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-shipping-method/-e-l-e-c-t-r-o-n-i-c_-d-e-l-i-v-e-r-y:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-shipping-method/-e-x-p-e-d-i-t-e-d:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-shipping-method/-g-r-o-u-n-d:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-shipping-method/-p-r-i-o-r-i-t-y:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-shipping-method/-s-a-m-e_-d-a-y:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-shipping-method/-s-h-i-p_-t-o_-s-t-o-r-e:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-tokenize-callback:
index.html                      on-three-d-secure-result.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-ui-type:
-b-o-t-h        -h-t-m-l        -n-a-t-i-v-e    entries.html    index.html      value-of.html   values.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-ui-type/-b-o-t-h:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-ui-type/-h-t-m-l:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-ui-type/-n-a-t-i-v-e:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-v2-button-customization:
-c-r-e-a-t-o-r.html                             get-background-color.html                       set-background-color.html                       text-font-name.html
-three-d-secure-v2-button-customization.html    get-corner-radius.html                          set-corner-radius.html                          text-font-size.html
background-color.html                           get-text-color.html                             set-text-color.html                             write-to-parcel.html
cardinal-button-customization.html              get-text-font-name.html                         set-text-font-name.html
corner-radius.html                              get-text-font-size.html                         set-text-font-size.html
describe-contents.html                          index.html                                      text-color.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-v2-button-type:
-b-u-t-t-o-n_-t-y-p-e_-c-a-n-c-e-l      -b-u-t-t-o-n_-t-y-p-e_-n-e-x-t          -b-u-t-t-o-n_-t-y-p-e_-v-e-r-i-f-y      index.html                              values.html
-b-u-t-t-o-n_-t-y-p-e_-c-o-n-t-i-n-u-e  -b-u-t-t-o-n_-t-y-p-e_-r-e-s-e-n-d      entries.html                            value-of.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-v2-button-type/-b-u-t-t-o-n_-t-y-p-e_-c-a-n-c-e-l:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-v2-button-type/-b-u-t-t-o-n_-t-y-p-e_-c-o-n-t-i-n-u-e:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-v2-button-type/-b-u-t-t-o-n_-t-y-p-e_-n-e-x-t:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-v2-button-type/-b-u-t-t-o-n_-t-y-p-e_-r-e-s-e-n-d:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-v2-button-type/-b-u-t-t-o-n_-t-y-p-e_-v-e-r-i-f-y:
index.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-v2-label-customization:
-c-r-e-a-t-o-r.html                             get-heading-text-font-size.html                 heading-text-font-size.html                     set-text-font-name.html
-three-d-secure-v2-label-customization.html     get-text-color.html                             index.html                                      set-text-font-size.html
cardinal-label-customization.html               get-text-font-name.html                         set-heading-text-color.html                     text-color.html
describe-contents.html                          get-text-font-size.html                         set-heading-text-font-name.html                 text-font-name.html
get-heading-text-color.html                     heading-text-color.html                         set-heading-text-font-size.html                 text-font-size.html
get-heading-text-font-name.html                 heading-text-font-name.html                     set-text-color.html                             write-to-parcel.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-v2-text-box-customization:
-c-r-e-a-t-o-r.html                             describe-contents.html                          get-text-font-size.html                         set-text-font-name.html
-three-d-secure-v2-text-box-customization.html  get-border-color.html                           index.html                                      set-text-font-size.html
border-color.html                               get-border-width.html                           set-border-color.html                           text-color.html
border-width.html                               get-corner-radius.html                          set-border-width.html                           text-font-name.html
cardinal-text-box-customization.html            get-text-color.html                             set-corner-radius.html                          text-font-size.html
corner-radius.html                              get-text-font-name.html                         set-text-color.html                             write-to-parcel.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-v2-toolbar-customization:
-c-r-e-a-t-o-r.html                             get-background-color.html                       header-text.html                                set-text-font-name.html
-three-d-secure-v2-toolbar-customization.html   get-button-text.html                            index.html                                      set-text-font-size.html
background-color.html                           get-header-text.html                            set-background-color.html                       text-color.html
button-text.html                                get-text-color.html                             set-button-text.html                            text-font-name.html
cardinal-toolbar-customization.html             get-text-font-name.html                         set-header-text.html                            text-font-size.html
describe-contents.html                          get-text-font-size.html                         set-text-color.html                             write-to-parcel.html

/Users/airm1/work/overseas/braintree_android/docs//ThreeDSecure/com.braintreepayments.api.threedsecure/-three-d-secure-v2-ui-customization:
-b-u-t-t-o-n_-t-y-p-e_-c-a-n-c-e-l.html         -c-r-e-a-t-o-r.html                             describe-contents.html                          toolbar-customization.html
-b-u-t-t-o-n_-t-y-p-e_-c-o-n-t-i-n-u-e.html     -three-d-secure-v2-ui-customization.html        index.html                                      write-to-parcel.html
-b-u-t-t-o-n_-t-y-p-e_-n-e-x-t.html             button-customization.html                       label-customization.html
-b-u-t-t-o-n_-t-y-p-e_-r-e-s-e-n-d.html         button-type.html                                set-button-customization.html
-b-u-t-t-o-n_-t-y-p-e_-v-e-r-i-f-y.html         cardinal-ui-customization.html                  text-box-customization.html

/Users/airm1/work/overseas/braintree_android/docs//UnionPay:
com.braintreepayments.api       index.html                      navigation.html

/Users/airm1/work/overseas/braintree_android/docs//UnionPay/com.braintreepayments.api:
-union-pay-capabilities                 -union-pay-client                       -union-pay-enrollment                   -union-pay-tokenize-callback
-union-pay-card                         -union-pay-enroll-callback              -union-pay-fetch-capabilities-callback  index.html

/Users/airm1/work/overseas/braintree_android/docs//UnionPay/com.braintreepayments.api/-union-pay-capabilities:
-c-r-e-a-t-o-r.html                     describe-contents.html                  is-debit.html                           is-union-pay.html                       write-to-parcel.html
-union-pay-capabilities.html            index.html                              is-supported.html                       supports-two-step-auth-and-capture.html

/Users/airm1/work/overseas/braintree_android/docs//UnionPay/com.braintreepayments.api/-union-pay-card:
-c-r-e-a-t-o-r.html             build-enrollment.html           enrollment-id.html              mobile-country-code.html        sms-code.html
-union-pay-card.html            build-j-s-o-n.html              index.html                      mobile-phone-number.html        write-to-parcel.html

/Users/airm1/work/overseas/braintree_android/docs//UnionPay/com.braintreepayments.api/-union-pay-client:
-union-pay-client.html  enroll.html             fetch-capabilities.html index.html              tokenize.html

/Users/airm1/work/overseas/braintree_android/docs//UnionPay/com.braintreepayments.api/-union-pay-enroll-callback:
index.html      on-result.html

/Users/airm1/work/overseas/braintree_android/docs//UnionPay/com.braintreepayments.api/-union-pay-enrollment:
id.html                         index.html                      is-sms-code-required.html

/Users/airm1/work/overseas/braintree_android/docs//UnionPay/com.braintreepayments.api/-union-pay-fetch-capabilities-callback:
index.html      on-result.html

/Users/airm1/work/overseas/braintree_android/docs//UnionPay/com.braintreepayments.api/-union-pay-tokenize-callback:
index.html      on-result.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo:
com.braintreepayments.api       com.braintreepayments.api.venmo index.html                      navigation.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api:
-venmo-account-nonce                    -venmo-is-ready-to-pay-callback         -venmo-listener                         -venmo-payment-method-usage             -venmo-tokenize-account-callback
-venmo-client                           -venmo-line-item                        -venmo-on-activity-result-callback      -venmo-request                          index.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api/-venmo-account-nonce:
-c-r-e-a-t-o-r.html     email.html              first-name.html         last-name.html          shipping-address.html   write-to-parcel.html
billing-address.html    external-id.html        index.html              phone-number.html       username.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api/-venmo-client:
-venmo-client.html                              is-ready-to-pay.html                            on-browser-switch-result.html                   show-venmo-in-google-play-store.html
clear-active-browser-switch-requests.html       is-venmo-app-switch-available.html              parse-browser-switch-result.html                tokenize-venmo-account.html
index.html                                      on-activity-result.html                         set-listener.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api/-venmo-is-ready-to-pay-callback:
index.html      on-result.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api/-venmo-line-item:
-c-r-e-a-t-o-r.html             -venmo-line-item.html           index.html                      product-code.html               unit-amount.html                write-to-parcel.html
-k-i-n-d_-c-r-e-d-i-t.html      describe-contents.html          kind.html                       quantity.html                   unit-tax-amount.html
-k-i-n-d_-d-e-b-i-t.html        description.html                name.html                       to-json.html                    url.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api/-venmo-listener:
index.html              on-venmo-failure.html   on-venmo-success.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api/-venmo-on-activity-result-callback:
index.html      on-result.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api/-venmo-payment-method-usage:
-m-u-l-t-i_-u-s-e.html          -s-i-n-g-l-e_-u-s-e.html        index.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api/-venmo-request:
-c-r-e-a-t-o-r.html                     describe-contents.html                  index.html                              profile-id.html                         sub-total-amount.html
-venmo-request.html                     discount-amount.html                    is-final-amount.html                    set-line-items.html                     tax-amount.html
collect-customer-billing-address.html   display-name.html                       line-items.html                         shipping-amount.html                    total-amount.html
collect-customer-shipping-address.html  fallback-to-web.html                    payment-method-usage.html               should-vault.html                       write-to-parcel.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api/-venmo-tokenize-account-callback:
index.html      on-result.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo:
-venmo-account-nonce                    -venmo-line-item                        -venmo-payment-auth-request-params      -venmo-pending-request                  index.html
-venmo-client                           -venmo-line-item-kind                   -venmo-payment-auth-result              -venmo-request
-venmo-launcher                         -venmo-payment-auth-request             -venmo-payment-auth-result-info         -venmo-result
-venmo-launcher-callback                -venmo-payment-auth-request-callback    -venmo-payment-method-usage             -venmo-tokenize-callback

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-account-nonce:
-c-r-e-a-t-o-r.html     billing-address.html    external-id.html        index.html              last-name.html          shipping-address.html   username.html
-companion              email.html              first-name.html         is-default.html         phone-number.html       string.html             write-to-parcel.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-account-nonce/-companion:
from-j-s-o-n.html       index.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-client:
-companion                              -venmo-client.html                      create-payment-auth-request.html        index.html                              tokenize.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-client/-companion:
index.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-launcher:
-companion                              handle-return-to-app.html               launch.html
-venmo-launcher.html                    index.html                              show-venmo-in-google-play-store.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-launcher/-companion:
-v-e-n-m-o_-p-a-c-k-a-g-e_-n-a-m-e.html index.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-launcher-callback:
index.html                              on-venmo-payment-auth-result.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-line-item:
-c-r-e-a-t-o-r.html             -k-i-n-d_-d-e-b-i-t.html        description.html                name.html                       to-json.html                    url.html
-companion                      -venmo-line-item.html           index.html                      product-code.html               unit-amount.html                write-to-parcel.html
-k-i-n-d_-c-r-e-d-i-t.html      describe-contents.html          kind.html                       quantity.html                   unit-tax-amount.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-line-item/-companion:
index.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-line-item-kind:
-c-r-e-d-i-t    -d-e-b-i-t      entries.html    index.html      value-of.html   values.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-line-item-kind/-c-r-e-d-i-t:
index.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-line-item-kind/-d-e-b-i-t:
index.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-payment-auth-request:
-failure                -ready-to-launch        index.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-payment-auth-request/-failure:
-failure.html   error.html      index.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-payment-auth-request/-ready-to-launch:
-ready-to-launch.html   index.html              request-params.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-payment-auth-request-callback:
index.html                              on-venmo-payment-auth-request.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-payment-auth-request-params:
browser-switch-options.html     index.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-payment-auth-result:
-failure        -no-result      -success        index.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-payment-auth-result/-failure:
-failure.html   error.html      index.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-payment-auth-result/-no-result:
index.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-payment-auth-result/-success:
-success.html           index.html              payment-auth-info.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-payment-auth-result-info:
index.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-payment-method-usage:
-m-u-l-t-i_-u-s-e               -s-i-n-g-l-e_-u-s-e             entries.html                    value-of.html
-m-u-l-t-i_-u-s-e.html          -s-i-n-g-l-e_-u-s-e.html        index.html                      values.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-payment-method-usage/-m-u-l-t-i_-u-s-e:
index.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-payment-method-usage/-s-i-n-g-l-e_-u-s-e:
index.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-pending-request:
-failure        -started        index.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-pending-request/-failure:
-failure.html   error.html      index.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-pending-request/-started:
-started.html                   index.html                      pending-request-string.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-request:
-c-r-e-a-t-o-r.html                     describe-contents.html                  is-final-amount.html                    set-line-items.html                     tax-amount.html
-venmo-request.html                     discount-amount.html                    line-items.html                         shipping-amount.html                    total-amount.html
collect-customer-billing-address.html   display-name.html                       payment-method-usage.html               should-vault.html                       write-to-parcel.html
collect-customer-shipping-address.html  index.html                              profile-id.html                         sub-total-amount.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-result:
-cancel         -failure        -success        index.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-result/-cancel:
index.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-result/-failure:
-failure.html   error.html      index.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-result/-success:
-success.html   index.html      nonce.html

/Users/airm1/work/overseas/braintree_android/docs//Venmo/com.braintreepayments.api.venmo/-venmo-tokenize-callback:
index.html              on-venmo-result.html

/Users/airm1/work/overseas/braintree_android/docs//images:
anchor-copy-button.svg          burger.svg                      copy-successful-icon.svg        go-to-top-icon.svg              nav-icons
arrow_down.svg                  copy-icon.svg                   footer-go-to-link.svg           logo-icon.svg                   theme-toggle.svg

/Users/airm1/work/overseas/braintree_android/docs//images/nav-icons:
abstract-class-kotlin.svg       annotation.svg                  enum-kotlin.svg                 field-value.svg                 interface-kotlin.svg            typealias-kotlin.svg
abstract-class.svg              class-kotlin.svg                enum.svg                        field-variable.svg              interface.svg
annotation-kotlin.svg           class.svg                       exception-class.svg             function.svg                    object.svg

/Users/airm1/work/overseas/braintree_android/docs//scripts:
clipboard.js                            navigation-loader.js                    platform-content-handler.js             sourceset_dependencies.js
main.js                                 pages.json                              prism.js                                symbol-parameters-wrapper_deferred.js

/Users/airm1/work/overseas/braintree_android/docs//styles:
font-jb-sans-auto.css   jetbrains-mono.css      logo-styles.css         main.css                prism.css               style.css
