package com.conekta;

import java.util.Calendar;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author L.Carlos
 */
public class OrderTest extends ConektaTest{

    JSONObject validOrder;
    JSONObject customerInfo;
    JSONObject validCharge;

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
        
        validCharge = new JSONObject("{"
                + "'source': {"
                + "    'type': 'card',"
                + "    'token_id': 'tok_test_visa_4242'"
                + "}, "
                + "'amount': 35000"
                + "}");

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
    public void testSuccessfulOrderCapture() throws JSONException, Error, ErrorList {
        validOrder.put("capture", false);
        validOrder.put("customer_info", customerInfo);
        JSONArray chargesArray = new JSONArray();
        chargesArray.put(validCharge);
        validOrder.put("charges", chargesArray);

        Order order = Order.create(validOrder);

        order.capture();

        assertTrue(order.capture);
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

    public void testSuccessfulShippingLineCreate() throws JSONException, Error, ErrorList {
        JSONObject shippingLineParams = new JSONObject("{" +
        "    'description': 'Free Shipping'," +
        "    'amount': 0," +
        "    'tracking_number': 'TRACK123'," +
        "    'carrier': 'USPS'," +
        "    'method': 'Train'," +
        "    'metadata': {" +
        "        'some_random': 'Stuff'" +
        "    }" +
        "}");

        Order order = Order.create(validOrder);

        ShippingLine shippingLine = order.createShippingLine(shippingLineParams);

        assertTrue(order.shipping_lines instanceof ConektaList);
        assertTrue(order.shipping_lines.size() == 1);
        assertTrue(shippingLine instanceof ShippingLine);
        assertTrue(((String)shippingLine.metadata.get("some_random")).equals("Stuff"));
    }

    public void testSuccessfulLineItemCreate() throws JSONException, Error, ErrorList {
        JSONObject lineItemParams = new JSONObject("{" +
            "  'name': 'Box of Cohiba S1s'," +
            "  'description': 'Imported From Mex.'," +
            "  'unit_price': 20000," +
            "  'quantity': 1," +
            "  'type': 'physical'," +
            "  'tags': ['food', 'mexican food']" +
            "}");

        Order order = Order.create(validOrder);

        LineItems lineItem = order.createLineItem(lineItemParams);

        assertTrue(order.line_items instanceof ConektaList);
        assertTrue(lineItem instanceof LineItems);
    }

    // @Test
    public void testSuccessfulOrderReturn() throws Exception {
        validOrder.put("customer_info", customerInfo);
        JSONArray chargesArray = new JSONArray();
        chargesArray.put(validCharge);
        validOrder.put("charges", chargesArray);

        Order order = Order.create(validOrder);
        
        JSONObject validReturn = new JSONObject(
            "{" +
            "  'amount': 35000," +
            "  'reason': 'Reason return'," +
            "  'currency': 'MXN'," +
            "  'order_id': '" + order.id + "'" +
            "}"
        );

        OrderReturn orderReturn = order.createReturn(validReturn);

        assertTrue(order.status.equals("returned"));
        assertTrue(orderReturn instanceof OrderReturn);
        assertTrue(order.returns instanceof ConektaList);
        assertTrue(order.returns.size() == 1);
    }

    // @Test
    public void testSuccessfulOrderTransportation() throws JSONException, ErrorList, Error{
        setApiKey("key_Jqn4jwiAt1fhxgMJbGx7vQ");
        JSONObject validTransportationOrder = new JSONObject(
            "{ 'currency': 'mxn'," +
            "  'line_items': [{" +
            "    'driver_id': 'driver_id'," +
            "    'ticket_type': 'Basico'," +
            "    'pickup_latlon': '23.4323456,-123.1234567'," +
            "    'dropoff_latlon': '23.4323456,-123.1234567'," +
            "    'stops': 0," +
            "    'name': 'Ride'," +
            "    'description': 'Viaje de la roma a narvarte'," +
            "    'unit_price': 35000," +
            "    'quantity': 1," +
            "    'tags': ['ride', 'standar']," +
            "    'type': 'physical'" +
            "  }]" +
            "}"
        );

        Order order = Order.create(validTransportationOrder);
        ConektaList lineItems = (ConektaList) order.line_items;
        LineItems lineItem = (LineItems) lineItems.get(0);

        assertTrue(lineItem.get("driver_id").equals("driver_id"));
        assertTrue(lineItem.get("ticket_type").equals("Basico"));
        assertTrue(lineItem.get("pickup_latlon").equals("23.4323456,-123.1234567"));
        setApiKey();
    }

    // @Test
    public void testSuccessfulOrderTravel() throws JSONException, ErrorList, Error{
        setApiKey("key_Psbw54NyBwjfq4RXv3zfrA");
        JSONObject validTraveOrder = new JSONObject(
            "{ 'currency': 'mxn'," +
            "  'line_items': [{" +
            "    'depart_at': " + tomorrow() + "," +
            "    'arrive_at': " + tomorrow() + "," +
            "    'ticket_class': 'Turista'," +
            "    'seat_number': '23'," +
            "    'seat_type': 'Estandar'," +
            "    'origin': 'Ciudad de México'," +
            "    'destination': 'Bahamas'," +
            "    'stops': 0," +
            "    'name': 'Viaje a las bahamas'," +
            "    'description': 'Viaje de lujo a las bahamas'," +
            "    'unit_price': 35000," +
            "    'quantity': 1," +
            "    'tags': ['travel', 'bahamas']," +
            "    'type': 'physical'" +
            "  }]" +
            "}"
        );

        Order order = Order.create(validTraveOrder);
        ConektaList lineItems = (ConektaList) order.line_items;
        LineItems lineItem = (LineItems) lineItems.get(0);

        assertTrue(lineItem.get("seat_type").equals("Estandar"));
        assertTrue(lineItem.get("ticket_class").equals("Turista"));
        assertTrue(lineItem.get("seat_number").equals("23"));
        setApiKey();
    }

    // @Test
    public void testSuccessfulOrderEvent() throws JSONException, ErrorList, Error{
        setApiKey("key_fYFLbCqC8r2THxN5ThuRug");
        JSONObject validTEventOrder = new JSONObject(
            "{ 'currency': 'mxn'," +
            "  'line_items': [{" +
            "    'start_at': " + tomorrow() + "," +
            "    'end_at': " + tomorrow() + "," +
            "    'ticket_class': 'vip'," +
            "    'seat_number': '62'," +
            "    'name': 'Daft Punk'," +
            "    'description': 'Alive 2017'," +
            "    'unit_price': 35000," +
            "    'quantity': 1," +
            "    'tags': ['concert', 'alive']," +
            "    'type': 'physical'" +
            "  }]" +
            "}"
        );

        Order order = Order.create(validTEventOrder);
        ConektaList lineItems = (ConektaList) order.line_items;
        LineItems lineItem = (LineItems) lineItems.get(0);

        assertTrue(lineItem.get("ticket_class").equals("vip"));
        assertTrue(lineItem.get("seat_number").equals("62"));
        setApiKey();
    }

    // @Test
    public void testSuccessfulOrderFinance() throws JSONException, ErrorList, Error{
        setApiKey("key_1yLLRs4B26YRGzUpzSKYvQ");
        JSONObject validFinanceOrder = new JSONObject(
            "{ 'currency': 'mxn'," +
            "  'line_items': [{" +
            "    'project_id': 'project_id'," +
            "    'project_capacity': 5," +
            "    'project_start_at':  " + tomorrow() + "," +
            "    'project_end_at':  " + tomorrow() + "," +
            "    'project_total_amount': 35000," +
            "    'name': 'Projecto'," +
            "    'description': 'La descripcion del proyecto'," +
            "    'unit_price': 35000," +
            "    'quantity': 1," +
            "    'tags': ['proyecto', 'standar']," +
            "    'type': 'physical'" +
            "  }]" +
            "}"
        );

        Order order = Order.create(validFinanceOrder);
        ConektaList lineItems = (ConektaList) order.line_items;
        LineItems lineItem = (LineItems) lineItems.get(0);

        assertTrue(lineItem.get("project_id").equals("project_id"));
        assertTrue(lineItem.get("project_capacity").equals("5"));
        assertTrue(lineItem.get("project_total_amount").equals("35000"));
        setApiKey();
    }
}
