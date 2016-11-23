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
public class Webhook extends Resource {
    public Boolean development_enabled;
    public Boolean production_enabled;
    public String status;
    public String url;
    public String[] subscribed_events;

    public Webhook(String id) {
        super(id);
        subscribed_events = new String[20];
    }

    public Webhook() {
        super();
    }

    public static Webhook find(String id) throws Error {
        String className = Webhook.class.getCanonicalName();
        return (Webhook) scpFind(className, id);
    }

    public static Webhook create(JSONObject params) throws Error {
        String className = Webhook.class.getCanonicalName();
        return (Webhook) scpCreate(className, params);
    }

    public void update(JSONObject params) throws Error {
        super.update(params);
    }
    
    public void delete() throws Error {
        this.delete(null, null);
    }
}