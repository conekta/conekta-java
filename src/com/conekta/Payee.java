/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conekta;

import org.json.JSONObject;

/**
 *
 * @author picharras
 */
public class Payee extends Resource{
    public Boolean livemode;
    
    public Payee(String id) {
        super(id);
    }

    public Payee() {
        super();
    }

    public static Payee find(String id) throws Error {
        String className = Charge.class.getCanonicalName();
        return (Payee) scpFind(className, id);
    }

    public static ConektaObject where(JSONObject params) throws Error {
        String className = Payee.class.getCanonicalName();
        return (ConektaObject) scpWhere(className, params);
    }

    public static ConektaObject where() throws Error {
        String className = Payee.class.getCanonicalName();
        return (ConektaObject) scpWhere(className, null);
    }

    public static Payee create(JSONObject params) throws Error {
        String className = Payee.class.getCanonicalName();
        return (Payee) scpCreate(className, params);
    }

    public void delete() throws Error {
        this.delete(null, null);
    }

    @Override
    public void update(JSONObject params) throws Error {
        super.update(params);
    }
}
