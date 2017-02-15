package io.conekta;

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

    public static Webhook find(String id) throws Error, ErrorList {
        String className = Webhook.class.getCanonicalName();
        return (Webhook) scpFind(className, id);
    }

    public static Webhook create(JSONObject params) throws Error, ErrorList {
        String className = Webhook.class.getCanonicalName();
        return (Webhook) scpCreate(className, params);
    }

    public void update(JSONObject params) throws Error, ErrorList {
        super.update(params);
    }
    
    public void delete() throws Error, ErrorList {
        this.delete(null, null);
    }
}