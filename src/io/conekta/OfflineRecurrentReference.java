package io.conekta;

import locales.Lang;

import java.util.HashMap;

public class OfflineRecurrentReference extends PaymentSource {
    public String reference;
    public String barcode;
    public String barcode_url;
    public String provider;
    public String expires_at;



    @Override
    public String instanceUrl() throws Error {
        if (id == null || id.length() == 0) {
            HashMap parameters = new HashMap();
            parameters.put("RESOURCE", this.getClass().getSimpleName());
            throw new Error(Lang.translate("error.resource.id", parameters, Lang.EN),
                    Lang.translate("error.resource.id_purchaser", parameters, Conekta.locale), null, null, null);
        }
        String base = this.customer.instanceUrl();

        return base + "/payment_sources/" + id;
    }
}
