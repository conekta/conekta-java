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
    public String clave_rastreo;
    public String nombre_ordenante;
    public Integer expected_amount;

    public SpeiPayment(JSONObject jsonObject) throws Error {
        super();
        //this.type = "real_time_payment";
        try {
            this.clabe = jsonObject.getString("clabe");
            this.clave_rastreo = jsonObject.getString("clave_rastreo");
            this.nombre_ordenante = jsonObject.getString("nombre_ordenante");
            this.expected_amount = Integer.parseInt(jsonObject.getString("expected_amount"));
        } catch (Exception e) {
            throw new Error(e.toString(), null, null, null);
        }
    }
}
