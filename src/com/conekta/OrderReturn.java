package com.conekta;

import java.util.HashMap;
import locales.Lang;

/**
 *
 * @author L.Carlos
 */
public class OrderReturn extends Resource {
    public Order order;
    public Integer amount;
    public String reason;
    public String currency;
    public LineItems[] items;
    public Boolean livemode;
    public String order_id;
    public String charge_id;
    
    @Override
    public String instanceUrl() throws Error {
        if (id == null || id.length() == 0) {
            HashMap parameters = new HashMap();
            parameters.put("RESOURCE", this.getClass().getSimpleName());
            throw new Error(Lang.translate("error.resource.id", parameters, Lang.EN),
                    Lang.translate("error.resource.id_purchaser", parameters, Conekta.locale), null, null, null);
        }
        String base = this.order.instanceUrl();
        return base + "/returns/" + id;
    }
}
