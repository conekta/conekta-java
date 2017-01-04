package com.conekta;

import java.util.HashMap;
import locales.Lang;
import org.json.JSONObject;

/**
 *
 * @author L.Carlos
 */
public class ShippingContact extends Resource{
    public Customer customer;
    public String name;
    public String email;
    public String receiver;
    public Boolean deleted;
    public Address address;

    @Override
    public String instanceUrl() throws Error {
        if (id == null || id.length() == 0) {
            HashMap parameters = new HashMap();
            parameters.put("RESOURCE", this.getClass().getSimpleName());
            throw new Error(Lang.translate("error.resource.id", parameters, Lang.EN),
                    Lang.translate("error.resource.id_purchaser", parameters, Conekta.locale), null, null, null);
        }
        String base = this.customer.instanceUrl();
        return base + "/shipping_contacts/" + id;
    }
    
    @Override
    public void update(JSONObject params) throws Error, ErrorList {
        super.update(params);
    }

    public ShippingContact delete() throws Error, ErrorList {
        return ((ShippingContact) this.delete(null, null));
    }
}
