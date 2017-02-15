package io.conekta;

import org.json.JSONObject;

/**
 *
 * @author mauricio
 */
public class Plan extends Resource {

    public Boolean livemode;
    public Boolean deleted;
    public Integer created_at;
    public String name;
    public String interval;
    public Integer frequency;
    public Integer interval_total_count;
    public Integer trial_period_days;
    public String currency;
    public Integer amount;

    public Plan(String id) {
        super(id);
    }

    public Plan() {
        super();
    }

    public static Plan find(String id) throws Error, ErrorList {
        String className = Plan.class.getCanonicalName();
        return (Plan) scpFind(className, id);
    }

    public static ConektaObject where(JSONObject params) throws Error, ErrorList {
        String className = Plan.class.getCanonicalName();
        return (ConektaObject) scpWhere(className, params);
    }

    public static ConektaObject where() throws Error, ErrorList {
        String className = Plan.class.getCanonicalName();
        return (ConektaObject) scpWhere(className, null);
    }

    public static Plan create(JSONObject params) throws Error, ErrorList {
        String className = Plan.class.getCanonicalName();
        return (Plan) scpCreate(className, params);
    }

    public void delete() throws Error, ErrorList {
        this.delete(null, null);
    }
}
