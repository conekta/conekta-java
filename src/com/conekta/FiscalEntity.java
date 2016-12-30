package com.conekta;

import java.util.HashMap;
import locales.Lang;
import org.json.JSONObject;

/**
 *
 * @author L.Carlos
 */
public class FiscalEntity extends Resource {
    
    public String tax_id;
    public String company_name;
    public String email;
    public String phone;
    public Boolean deleted;
    public Address address;
    public Customer customer;
    
    @Override
    public String instanceUrl() throws Error {
        if (id == null || id.length() == 0) {
            HashMap parameters = new HashMap();
            parameters.put("RESOURCE", this.getClass().getSimpleName());
            throw new Error(Lang.translate("error.resource.id", parameters, Lang.EN),
                    Lang.translate("error.resource.id_purchaser", parameters, Conekta.locale), null, null, null);
        }
        String base = this.customer.instanceUrl();
        return base + "/fiscal_entities/" + id;
    }
    
    @Override
    public void update(JSONObject params) throws Error, ErrorList {
        super.update(params);
    }

    public FiscalEntity delete() throws Error, ErrorList {
        return ((FiscalEntity) this.delete(null, null));
    }
}
