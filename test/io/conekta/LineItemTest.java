package io.conekta;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author L.Carlos
 */
public class LineItemTest extends ConektaBase{

    JSONObject validOrder;

    public LineItemTest() throws JSONException {
        validOrder = new JSONObject(
            "{ 'currency': 'mxn'," +
            "  'metadata': {" +
            "    'test': true"+
            "  }," +
            "  line_items: [{" +
            "    'name': 'Box of Cohiba S1s'," +
            "    'description': 'Imported From Mex.'," +
            "    'unit_price': 20000," +
            "    'quantity': 1," +
            "    'type': 'physical'," +
            "    'tags': ['food', 'mexican food']" +
            "  }, {" +
            "    'name': 'Box of Romeo y Julueta'," +
            "    'description': 'Imported From Cuba.'," +
            "    'unit_price': 20001," +
            "    'quantity': 1," +
            "    'type': 'physical'," +
            "    'tags': ['food', 'mexican food']" +
            "  }]" +
            "}"
        );
    }

    // @Test
    public void testLineItemsDelete() throws ErrorList, Error{
        Order order = Order.create(validOrder);

        LineItems lineItem = (LineItems) order.line_items.get(0);

        lineItem.delete();

        assertTrue(lineItem.deleted);
    }

    // @Test
    public void testLineItemsUpdate() throws ErrorList, Error, JSONException{
        Order order = Order.create(validOrder);

        LineItems lineItem = (LineItems) order.line_items.get(0);

        lineItem.update(new JSONObject("{'name': 'Box of Romeo y Julieta'}"));

        assertTrue(lineItem.name.equals("Box of Romeo y Julieta"));
    }
}
