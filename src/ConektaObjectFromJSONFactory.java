
import org.json.JSONException;
import org.json.JSONObject;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mauricio
 */
public abstract class ConektaObjectFromJSONFactory {
    public static ConektaObject ConektaObjectFactory(JSONObject jsonObject, String attributeName) throws JSONException, Exception{
        ConektaObject conektaObject = new ConektaObject();
        if (isPaymentMethod(jsonObject)) {
            conektaObject = PaymentMethodFactory(jsonObject);
            conektaObject.loadFromObject(jsonObject);
        } else {
            conektaObject = (ConektaObject) Class.forName(ConektaObject.toCamelCase(attributeName)).newInstance();
            conektaObject.loadFromObject(jsonObject);
        }
        return conektaObject;
    }

    protected static PaymentMethod PaymentMethodFactory(JSONObject jsonObject) throws JSONException, Exception {
        PaymentMethod payment_method = null;
        if (isKindOfPaymentMethod(jsonObject,"card_payment")) {
            payment_method = new CardPayment(jsonObject);
        } else if (isKindOfPaymentMethod(jsonObject,"cash_payment")) {
            payment_method = new OxxoPayment(jsonObject);
        } else if (isKindOfPaymentMethod(jsonObject,"bank_transfer_payment")) {
            payment_method = new BankTransferPayment(jsonObject);
        }
        if (isPaymentMethod(jsonObject)) {
            payment_method.loadFromObject(jsonObject);
            return payment_method;
        }
        throw new Exception("Invalid PaymentMethod");
    }

    protected static Boolean isPaymentMethod(JSONObject jsonObject) throws JSONException {
        Boolean card_payment = isKindOfPaymentMethod(jsonObject, "card_payment");
        Boolean cash_payment = isKindOfPaymentMethod(jsonObject,"cash_payment");
        Boolean bank_transfer_payment = isKindOfPaymentMethod(jsonObject,"bank_transfer_payment");
        Boolean is_payment = card_payment || cash_payment || bank_transfer_payment;
        return is_payment;
    }

    protected static Boolean isKindOfPaymentMethod(JSONObject jsonObject, String kind) throws JSONException {
        return jsonObject.getString("object").equals(kind);
    }
}
