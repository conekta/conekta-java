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
    public String reference;
    public String barcode_url;
    public Integer expires_at;

    public OxxoPayment(JSONObject jsonObject) throws Error {
        super();
        //this.type = "cash_payment";
        try {
            if (jsonObject.has("barcode")) {
                this.barcode = jsonObject.getString("barcode");
            } else {
                this.barcode = this.reference = jsonObject.getString("reference");
            }
            this.barcode_url = jsonObject.getString("barcode_url");
        } catch (Exception e) {
            throw new Error(e.toString(), null, null, null, null);
        }
    }
}
