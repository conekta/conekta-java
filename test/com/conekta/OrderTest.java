package com.conekta;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author L.Carlos
 */
public class OrderTest extends ConektaTest{
    
    JSONObject validOrder;
    
    public OrderTest() throws JSONException {
        super();
        setApiVersion("1.1.0");
        setApiBase("http://localhost:61440");
        setApiKey("WVWIDUUBc4ca4238a0b9");
        
        validOrder = new JSONObject(
            "{ 'currency': 'mxn'," +
            "  'line_items': [{" +
            "    'name': 'Box of Cohiba S1s'," +
            "    'description': 'Imported From Mex.'," +
            "    'unit_price': 35000," +
            "    'quantity': 1," +
            "    'tags': ['food', 'mexican food']," +
            "    'type': 'physical'" +
            "  }]" +
            "}"
        );
    }
    
    //@Test
    public void testSuccesfulCreateCustomer() throws Exception {
        Order order = Order.create(validOrder);
        
        assertTrue(order instanceof Order);
    }
}
