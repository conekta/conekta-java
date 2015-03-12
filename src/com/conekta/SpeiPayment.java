/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.conekta;

import org.json.JSONObject;

/**
 *
 * @author mauricio
 */
public class SpeiPayment  extends PaymentMethod {

    public String clabe;
    public String tracking_code;
    public String bank;

    public SpeiPayment(JSONObject jsonObject) throws Error {
        super();
        //this.type = "real_time_payment";
        try {
            this.clabe = jsonObject.getString("clabe");
            this.tracking_code = jsonObject.getString("tracking_code");
            this.bank = jsonObject.getString("bank");
        } catch (Exception e) {
            throw new Error(e.toString(), null, null, null, null);
        }
    }
}
