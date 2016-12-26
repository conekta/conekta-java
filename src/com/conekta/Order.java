package com.conekta;

import java.util.HashMap;
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
    
    public Order(String id) {
        super(id);
    }

    public Order() {
        super();
    }
    
    public static Order create(JSONObject params) throws Error {
        String className = Order.class.getCanonicalName();
        return (Order) scpCreate(className, params);
    }
    
    public static Order find(String id) throws Error {
        String className = Order.class.getCanonicalName();
        
        return (Order) scpFind(className, id);
    }
}
