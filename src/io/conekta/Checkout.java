package io.conekta;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author L.Carlos
 */
public class Checkout extends Resource {
    public List<Integer> monthly_installments_options;
    public List<String> allowed_payment_methods;
    public Integer created_at;
    public String currency;
    public Integer emails_sent;
    public Integer expired_at;
    public Integer expires_at;
    public Boolean force_3ds_flow;
    public Boolean livemode;
    public HashMap metadata = new HashMap();
    public Boolean monthly_installments_enabled;
    public String name;
    public Boolean needs_shipping_contact;
    public String object;
    public Boolean on_demand_enabled;
    public Integer paid_payments_count;
    public Boolean recurrent;
    public String slug;
    public Integer sms_sent;
    public String status;
    public String type;
    public String url;
    public Order order_template;

    public Checkout(String id) {
        super(id);
    }

    public Checkout() {
        super();
    }

    @Override
    public void loadFromObject(JSONObject jsonObject) throws Exception {
        if (jsonObject != null) {
            try {
                super.loadFromObject(jsonObject);
            } catch (Exception e) {
                throw new Error(e.toString(), null, null, null, null);
            }
        }
    }

    public void reload() throws Exception{
        Requestor requestor = new Requestor();
        JSONObject orderJson = (JSONObject) requestor.request("GET", this.instanceUrl(), null);
        this.loadFromObject(orderJson);
    }

    public static Checkout create(JSONObject params) throws ErrorList, Error {
        String className = Checkout.class.getCanonicalName();
        return (Checkout) scpCreate(className, params);
    }

    public static Checkout find(String id) throws ErrorList, Error {
        String className = Checkout.class.getCanonicalName();

        return (Checkout) scpFind(className, id);
    }

    public static ConektaList where(JSONObject params) throws Error, JSONException, ErrorList {
        String className = Checkout.class.getSimpleName();
        return (ConektaList) scpWhereList(className, params);
    }


    public Checkout cancel() throws Exception {
        Checkout order = (Checkout) this.customAction("PUT", "cancel", null);
        this.reload();
        return order;
    }

    public Checkout sendSms(String phone) throws Exception {
        JSONObject params  = new JSONObject();
        params.put("phone", phone);
        Checkout order = (Checkout) this.customAction("POST", "sms", params);
        this.reload();
        return order;
    }

    public Checkout sendEmail(String email) throws Exception {
        JSONObject params  = new JSONObject();
        params.put("email", email);
        Checkout order = (Checkout) this.customAction("POST", "email", params);
        this.reload();
        return order;
    }

    @Override
    public void update(JSONObject params) throws Error, ErrorList {
        super.update(params);
    }

    public void delete() throws Error, ErrorList {
        this.delete(null, null);
    }
}
