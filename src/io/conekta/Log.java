package io.conekta;

import org.json.JSONObject;

/**
 * @author picharras
 */
public class Log extends Resource {
    public String id;
    public Boolean livemode;
    public String method;
    public String url;
    public String ip_address;
    public String related;
    public RequestBody request_body;
    public String response_body;
    public String status;
    public String version;
    public String request_headers;
    public ConektaObject response_headers;

    public Log(String id) {
        super(id);
    }

    public Log() {
        super();
    }
    
    public static Log find(String id) throws Error, ErrorList {
        String className = Log.class.getCanonicalName();
        return (Log) scpFind(className, id);
    }

    public static ConektaObject where(JSONObject params) throws Error, ErrorList {
        String className = Log.class.getCanonicalName();
        return scpWhere(className, params);
    }

    public static ConektaObject where() throws Error, ErrorList {
        String className = Log.class.getCanonicalName();
        return scpWhere(className, null);
    }
}