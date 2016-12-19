/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conekta;

import org.json.JSONObject;

/**
 *
 * @author L.Carlos
 */
public class Order extends Resource {
    public Order(String id) {
        super(id);
    }

    public Order() {
        super();
    }
    
    @Override
    public void loadFromObject(JSONObject jsonObject) throws Error {
        if (jsonObject != null) {
            try {
                super.loadFromObject(jsonObject);
            } catch (Exception e) {
                throw new Error(e.toString(), null, null, null, null);
            }
        }
    }
    
    public static Order create(JSONObject params) throws Error {
        String className = Order.class.getCanonicalName();
        return (Order) scpCreate(className, params);
    }

}
