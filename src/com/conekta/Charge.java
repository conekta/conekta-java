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
public class Charge extends Resource {

    public Boolean livemode;
    public Integer created_at;
    public String status;
    public String currency;
    public String description;
    public String reference_id;
    public String failure_code;
    public String failure_message;
    public Integer amount;
    public PaymentMethod payment_method;
    public Details details;
    public Integer fee;
    public Integer monthly_installments;
    public ConektaObject refunds;

    public Charge(String id) {
        super(id);
    }

    public Charge() {
        super();
    }

    public static Charge find(String id) throws Error {
        String className = Charge.class.getCanonicalName();
        return (Charge) scpFind(className, id);
    }

    public static ConektaObject where(JSONObject params) throws Error {
        String className = Charge.class.getCanonicalName();
        return (ConektaObject) scpWhere(className, params);
    }

    public static ConektaObject where() throws Error {
        String className = Charge.class.getCanonicalName();
        return (ConektaObject) scpWhere(className, null);
    }

    public static Charge create(JSONObject params) throws Error {
        String className = Charge.class.getCanonicalName();
        return (Charge) scpCreate(className, params);
    }

    public Charge capture() throws Error {
        return (Charge) customAction("POST", "capture", null);
    }

    public Charge refund(Integer amount) throws Error {
        JSONObject params;
        try {
            params = new JSONObject("{'amount':" + amount + "}");
        } catch(Exception e) {
            throw new Error(e.toString(), null, null, null, null);
        }
        return (Charge) customAction("POST", "refund", params);
    }

    public Charge refund() throws Error {
        JSONObject params;
        try {
            params = new JSONObject("{'amount':" + amount + "}");
        } catch(Exception e) {
            throw new Error(e.toString(), null, null, null, null);
        }
        return (Charge) customAction("POST", "refund", params);
    }
}