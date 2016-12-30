package com.conekta;


import org.json.JSONObject;

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

    public static ConektaObject where(JSONObject params) throws Error, ErrorList {
        String className = Event.class.getCanonicalName();
        return (ConektaObject) scpWhere(className, params);
    }

    public static ConektaObject where() throws Error, ErrorList {
        String className = Event.class.getCanonicalName();
        return (ConektaObject) scpWhere(className, null);
    }

}
