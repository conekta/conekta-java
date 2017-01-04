package com.conekta;

import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author L.Carlos
 */
public class Order extends Resource {
    public String status;
    public String currency;
    public String customer_id;
    public Integer amount;
    public Integer created_at;
    public Boolean livemode;
    public HashMap customer_info = new HashMap();
    public HashMap metadata = new HashMap();
    public HashMap last_payment_info = new HashMap();
    public HashMap transitions = new HashMap();
    public FiscalEntity fiscal_entity;
    public ShippingContact shipping_contact;

    public Order(String id) {
        super(id);
    }

    public Order() {
        super();
    }

    public static Order create(JSONObject params) throws ErrorList, Error {
        String className = Order.class.getCanonicalName();
        return (Order) scpCreate(className, params);
    }

    public static Order find(String id) throws ErrorList, Error {
        String className = Order.class.getCanonicalName();

        return (Order) scpFind(className, id);
    }
    
    public static ConektaList where(JSONObject params) throws Error, JSONException, ErrorList {
        String className = Order.class.getSimpleName();
        return (ConektaList) scpWhereList(className, params);
    }
    
    public FiscalEntity createFiscalEntity(JSONObject params) throws JSONException, Error, ErrorList{
        JSONObject updateParams = new JSONObject();
        updateParams.put("fiscal_entity", params);
        this.update(updateParams);
        
        return this.fiscal_entity;
    }

    public ShippingContact createShippingContact(JSONObject params) throws JSONException, Error, ErrorList{
        JSONObject updateParams = new JSONObject();
        updateParams.put("shipping_contact", params);
        this.update(updateParams);
        
        return this.shipping_contact;
    }
}
