package com.conekta;


import org.json.JSONObject;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mauricio
 */
public class Event extends Resource {

    public Boolean livemode;
    public Integer created_at;
    public String type;

    public Event(String id) {
        super(id);
    }

    public Event() {
        super();
    }    

    public static ConektaObject where(JSONObject params) throws Error {
        String className = Event.class.getCanonicalName();
        return (ConektaObject) scpWhere(className, params);
    }

    public static ConektaObject where() throws Error {
        String className = Event.class.getCanonicalName();
        return (ConektaObject) scpWhere(className, null);
    }

}
