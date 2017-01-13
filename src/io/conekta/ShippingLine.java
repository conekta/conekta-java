package io.conekta;

import java.util.HashMap;
import locales.Lang;
import org.json.JSONObject;

/**
 *
 * @author L.Carlos
 */
public class ShippingLine extends Resource {
    public Order order;
    public Boolean deleted;
    public String tracking_number;
    public String description;
    public String carrier;
    public Integer amount;
    public Integer method;
    public HashMap metadata = new HashMap();

    @Override
    public String instanceUrl() throws Error {
        if (id == null || id.length() == 0) {
            HashMap parameters = new HashMap();
            parameters.put("RESOURCE", this.getClass().getSimpleName());
            throw new Error(Lang.translate("error.resource.id", parameters, Lang.EN),
                    Lang.translate("error.resource.id_purchaser", parameters, Conekta.locale), null, null, null);
        }
        String base = this.order.instanceUrl();
        return base + "/shipping_lines/" + id;
    }
    
    @Override
    public void update(JSONObject params) throws Error, ErrorList {
        super.update(params);
    }

    public ShippingLine delete() throws Error, ErrorList {
        return ((ShippingLine) this.delete(null, null));
    }
}
