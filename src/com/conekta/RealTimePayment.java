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
public class RealTimePayment extends PaymentMethod {
    public String store_name;
    public String barcode;
    public String barcode_url;

    public RealTimePayment(JSONObject jsonObject) throws Error {
        super();
        //this.type = "real_time_payment";
        try {
            this.store_name = jsonObject.getString("store_name");
            this.barcode = jsonObject.getString("barcode");
            this.barcode_url = jsonObject.getString("barcode_url");
        } catch (Exception e) {
            throw new Error(e.toString(), null, null, null, null);
        }
    }
}
