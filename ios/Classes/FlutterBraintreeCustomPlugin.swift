import Flutter
import UIKit
import Braintree
import BraintreeDropIn
import os.log

public class FlutterBraintreeCustomPlugin: BaseFlutterBraintreePlugin, FlutterPlugin, BTViewControllerPresentingDelegate, BTThreeDSecureRequestDelegate {
    private var completionBlock: FlutterResult?
    public static func register(with registrar: FlutterPluginRegistrar) {
        let channel = FlutterMethodChannel(name: "flutter_braintree.custom", binaryMessenger: registrar.messenger())
        let instance = FlutterBraintreeCustomPlugin()
        registrar.addMethodCallDelegate(instance, channel: channel)
    }
    
    public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
        os_log("handle called with method: %{public}@", log: OSLog.default, type: .info, call.method)
        guard !isHandlingResult else {
            os_log("Already handling result.", log: OSLog.default, type: .info)
            returnAlreadyOpenError(result: result)
            return
        }
        isHandlingResult = true
        guard let authorization = getAuthorization(call: call) else {
            os_log("Authorization missing.", log: OSLog.default, type: .error)
            returnAuthorizationMissingError(result: result)
            isHandlingResult = false
            return
        }
        self.completionBlock = result  // Store the completion block here
        let client = BTAPIClient(authorization: authorization)
        switch call.method {
        case "requestPaypalNonce":
            handlePayPalNonce(call: call, client: client!, result: result)
        case "tokenizeCreditCard":
            handleCreditCardTokenization(call: call, client: client!, result: result)
        case "startThreeDSecureFlow":
            startThreeDSecureFlow(call: call, client: client!, result: result)
        default:
            result(FlutterMethodNotImplemented)
            self.isHandlingResult = false
        }
    }
    
    private func handleResult(nonce: BTPaymentMethodNonce?, error: Error?, flutterResult: FlutterResult) {
        os_log("handleResult called.", log: OSLog.default, type: .info)
        if let error = error {
            os_log("handleResult error: %{public}@", log: OSLog.default, type: .error, error.localizedDescription)
            returnBraintreeError(result: flutterResult, error: error)
        } else if let nonce = nonce {
            os_log("handleResult nonce: %{public}@", log: OSLog.default, type: .info, nonce.nonce)
            if let cardNonce = nonce as? BTCardNonce {
                let threeDSecureInfo = cardNonce.threeDSecureInfo 
                            os_log("3D Secure liability shift possible: %{public}@", log: OSLog.default, type: .info, String(threeDSecureInfo.liabilityShiftPossible))
                            os_log("3D Secure liability shifted: %{public}@", log: OSLog.default, type: .info, String(threeDSecureInfo.liabilityShifted))
                            
                            let resultDict: [String: Any] = [
                                "nonce": nonce.nonce,
                                "isDefault": nonce.isDefault,
                                "liabilityShiftPossible": threeDSecureInfo.liabilityShiftPossible,
                                "liabilityShifted": threeDSecureInfo.liabilityShifted
                            ]
                        
            }

            flutterResult(buildPaymentNonceDict(nonce: nonce))
        } else {
            os_log("handleResult no nonce and no error.", log: OSLog.default, type: .info)
            flutterResult(nil)
        }
    }
    
    private func handlePayPalNonce(call: FlutterMethodCall, client: BTAPIClient, result: @escaping FlutterResult) {
        let driver = BTPayPalDriver(apiClient: client)
        guard let requestInfo = dict(for: "request", in: call) else {
            isHandlingResult = false
            return
        }
        if let amount = requestInfo["amount"] as? String {
            let paypalRequest = BTPayPalCheckoutRequest(amount: amount)
            paypalRequest.currencyCode = requestInfo["currencyCode"] as? String
            paypalRequest.displayName = requestInfo["displayName"] as? String
            paypalRequest.billingAgreementDescription = requestInfo["billingAgreementDescription"] as? String
            if let intent = requestInfo["payPalPaymentIntent"] as? String {
                switch intent {
                case "order":
                    paypalRequest.intent = .order
                case "sale":
                    paypalRequest.intent = .sale
                default:
                    paypalRequest.intent = .authorize
                }
            }
            if let userAction = requestInfo["payPalPaymentUserAction"] as? String {
                switch userAction {
                case "commit":
                    paypalRequest.userAction = .commit
                default:
                    paypalRequest.userAction = .default
                }
            }
            driver.tokenizePayPalAccount(with: paypalRequest) { (nonce, error) in
                os_log("handlePayPalNonce callback called.", log: OSLog.default, type: .info)
                self.handleResult(nonce: nonce, error: error, flutterResult: result)
                self.isHandlingResult = false
            }
        } else {
            let paypalRequest = BTPayPalVaultRequest()
            paypalRequest.displayName = requestInfo["displayName"] as? String
            paypalRequest.billingAgreementDescription = requestInfo["billingAgreementDescription"] as? String
            driver.tokenizePayPalAccount(with: paypalRequest) { (nonce, error) in
                os_log("handlePayPalNonce callback called (vault).", log: OSLog.default, type: .info)
                self.handleResult(nonce: nonce, error: error, flutterResult: result)
                self.isHandlingResult = false
            }
        }
    }
    
    private func handleCreditCardTokenization(call: FlutterMethodCall, client: BTAPIClient, result: @escaping FlutterResult) {
        let cardClient = BTCardClient(apiClient: client)
        guard let cardRequestInfo = dict(for: "request", in: call) else { 
            os_log("handleCreditCardTokenization missing card request info.", log: OSLog.default, type: .error)
            isHandlingResult = false
            return 
        }
        let card = BTCard()
        card.number = cardRequestInfo["cardNumber"] as? String
        card.expirationMonth = cardRequestInfo["expirationMonth"] as? String
        card.expirationYear = cardRequestInfo["expirationYear"] as? String
        card.cvv = cardRequestInfo["cvv"] as? String
        card.cardholderName = cardRequestInfo["cardholderName"] as? String
        cardClient.tokenizeCard(card) { (nonce, error) in
            os_log("handleCreditCardTokenization callback called.", log: OSLog.default, type: .info)
            self.handleResult(nonce: nonce, error: error, flutterResult: result)
            self.isHandlingResult = false
        }
    }
    
    private func startThreeDSecureFlow(call: FlutterMethodCall, client: BTAPIClient, result: @escaping FlutterResult) {
        os_log("startThreeDSecureFlow called", log: OSLog.default, type: .info)
        
        guard let amount = dict(for: "request", in: call)?["amount"] as? String else {
            os_log("startThreeDSecureFlow missing amount.", log: OSLog.default, type: .error)
            self.returnAuthorizationMissingError(result: result)
            self.isHandlingResult = false
            return
        }
        
        guard let nonce = dict(for: "request", in: call)?["nonce"] as? String else {
            os_log("startThreeDSecureFlow missing nonce.", log: OSLog.default, type: .error)
            self.returnAuthorizationMissingError(result: result)
            self.isHandlingResult = false
            return
        }
        
        os_log("startThreeDSecureFlow input nonce: %{public}@", log: OSLog.default, type: .info, nonce)
        
        let threeDSecureRequest = BTThreeDSecureRequest()
        threeDSecureRequest.amount = NSDecimalNumber(string: amount)
        threeDSecureRequest.nonce = nonce
        threeDSecureRequest.email = dict(for: "request", in: call)?["email"] as? String
        threeDSecureRequest.versionRequested = .version2
        threeDSecureRequest.threeDSecureRequestDelegate = self
        
        os_log("BTThreeDSecureRequest created with amount: %{public}@", log: OSLog.default, type: .info, amount)
        os_log("BTThreeDSecureRequest created with nonce: %{public}@", log: OSLog.default, type: .info, nonce)
        
        if let email = threeDSecureRequest.email {
            os_log("BTThreeDSecureRequest created with email: %{public}@", log: OSLog.default, type: .info, email)
        } else {
            os_log("BTThreeDSecureRequest created without email", log: OSLog.default, type: .info)
        }
        
        let paymentFlowDriver = BTPaymentFlowDriver(apiClient: client)
        paymentFlowDriver.viewControllerPresentingDelegate = self

        os_log("Starting payment flow with Cardinal.", log: OSLog.default, type: .info)
        paymentFlowDriver.startPaymentFlow(threeDSecureRequest) { (paymentFlowResult, error) in
            os_log("startThreeDSecureFlow callback called.", log: OSLog.default, type: .info)
            if let error = error {
                os_log("startThreeDSecureFlow error: %{public}@", log: OSLog.default, type: .error, error.localizedDescription)
                result(FlutterError(code: "3DSecureError", message: error.localizedDescription, details: nil))
                self.isHandlingResult = false
                return
            }
            let threeDSecureResult = paymentFlowResult as? BTThreeDSecureResult
            os_log("startThreeDSecureFlow callback BTThreeDSecureResult type called.", log: OSLog.default, type: .info)
            self.handleResult(nonce: threeDSecureResult?.tokenizedCard, error: error, flutterResult: result)
            self.isHandlingResult = false
        }
    }
        public func onLookupComplete(_ request: BTThreeDSecureRequest, lookupResult result: BTThreeDSecureResult, next: @escaping () -> Void) {
        os_log("onLookupComplete called", log: OSLog.default, type: .info)
        
        if let lookup = result.lookup {
            
            os_log("3D Secure lookup result available.", log: OSLog.default, type: .info)
            os_log("ACS URL: %{public}@", log: OSLog.default, type: .info, lookup.acsURL?.absoluteString ?? "N/A")
            os_log("MD: %{public}@", log: OSLog.default, type: .info, lookup.md ?? "N/A")
            os_log("PAReq: %{public}@", log: OSLog.default, type: .info, lookup.paReq ?? "N/A")
            os_log("TermURL: %{public}@", log: OSLog.default, type: .info, lookup.termURL?.absoluteString ?? "N/A")
            os_log("Transaction ID: %{public}@", log: OSLog.default, type: .info, lookup.transactionID ?? "N/A")
            os_log("Requires User Authentication: %{public}@", log: OSLog.default, type: .info, String(lookup.requiresUserAuthentication))
            
            if lookup.requiresUserAuthentication {
                os_log("Proceeding with user authentication.", log: OSLog.default, type: .info)
                next()
            } else {
                os_log("User authentication not required.", log: OSLog.default, type: .info)
                let error = NSError(domain: "CardinalLookup", code: 0, userInfo: [NSLocalizedDescriptionKey: "3D Secure lookup does not require user authentication"])
                if let completion = self.completionBlock {
                    self.handleResult(nonce: nil, error: error, flutterResult: completion)
                }
                self.isHandlingResult = false
            }
        } else {
            os_log("3D Secure lookup failed or returned no result.", log: OSLog.default, type: .error)
            let error = NSError(domain: "CardinalLookup", code: 0, userInfo: [NSLocalizedDescriptionKey: "3D Secure lookup failed or returned no result"])
            if let completion = self.completionBlock {
                self.handleResult(nonce: nil, error: error, flutterResult: completion)
            }
            self.isHandlingResult = false
        }
}
    
    public func paymentDriver(_ driver: Any, requestsPresentationOf viewController: UIViewController) {
        os_log("Presenting viewController: %{public}@", log: OSLog.default, type: .info, String(describing: viewController))
        DispatchQueue.main.async {
            if let rootViewController = UIApplication.shared.keyWindow?.rootViewController {
                os_log("Root view controller: %{public}@", log: OSLog.default, type: .info, String(describing: rootViewController))
                rootViewController.present(viewController, animated: true) {
                    os_log("View controller presented: %{public}@", log: OSLog.default, type: .info, String(describing: viewController))
                }
            } else {
                os_log("Failed to get root view controller.", log: OSLog.default, type: .error)
            }
        }
    }
    
    public func paymentDriver(_ driver: Any, requestsDismissalOf viewController: UIViewController) {
        os_log("Dismissing viewController: %{public}@", log: OSLog.default, type: .info, String(describing: viewController))
        DispatchQueue.main.async {
            viewController.dismiss(animated: true) {
                os_log("View controller dismissed: %{public}@", log: OSLog.default, type: .info, String(describing: viewController))
            }
        }
    }

}