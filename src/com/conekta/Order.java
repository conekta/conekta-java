/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conekta;

import java.util.HashMap;
import org.json.JSONObject;

/**
 *
 * @author mauriciomurga
 */
public class Order extends Resource {
    
    public String status;
    public String currency;
    public String customer_id;
    public Integer amount;
    public Integer created_at;
    public Boolean livemode;
    public HashMap customer_info = new HashMap();
    public HashMap contextual_data = new HashMap();
    public ConektaObject line_items;
    public ConektaObject shipping_lines;
    public ConektaObject tax_lines;
    public ConektaObject discount_lines;
    public ShippingDetails shipping_details;
    public FiscalEntity fiscal_entity;
    public ConektaObject charges;
    
    
    public Order(String id) {
        super(id);
    }

    public Order() {
        super();
    }

    public static Order find(String id) throws Error {
        String className = Order.class.getCanonicalName();
        return (Order) scpFind(className, id);
    }

    public static ConektaObject where(JSONObject params) throws Error {
        String className = Order.class.getCanonicalName();
        return (ConektaObject) scpWhere(className, params);
    }

    public static ConektaObject where() throws Error {
        String className = Order.class.getCanonicalName();
        return (ConektaObject) scpWhere(className, null);
    }

    public static Order create(JSONObject params) throws Error {
        String className = Order.class.getCanonicalName();
        return (Order) scpCreate(className, params);
    }
}
