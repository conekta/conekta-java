package io.conekta;

import java.util.HashMap;
import locales.Lang;
import org.json.JSONObject;

/**
 *
 * @author mauricio
 */
public class Subscription extends Resource {
    public Customer customer;
    public String status;
    public Integer created_at;
    public Integer billing_cycle_start;
    public Integer billing_cycle_end;
    public String plan_id;
    public String customer_id;
    public String card_id;

    @Override
    public String instanceUrl() throws Error {
        if (id == null || id.length() == 0) {
            HashMap parameters = new HashMap();
            parameters.put("RESOURCE", this.getClass().getSimpleName());
            throw new Error(Lang.translate("error.resource.id", parameters, Lang.EN),
                    Lang.translate("error.resource.id_purchaser", parameters, Conekta.locale), null, null, null);
        }
        String base = "/customers/"+ this.customer_id;
        return base + "/subscription";
    }

    @Override
    public void update(JSONObject params) throws Error, ErrorList {
        super.update(params);
    }

    public void pause() throws Error, ErrorList {
        this.customAction("POST", "pause", null);
    }

    public void cancel() throws Error, ErrorList {
        this.customAction("POST", "cancel", null);
    }

    public void resume() throws Error, ErrorList {
        this.customAction("POST", "resume", null);
    }
}
