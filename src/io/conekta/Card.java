package io.conekta;


import java.util.HashMap;
import locales.Lang;
import org.json.JSONObject;

/**
 *
 * @author mauricio
 */
public class Card extends PaymentSource {
    public String name;
    public String last4;
    public String type;
    public String bin;
    public String brand;
    public String cvc;
    public Address address;
    public String exp_month;
    public String exp_year;

    @Override
    public String instanceUrl() throws Error {
        if (id == null || id.length() == 0) {
            HashMap parameters = new HashMap();
            parameters.put("RESOURCE", this.getClass().getSimpleName());
            throw new Error(Lang.translate("error.resource.id", parameters, Lang.EN),
                    Lang.translate("error.resource.id_purchaser", parameters, Conekta.locale), null, null, null);
        }
        String base = this.customer.instanceUrl();

        if(Conekta.apiVersion.equals("1.0.0")){
            return base + "/cards/" + id;
        }
        return base + "/payment_sources/" + id;
    }

    @Override
    public void update(JSONObject params) throws Error, ErrorList {
        super.update(params);
    }

    // this method delete a card in the cards array from customer.
    @Override
    public Card delete() throws Error, ErrorList {
        return (Card) this.delete("customer", "cards");
    }
}
