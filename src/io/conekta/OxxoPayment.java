/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package io.conekta;

import org.json.JSONObject;

/**
 *
 * @author mauricio
 */
public class OxxoPayment extends PaymentMethod {

    public String barcode;
    public String barcode_url;
    public Integer expires_at;
    public String store_name;
    public String reference;
    public String service_name;

    public OxxoPayment(JSONObject jsonObject) throws Error {
        super();
        try {
            this.barcode = jsonObject.optString("barcode");
            this.barcode_url = jsonObject.optString("barcode_url");
        } catch (Exception e) {
            throw new Error(e.toString(), null, null, null, null);
        }
    }
}
