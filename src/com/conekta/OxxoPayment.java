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
public class OxxoPayment extends PaymentMethod {

    public String barcode;
    public String barcode_url;
    public Integer expires_at;

    public OxxoPayment(JSONObject jsonObject) throws Error {
        super();
        //this.type = "cash_payment";
        try {
            this.barcode = jsonObject.getString("barcode");
            this.barcode_url = jsonObject.getString("barcode_url");
        } catch (Exception e) {
            throw new Error(e.toString(), null, null, null, null);
        }
    }
}
