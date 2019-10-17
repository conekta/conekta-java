package io.conekta;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author L.Carlos
 */
public class TaxLineTest extends ConektaBase{

     JSONObject validOrder;

    public TaxLineTest() throws JSONException {
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
            "'tax_lines': [{" +
                "  'description': 'IVA'," +
                "  'amount': 60" +
                "}, {" +
                "  'description': 'ISR'," +
                "  'amount': 6" +
                "}]" +
            "}"
        );
    }

    // @Test
    public void testFiscalEntityDelete() throws ErrorList, Error{
        setApiVersion("2.0.0");
        Order order = Order.create(validOrder);

        TaxLine taxLine = (TaxLine) order.tax_lines.get(0);

        taxLine.delete();

        assertTrue(taxLine.deleted);
    }

    // @Test
    public void testFiscalEntityUpdate() throws ErrorList, Error, JSONException{
        Order order = Order.create(validOrder);

        TaxLine taxLine = (TaxLine) order.tax_lines.get(0);

        taxLine.update(new JSONObject("{ 'amount': 10 }"));

        assertTrue(taxLine.amount == 10);
    }
}
