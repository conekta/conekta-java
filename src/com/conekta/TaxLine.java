package com.conekta;

import java.util.HashMap;
import locales.Lang;
import org.json.JSONObject;

/**
 *
 * @author L.Carlos
 */
public class TaxLine extends Resource {
    public Order order;
    public Integer amount;
    public String description;
    public Boolean deleted;
    public HashMap metadata;

    @Override
    public String instanceUrl() throws Error {
        if (id == null || id.length() == 0) {
            HashMap parameters = new HashMap();
            parameters.put("RESOURCE", this.getClass().getSimpleName());
            throw new Error(Lang.translate("error.resource.id", parameters, Lang.EN),
                    Lang.translate("error.resource.id_purchaser", parameters, Conekta.locale), null, null, null);
        }
        String base = this.order.instanceUrl();
        return base + "/tax_lines/" + id;
    }
    
    @Override
    public void update(JSONObject params) throws Error, ErrorList {
        super.update(params);
    }

    public TaxLine delete() throws Error, ErrorList {
        return ((TaxLine) this.delete(null, null));
    }
}
