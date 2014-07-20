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
public class BankTransferPayment extends PaymentMethod {

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