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
public abstract class PaymentMethod extends ConektaObject {
    public String type;
}

class CardPayment extends PaymentMethod {

    public String brand;
    public String auth_code;
    public String last4;
    public String exp_month;
    public String exp_year;
    public String name;

    public CardPayment(JSONObject jsonObject) throws Error {
        super();
        //this.type = "card_payment";
        try {
            this.brand = jsonObject.getString("brand");
            this.auth_code = jsonObject.getString("auth_code");
            this.last4 = jsonObject.getString("last4");
            this.exp_month = jsonObject.getString("exp_month");
            this.exp_year = jsonObject.getString("exp_year");
            this.name = jsonObject.getString("name");
        } catch (Exception e) {
            throw new Error(e.toString(), null, null, null);
        }
    }
}

class OxxoPayment extends PaymentMethod {

    public String expiry_date;
    public String barcode;
    public String barcode_url;
    public Integer expires_at;

    public OxxoPayment(JSONObject jsonObject) throws Error {
        super();
        //this.type = "cash_payment";
        try {
            this.expiry_date = jsonObject.getString("expiry_date");
            this.barcode = jsonObject.getString("barcode");
            this.barcode_url = jsonObject.getString("barcode_url");
        } catch (Exception e) {
            throw new Error(e.toString(), null, null, null);
        }
    }
}

class BankTransferPayment extends PaymentMethod {

    public String service_name;
    public String service_number;
    public String reference;

    public BankTransferPayment(JSONObject jsonObject) throws Error {
        super();
        //this.type = "bank_transfer_payment";
        try {
            this.service_name = jsonObject.getString("service_name");
            this.service_number = jsonObject.getString("service_number");
            this.reference = jsonObject.getString("reference");
        } catch (Exception e) {
            throw new Error(e.toString(), null, null, null);
        }
    }
}
