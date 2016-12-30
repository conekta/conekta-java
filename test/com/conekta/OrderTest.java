package com.conekta;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author L.Carlos
 */
public class OrderTest extends ConektaTest{

    JSONObject validOrder;
    JSONObject customerInfo;

    public OrderTest() throws JSONException {
        super();
        setApiVersion("1.1.0");

        validOrder = new JSONObject(
            "{ 'currency': 'mxn'," +
            "  'metadata': {" +
            "    'test': true"+
            " }," +
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

        customerInfo = new JSONObject(
            "{ 'name': 'John Constantine'," +
            "  'phone': '+5213353319758'," +
            "  'email': 'hola@hola.com'" +
            "}"
        );
    }

    //@Test
    public void testSuccesfulOrderCreate() throws Exception {
        Order order = Order.create(validOrder);

        assertTrue(order instanceof Order);
        assertTrue(order.livemode == false);
        assertTrue(order.amount == 35000);
        assertTrue(order.status.equals("created"));
        assertTrue(order.customer_id == null);
        assertTrue(order.currency.equals("MXN"));
        assertTrue((Boolean) order.metadata.get("test"));
    }

    //@Test
    public void testSuccesfulOrderUpdate() throws Exception {
        JSONObject newOrderData = new JSONObject();
        newOrderData.put("customer_info", customerInfo);
        Order order = Order.create(validOrder);

       order.update(newOrderData);

       assertTrue(order.customer_info.get("phone").equals("+5213353319758"));
    }

    //@Test
    public void testSuccesfulOrderFind() throws Exception {
        Order order = Order.create(validOrder);

        Order orderFound = Order.find(order.id);

        assertTrue(orderFound instanceof Order);
        assertTrue(orderFound.livemode == false);
        assertTrue(orderFound.amount == 35000);
        assertTrue(orderFound.status.equals("created"));
        assertTrue(orderFound.customer_id == null);
        assertTrue(orderFound.currency.equals("MXN"));
        assertTrue((Boolean) orderFound.metadata.get("test"));
    }

    // @Test
    public void testSuccesfulOrderWhere() throws Exception {
        JSONObject paginateParams = new JSONObject("{'limit': 10}");

        ConektaList orders = Order.where(paginateParams);
        Order order = (Order) orders.get(0);

        assertTrue(orders instanceof ConektaList);
        assertTrue(orders.size() == 10);assertTrue(orders.size() == 10);
        assertTrue(order instanceof Order);
    }

    // @Test
    public void testSuccessfulFiscalEntityCreate() throws JSONException, Error, ErrorList {
        JSONObject fiscalEntityParams = new JSONObject("{" +
        "    'tax_id': 'AMGH851205MN1'," +
        "    'company_name': 'Nike SA de CV'," +
        "    'email': 'contacto@nike.mx'," +
        "    'phone': '+5213353319758'," +
        "    'address': {" +
        "        'street1': '250 Alexis St'," +
        "        'internal_number': 19," +
        "        'external_number': 91," +
        "        'city': 'Red Deer'," +
        "        'state': 'Alberta'," +
        "        'country': 'MX'," +
        "        'zip': '78215'" +
        "    }" +
        "}");

        Order order = Order.create(validOrder);

        order.createFiscalEntity(fiscalEntityParams);

        assertTrue(order.fiscal_entity instanceof FiscalEntity);
    }
}
