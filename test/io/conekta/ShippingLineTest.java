package io.conekta;

import static junit.framework.TestCase.assertTrue;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author L.Carlos
 */
public class ShippingLineTest extends ConektaBase {
    JSONObject validOrder;

    public ShippingLineTest() throws JSONException {
        super();
        setApiVersion("2.0.0");
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
            "  }]," +
            "'shipping_lines': [" +
            "    {" +
            "        'amount': 0," +
            "        'tracking_number': 'TRACK123'," +
            "        'carrier': 'USPS'," +
            "        'method': 'Train'" +
            "    }" +
            "]" +
            "}"
        );

    }

    // @Test
    public void testShippingLineDelete() throws ErrorList, Error{
        Order order = Order.create(validOrder);

        ShippingLine shippingLine = (ShippingLine) order.shipping_lines.get(0);

        shippingLine.delete();

        assertTrue(shippingLine.deleted);
    }

    // @Test
    public void testShippingLineUpdate() throws ErrorList, Error, JSONException{
        Order order = Order.create(validOrder);

        ShippingLine shippingLine = (ShippingLine) order.shipping_lines.get(0);

        shippingLine.update(new JSONObject("{'tracking_number': 'TRACK124'}"));

        assertTrue(shippingLine.tracking_number.equals("TRACK124"));
    }
}
