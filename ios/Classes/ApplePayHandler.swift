import Braintree
import BraintreeDropIn
import PassKit
import os.log

class ApplePayHandler: NSObject, PKPaymentAuthorizationViewControllerDelegate  {
    private var applePayClient: BTApplePayClient?
    var completionBlock: ((BTPaymentMethodNonce?, Error?) -> Void)?
    weak var presentingDelegate: BTViewControllerPresentingDelegate?
    private var hasCalledCompletion = false

    init(applePayClient: BTApplePayClient) {
        self.applePayClient = applePayClient
    }

    static func canMakePayments() -> Bool {
        let canMakePayments = PKPaymentAuthorizationViewController.canMakePayments(usingNetworks: [.visa, .masterCard, .amex, .discover])
        os_log("Can make Apple Pay payments: %{public}@", log: .default, type: .debug, String(canMakePayments))
        return canMakePayments
    }


    func startApplePaymentFlow(paymentSummaryItems: [[String: Any]], completion: @escaping (BTPaymentMethodNonce?, Error?) -> Void) {
        os_log("startApplePaymentFlow called", log: OSLog.default, type: .info)
        
        self.completionBlock = completion
        
        guard ApplePayHandler.canMakePayments() else {
            os_log("Apple Pay is not available.", log: OSLog.default, type: .error)
            completion(nil, NSError(domain: "ApplePay", code: 0, userInfo: [NSLocalizedDescriptionKey: "Apple Pay is not available on this device."]))
            return
        }
        
        os_log("Apple Pay is available.", log: OSLog.default, type: .info)
        
        self.applePayClient?.paymentRequest { [weak self] (providedPaymentRequest, error) in
            guard let self = self else {
                os_log("Self is nil in paymentRequest callback", log: OSLog.default, type: .error)
                completion(nil, NSError(domain: "ApplePay", code: 0, userInfo: [NSLocalizedDescriptionKey: "Internal error: self is nil"]))
                return
            }
            
            if let error = error {
                os_log("Failed to create payment request: %{public}@", log: OSLog.default, type: .error, error.localizedDescription)
                completion(nil, error)
                return
            }
            
            guard let paymentRequest = providedPaymentRequest else {
                os_log("Payment request is nil, but no error was provided", log: OSLog.default, type: .error)
                completion(nil, NSError(domain: "ApplePay", code: 0, userInfo: [NSLocalizedDescriptionKey: "Failed to create payment request: unknown error"]))
                return
            }
            
            os_log("Payment request created successfully", log: OSLog.default, type: .info)
            
            self.configurePaymentRequest(paymentRequest, with: paymentSummaryItems)
            
            if let vc = PKPaymentAuthorizationViewController(paymentRequest: paymentRequest) {
                os_log("PKPaymentAuthorizationViewController created successfully", log: OSLog.default, type: .info)
                vc.delegate = self
                self.presentPaymentAuthorization(vc)
            } else {
                os_log("Failed to create PKPaymentAuthorizationViewController", log: OSLog.default, type: .error)
                completion(nil, NSError(domain: "ApplePay", code: 0, userInfo: [NSLocalizedDescriptionKey: "Failed to create payment authorization view controller"]))
            }
        }
    }

