package com.conekta;

import static junit.framework.TestCase.assertTrue;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author L.Carlos
 */
public class DiscountLineTest extends ConektaTest {
    JSONObject validOrder;
    
    public DiscountLineTest() throws JSONException {
        super();
        setApiVersion("1.1.0");
        validOrder = new JSONObject(
            "{ 'currency': 'mxn'," +
            "  'metadata': {" +
            "    'test': true"+ 
            " }," +
            " 'line_items': [{" +
            "    'name': 'Box of Cohiba S1s'," +
            "    'description': 'Imported From Mex.'," +
            "    'unit_price': 35000," +
            "    'quantity': 1," +
            "    'tags': ['food', 'mexican food']," +
            "    'type': 'physical'" +
            "  }],"
            + "'discount_lines': [" +
            "    {" +
            "        'code': 'Cupon de descuento'," +
            "        'amount': 10," +
            "        'type': 'loyalty'" +
            "    }," +
            "    {" +
            "        'code': 'Cupon de descuento'," +
            "        'amount': 5," +
            "        'type': 'loyalty'" +
            "    }" +
            "]" +
            "}"
        );

    }

    // @Test
    public void testDiscountLineDelete() throws ErrorList, Error{
        Order order = Order.create(validOrder);
        
        DiscountLine discountLine = (DiscountLine) order.discount_lines.get(0);
        
        discountLine.delete();
        
        assertTrue(discountLine.deleted); 
    }
    
    // @Test
    public void testDiscountLineUpdate() throws ErrorList, Error, JSONException{
        Order order = Order.create(validOrder);
        
        DiscountLine discountLine = (DiscountLine) order.discount_lines.get(0);
        
        discountLine.update(new JSONObject("{'amount': 11}"));
        
        assertTrue(discountLine.amount == 11);
    }
}
