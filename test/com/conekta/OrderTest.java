/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conekta;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author mauriciomurga
 */
public class OrderTest extends ConektaTest {
    
    JSONObject params;
    JSONObject charges;
    

    public OrderTest() throws JSONException {
        super();
        setApiVersion("1.1.0");
        setApiBase("http://localhost:3000");
        setApiKey("PZAHFXPDb53b3a3d6ab9");
        
        params = new JSONObject("{"
                + "'currency': 'mxn',"
                + "'line_items': [{"
                + "'name': 'Box of Cohiba S1s',"
                + "'description': 'Imported From Mex.',"
                + "'unit_price': 35000,"
                + "'quantity': 1,"
                + "'tags': ['food', 'mexican food'],"
                + "'type': 'physical'"
                + " }]"
                + "}");
        charges = new JSONObject("{"
                + "'charges': [{"
                + "'source': {"
                + "'type': 'oxxo_cash'"
                + "},"
                + "amount: 3500"
                + "}]"
                + "}"
        );
    }
    
    public Order createBasicOrder() throws Exception {
        Order order = Order.create(params);
        return order;
    }
    
    //@Test
    public void testCreateBasicOrder() throws Exception {
        Order order = createBasicOrder();
        assertTrue(order.livemode == false);
        assertTrue(order.contextual_data.isEmpty());
        assertTrue(order.amount == 35000);
        assertTrue(order.status.equals("created"));
        assertTrue(order.customer_id == null);
        assertTrue(order.currency.equals("MXN"));
        assertTrue(order.customer_info.isEmpty());
        assertTrue(order.line_items.get(0) instanceof LineItems);
        assertTrue(((LineItems)order.line_items.get(0)).name.equals("Box of Cohiba S1s"));
        assertTrue(((LineItems)order.line_items.get(0)).description.equals("Imported From Mex."));
        assertTrue(((LineItems)order.line_items.get(0)).unit_price == 35000);
        assertTrue(((LineItems)order.line_items.get(0)).quantity == 1);
        assertTrue(((LineItems)order.line_items.get(0)).sku == null);
        assertTrue(((LineItems)order.line_items.get(0)).shippable == null);
        assertTrue(((LineItems)order.line_items.get(0)).tags.isEmpty());
        assertTrue(((LineItems)order.line_items.get(0)).brand == null);
        assertTrue(((LineItems)order.line_items.get(0)).type.equals("physical"));
        assertTrue(((LineItems)order.line_items.get(0)).contextual_data.isEmpty());
        assertTrue(((LineItems)order.line_items.get(0)).parent_id.equals(order.id));
        assertTrue(order.shipping_details == null);
        assertTrue(order.fiscal_entity == null);
        assertTrue(order.charges == null);
        assertTrue(order.created_at instanceof Integer);
    }
}
