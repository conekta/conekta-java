package com.conekta;

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

    public static ConektaObject ConektaObjectFactory(JSONObject jsonObject, String attributeName) throws Error {
        ConektaObject conektaObject = new ConektaObject();
        if (jsonObject.has("object") && isPaymentMethod(jsonObject)) {
            conektaObject = PaymentMethodFactory(jsonObject);
            try {
                conektaObject.loadFromObject(jsonObject);
            } catch (Exception e) {
                throw new Error(e.toString(), null, null, null);
            }
        } else {
            try {
                conektaObject = (ConektaObject) Class.forName("com.conekta."+ConektaObject.toCamelCase(attributeName)).newInstance();
                conektaObject.loadFromObject(jsonObject);
            } catch (Exception e) {
                throw new Error(e.toString(), null, null, null);
            }
        }
        return conektaObject;
    }

    protected static PaymentMethod PaymentMethodFactory(JSONObject jsonObject) throws Error {
        PaymentMethod payment_method = null;
        if (isKindOfPaymentMethod(jsonObject, "card_payment")) {
            payment_method = new CardPayment(jsonObject);
        } else if (isKindOfPaymentMethod(jsonObject, "cash_payment")) {
            payment_method = new OxxoPayment(jsonObject);
        } else if (isKindOfPaymentMethod(jsonObject, "bank_transfer_payment")) {
            payment_method = new BankTransferPayment(jsonObject);
        }
        if (isPaymentMethod(jsonObject)) {
            try {
            payment_method.loadFromObject(jsonObject);
            } catch (Exception e) {
                throw new Error(e.toString(), null, null, null);
            }
            return payment_method;
        }
        throw new Error("Invalid PaymentMethod", null, null, null);
    }

    protected static Boolean isPaymentMethod(JSONObject jsonObject) throws Error {
        Boolean card_payment = isKindOfPaymentMethod(jsonObject, "card_payment");
        Boolean cash_payment = isKindOfPaymentMethod(jsonObject, "cash_payment");
        Boolean bank_transfer_payment = isKindOfPaymentMethod(jsonObject, "bank_transfer_payment");
        Boolean is_payment = card_payment || cash_payment || bank_transfer_payment;
        return is_payment;
    }

    protected static Boolean isKindOfPaymentMethod(JSONObject jsonObject, String kind) throws Error {
        try {
            return jsonObject.getString("object").equals(kind);
        } catch (Exception e) {
            throw new Error(e.toString(), null, null, null);
        }
    }
}
