package com.conekta;

import java.util.Calendar;
import java.util.Date;
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

    // @Test
    public void testSuccessfulDiscountLineCreate() throws JSONException, Error, ErrorList {
        JSONObject discountLineParams = new JSONObject("{" +
            "    'description': 'Cupon de descuento'," +
            "    'amount': 5," +
            "    'kind': 'loyalty'" +
            "}");

        Order order = Order.create(validOrder);

        DiscountLine discountLine = order.createDiscountLine(discountLineParams);

        assertTrue(order.discount_lines instanceof ConektaList);
        assertTrue(discountLine instanceof DiscountLine);
    }

    public void testSuccessfulShippingContactCreate() throws JSONException, Error, ErrorList {
        JSONObject shippingContactParams = new JSONObject("{"+
        "    'id': '1jap4jmcjnwh34'," +
        "    'email': 'thomas.logan@xmen.org'," +
        "    'phone': '+5213353319758'," +
        "    'receiver': 'Marvin Fuller'," +
        "    'between_streets': {" +
        "        'street1': 'Ackerman Crescent'," +
        "    }," +
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

        order.createShippingContact(shippingContactParams);

        assertTrue(order.shipping_contact instanceof ShippingContact);
    }

    // @Test
    public void testSuccessfulTaxLineCreate() throws JSONException, Error, ErrorList {
        JSONObject taxLineIVAParams = new JSONObject("{" +
        "  'description': 'IVA'," +
        "  'amount': 60" +
        "}");

        JSONObject taxLineISRParams = new JSONObject("{" +
        "  'description': 'ISR'," +
        "  'amount': 6," +
        "  'metadata': {" +
        "      'some_random': 'Stuff'" +
        "   }" +
        "}");


        Order order = Order.create(validOrder);

        order.createTaxLine(taxLineIVAParams);
        order.createTaxLine(taxLineISRParams);

        TaxLine taxLine = (TaxLine) order.tax_lines.get(1);

        assertTrue(order.tax_lines instanceof ConektaList);
        assertTrue(order.tax_lines.size() == 2);
        assertTrue(taxLine instanceof TaxLine);
        assertTrue(((String)taxLine.metadata.get("some_random")).equals("Stuff"));
    }

    // @Test
    public void testSuccessfulChargeCreate() throws JSONException, Error, ErrorList {
        JSONObject chargeParams = new JSONObject("{"
                + "'source': {"
                + "    'type': 'oxxo_cash'"
                + "}, "
                + "'amount': 35000"
                + "}");

        Order order = Order.create(validOrder.put("customer_info", customerInfo));

        Charge charge = order.createCharge(chargeParams);

        assertTrue(order.charges instanceof ConektaList);
        assertTrue(charge instanceof Charge);
    }
    
    //@Test
    public void testSuccesfulBankPMCreate() throws Exception {
        JSONObject chargeParams = new JSONObject("{"
                + "'source': {"
                + "    'type': 'banorte',"
                + "    'expires_at': " + tomorrow()
                + "}, "
                + "'amount': 35000"
                + "}");

        Order order = Order.create(validOrder.put("customer_info", customerInfo));

        Charge charge = order.createCharge(chargeParams);

        assertTrue(order.charges instanceof ConektaList);
        assertTrue(charge instanceof Charge);
    }
    
    //@Test
    public void testSuccesfulSPEIPMCreate() throws Exception {
        JSONObject chargeParams = new JSONObject("{"
                + "'source': {"
                + "    'type': 'spei',"
                + "    'expires_at': " + tomorrow()
                + "}, "
                + "'amount': 35000"
                + "}");

        Order order = Order.create(validOrder.put("customer_info", customerInfo));

        Charge charge = order.createCharge(chargeParams);

        assertTrue(order.charges instanceof ConektaList);
        assertTrue(charge instanceof Charge);
    }

    //@Test
    public void testSuccesfulCardCreate() throws Exception {
        JSONObject chargeParams = new JSONObject("{"
                + "'source': {"
                + "    'type': 'card',"
                + "    'token_id': 'tok_test_visa_4242'"
                + "}, "
                + "'amount': 35000"
                + "}");

        Order order = Order.create(validOrder.put("customer_info", customerInfo));

        Charge charge = order.createCharge(chargeParams);

        assertTrue(order.charges instanceof ConektaList);
        assertTrue(charge instanceof Charge);
        
    }
    
    //@Test
    public void testUnsuccesfulCardCreate() throws Exception {
        setApiVersion("1.1.0");
        JSONObject chargeParams = new JSONObject("{"
                + "'source': {"
                + "    'type': 'card',"
                + "    'token_id': 'tok_test_card_declined'"
                + "}, "
                + "'amount': 35000"
                + "}");

        Order order = Order.create(validOrder.put("customer_info", customerInfo));
        
        try {
            Charge charge = order.createCharge(chargeParams);
        } catch(ErrorList e) {
            assertTrue(e.details.get(0).message_to_purchaser.equals("La tarjeta ingresada ha sido declinada. Por favor intenta con otro método de pago."));
        }

    }

    private long tomorrow(){
        Date dt = new Date();
        Calendar c = Calendar.getInstance(); 
        c.setTime(dt); 
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        
        return dt.getTime()/1000L;
    }
}
