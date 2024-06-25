import Flutter
import UIKit
import Braintree
import BraintreeDropIn

public class FlutterBraintreeCustomPlugin: BaseFlutterBraintreePlugin, FlutterPlugin, BTViewControllerPresentingDelegate, BTThreeDSecureRequestDelegate {
    public static func register(with registrar: FlutterPluginRegistrar) {
        let channel = FlutterMethodChannel(name: "flutter_braintree.custom", binaryMessenger: registrar.messenger())
        
        let instance = FlutterBraintreeCustomPlugin()
        registrar.addMethodCallDelegate(instance, channel: channel)
    }
    
    public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
        guard !isHandlingResult else {
            returnAlreadyOpenError(result: result)
            return
        }
        
        isHandlingResult = true
        
        guard let authorization = getAuthorization(call: call) else {
            returnAuthorizationMissingError(result: result)
            isHandlingResult = false
            return
        }
        
        let client = BTAPIClient(authorization: authorization)
        if call.method == "requestPaypalNonce" {
            handlePayPalNonce(call: call, client: client!, result: result)
        } else if call.method == "tokenizeCreditCard" {
            handleCreditCardTokenization(call: call, client: client!, result: result)
        } else if call.method == "startThreeDSecureFlow" {
            startThreeDSecureFlow(call: call, client: client!, result: result)
        } else {
            result(FlutterMethodNotImplemented)
            self.isHandlingResult = false
        }
        
    }
    
    private func handleResult(nonce: BTPaymentMethodNonce?, error: Error?, flutterResult: FlutterResult) {
        print("startThreeDSecureFlow handleResult called.")
        if error != nil {
            returnBraintreeError(result: flutterResult, error: error!)
        } else if nonce == nil {
            flutterResult(nil)
        } else {
            flutterResult(buildPaymentNonceDict(nonce: nonce));
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
                    paypalRequest.intent = BTPayPalRequestIntent.order
                case "sale":
                    paypalRequest.intent = BTPayPalRequestIntent.sale
                default:
                    paypalRequest.intent = BTPayPalRequestIntent.authorize
                }
            }
            if let userAction = requestInfo["payPalPaymentUserAction"] as? String {
                switch userAction {
                case "commit":
                    paypalRequest.userAction = BTPayPalRequestUserAction.commit
                default:
                    paypalRequest.userAction = BTPayPalRequestUserAction.default
                }
            }
            driver.tokenizePayPalAccount(with: paypalRequest) { (nonce, error) in
                self.handleResult(nonce: nonce, error: error, flutterResult: result)
                self.isHandlingResult = false
            }
        } else {
            let paypalRequest = BTPayPalVaultRequest()
            paypalRequest.displayName = requestInfo["displayName"] as? String
            paypalRequest.billingAgreementDescription = requestInfo["billingAgreementDescription"] as? String
            
            driver.tokenizePayPalAccount(with: paypalRequest) { (nonce, error) in
                self.handleResult(nonce: nonce, error: error, flutterResult: result)
                self.isHandlingResult = false
            }
        }
    }
    private func handleCreditCardTokenization(call: FlutterMethodCall, client: BTAPIClient, result: @escaping FlutterResult) {
        let cardClient = BTCardClient(apiClient: client)
        
        guard let cardRequestInfo = dict(for: "request", in: call) else {return}
        
        let card = BTCard()
        card.number = cardRequestInfo["cardNumber"] as? String
        card.expirationMonth = cardRequestInfo["expirationMonth"] as? String
        card.expirationYear = cardRequestInfo["expirationYear"] as? String
        card.cvv = cardRequestInfo["cvv"] as? String
        card.cardholderName = cardRequestInfo["cardholderName"] as? String
        
        cardClient.tokenizeCard(card) { (nonce, error) in
            self.handleResult(nonce: nonce, error: error, flutterResult: result)
            self.isHandlingResult = false
        }
    }
    
    private func startThreeDSecureFlow(call: FlutterMethodCall, client: BTAPIClient, result: @escaping FlutterResult) {
        guard let amount = dict(for: "request", in: call)?["amount"] as? String else {
            self.returnAuthorizationMissingError(result: result)
            self.isHandlingResult = false
            return
        }
         guard let nonce = dict(for: "request", in: call)?["nonce"] as? String else {
            self.returnAuthorizationMissingError(result: result)
            self.isHandlingResult = false
            return
        }
        print("startThreeDSecureFlow input nonce : \(nonce)")
        
        let threeDSecureRequest = BTThreeDSecureRequest()
        threeDSecureRequest.amount = NSDecimalNumber(string: amount)
        threeDSecureRequest.nonce = nonce
        threeDSecureRequest.email = dict(for: "request", in: call)?["email"] as? String
        
        print("startThreeDSecureFlow request created.")
        
        // let threeDSecureClient = BTThreeDSecureClient(apiClient: client)
        // threeDSecureClient.startPaymentFlow(threeDSecureRequest) { (threeDSecureResult, error) in
        //     print("startThreeDSecureFlow callback called.")
        //     self.handleResult(nonce: threeDSecureResult?.tokenizedCard, error: error, flutterResult: result)
        //     self.isHandlingResult = false
        // }

        let paymentFlowDriver = BTPaymentFlowDriver(apiClient: client)
        paymentFlowDriver.viewControllerPresentingDelegate = self

        threeDSecureRequest.threeDSecureRequestDelegate = self

        paymentFlowDriver.startPaymentFlow(threeDSecureRequest) { (paymentFlowResult, error) in
            print("startThreeDSecureFlow callback called.")
            let threeDSecureResult = paymentFlowResult as? BTThreeDSecureResult
            print("startThreeDSecureFlow callback BTThreeDSecureResult type called.")
            self.handleResult(nonce: threeDSecureResult?.tokenizedCard, error: error, flutterResult: result)
            self.isHandlingResult = false   
        }
    }

    public func onLookupComplete(_ request: BTThreeDSecureRequest, lookupResult: BTThreeDSecureResult, next: @escaping () -> Void) {
        next()
    }
  
    public func paymentDriver(_ driver: Any, requestsPresentationOf viewController: UIViewController) {
        // Present the view controller
        if let rootViewController = UIApplication.shared.keyWindow?.rootViewController {
            rootViewController.present(viewController, animated: true, completion: nil)
        }
    }
    
    public func paymentDriver(_ driver: Any, requestsDismissalOf viewController: UIViewController) {
        // Dismiss the view controller
        viewController.dismiss(animated: true, completion: nil)
    }
} 
