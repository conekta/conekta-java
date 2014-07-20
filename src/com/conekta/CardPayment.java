/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.conekta;

import org.json.JSONObject;

/**
 *
 * @author mauricio
 */
public class CardPayment extends PaymentMethod {

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
