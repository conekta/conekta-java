package io.conekta;

import java.util.HashMap;
import locales.Lang;
import org.json.JSONObject;

/**
 *
 * @author L.Carlos
 */
public class Source extends Resource{
    public Customer customer;
    public String type;
    public String name;
    public String exp_month;
    public String exp_year;
    public String cvc;
    public Address address;
    public Boolean deleted;
    public Integer expires_at;
    
    @Override
    public String instanceUrl() throws Error {
        if (id == null || id.length() == 0) {
            HashMap parameters = new HashMap();
            parameters.put("RESOURCE", this.getClass().getSimpleName());
            throw new Error(Lang.translate("error.resource.id", parameters, Lang.EN),
                    Lang.translate("error.resource.id_purchaser", parameters, Conekta.locale), null, null, null);
        }
        String base = this.customer.instanceUrl();
        return base + "/sources/" + id;
    }
    
    @Override
    public void update(JSONObject params) throws Error, ErrorList {
        super.update(params);
    }

    public Source delete() throws Error, ErrorList {
        return ((Source) this.delete(null, null));
    }
}