    private func configurePaymentRequest(_ paymentRequest: PKPaymentRequest, with paymentSummaryItemsData: [[String: Any]]) {
        os_log("Configuring payment request", log: OSLog.default, type: .info)
        
        // Set required billing contact fields
        paymentRequest.requiredBillingContactFields = [.postalAddress]

        // Set merchant capabilities
        paymentRequest.merchantCapabilities = .capability3DS

        // Process payment summary items
        var paymentSummaryItems: [PKPaymentSummaryItem] = []
        for item in paymentSummaryItemsData {
            os_log("Processing item: %{public}@", log: OSLog.default, type: .info, String(describing: item))

            if let label = item["label"] as? String {
                os_log("Extracted label: %{public}@", log: OSLog.default, type: .info, label)
            } else {
                os_log("Failed to extract label from item: %{public}@", log: OSLog.default, type: .error, String(describing: item))
            }
            
            if let amountDouble = item["amount"] as? Double {
                os_log("Extracted amount actual: %f", log: OSLog.default, type: .info, amountDouble)
                let amountString = String(amountDouble)
                let amount = NSDecimalNumber(string: amountString)
                if let label = item["label"] as? String {
                    let paymentSummaryItem = PKPaymentSummaryItem(label: label, amount: amount)
                    paymentSummaryItems.append(paymentSummaryItem)
                } else {
                    os_log("Failed to extract label from item: %{public}@", log: OSLog.default, type: .error, String(describing: item))
                }
            } else {
                os_log("Failed to extract amount from item: %{public}@", log: OSLog.default, type: .error, String(describing: item))
            }
        }

        // Set the processed payment summary items
        paymentRequest.paymentSummaryItems = paymentSummaryItems

        // Log the configured payment request
        os_log("paymentRequest configured:", log: OSLog.default, type: .info)
        os_log("paymentRequest.merchantIdentifier: %{public}@", log: OSLog.default, type: .info, String(describing: paymentRequest.merchantIdentifier))
        os_log("paymentRequest.supportedNetworks: %{public}@", log: OSLog.default, type: .info, String(describing: paymentRequest.supportedNetworks))
        os_log("paymentRequest.countryCode: %{public}@", log: OSLog.default, type: .info, String(describing: paymentRequest.countryCode))
        os_log("paymentRequest.currencyCode: %{public}@", log: OSLog.default, type: .info, String(describing: paymentRequest.currencyCode))
        os_log("paymentRequest.merchantCapabilities: %{public}@", log: OSLog.default, type: .info, String(describing: paymentRequest.merchantCapabilities))
        os_log("paymentRequest.requiredBillingContactFields: %{public}@", log: OSLog.default, type: .info, String(describing: paymentRequest.requiredBillingContactFields))
        os_log("paymentRequest.paymentSummaryItems:", log: OSLog.default, type: .info)
        for item in paymentRequest.paymentSummaryItems {
            os_log("label: %{public}@, amount: %{public}@", log: OSLog.default, type: .info, item.label, item.amount.description)
        }
    }
    

    // MARK: - PKPaymentAuthorizationViewControllerDelegate

    public func paymentAuthorizationViewController(_ controller: PKPaymentAuthorizationViewController,
                        didAuthorizePayment payment: PKPayment,
                                handler completion: @escaping (PKPaymentAuthorizationResult) -> Void) {
        os_log("Payment authorized, tokenizing Apple Pay payment", log: .default, type: .info)

        self.applePayClient?.tokenizeApplePay(payment) { [weak self] (nonce, error) in
            guard let self = self else { return }
            
            if let nonce = nonce {
                os_log("Tokenize Apple Pay payment succeeded", log: .default, type: .info)
                self.completionBlock?(nonce, nil)
                completion(PKPaymentAuthorizationResult(status: .success, errors: nil))
            } else if let error = error {
                os_log("Tokenize Apple Pay payment failed with error: %{public}@", log: .default, type: .error, error.localizedDescription)
                self.completionBlock?(nil, error)
                completion(PKPaymentAuthorizationResult(status: .failure, errors: [error]))
            } else {
                os_log("Tokenize Apple Pay payment failed with unknown error", log: .default, type: .error)
                let unknownError = NSError(domain: "ApplePay", code: 0, userInfo: [NSLocalizedDescriptionKey: "Unknown error occurred"])
                self.completionBlock?(nil, unknownError)
                completion(PKPaymentAuthorizationResult(status: .failure, errors: [unknownError]))
            }
            
            os_log("paymentAuthorizationViewController completion called", log: .default, type: .info)
        }
    }

    public func paymentAuthorizationViewControllerDidFinish(_ controller: PKPaymentAuthorizationViewController) {
        os_log("paymentAuthorizationViewControllerDidFinish called", log: OSLog.default, type: .info)
        
        presentingDelegate?.paymentDriver(self, requestsDismissalOf: controller)
        
        // If completion hasn't been called (e.g., user cancelled), call it now
        if !hasCalledCompletion {
            self.callCompletion(nonce: nil, error: nil)
        }
    }

    private func callCompletion(nonce: BTPaymentMethodNonce?, error: Error?) {
        guard !hasCalledCompletion else { return }
        hasCalledCompletion = true
        self.completionBlock?(nonce, error)
        self.completionBlock = nil
    }

    func presentPaymentAuthorization(_ viewController: PKPaymentAuthorizationViewController) {
        presentingDelegate?.paymentDriver(self, requestsPresentationOf: viewController)
    }

}
