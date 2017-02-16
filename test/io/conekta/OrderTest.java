package io.conekta;

import java.util.Calendar;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author L.Carlos
 */
public class OrderTest extends ConektaBase{

    JSONObject validOrder;
    JSONObject customerInfo;
    JSONObject validCharge;
    JSONObject customerTransportationInfo;
    JSONObject customerInfoTransportationWithId;
    Customer customer;

    public OrderTest() throws JSONException, Error, ErrorList {
        super();
        setApiVersion("2.0.0");
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
        
        customerTransportationInfo = new JSONObject(
            "{ 'name': 'John Constantine'," +
            "  'phone': '+5213353319758'," +
            "  'email': 'hola@hola.com'," +
            "  'antifraud_info': {" +
            "      'account_created_at': 1484040996," +
            "      'first_paid_at': 1484040996," +
            "      'paid_transactions': 9," +
            "  }" +
            "}"
        );
        
        validCharge = new JSONObject("{"
                + "'payment_method': {"
                + "    'type': 'card',"
                + "    'token_id': 'tok_test_visa_4242'"
                + "}, "
                + "'amount': 35000"
                + "}");

        customer = Customer.create(new JSONObject("{'name': 'test name', 'email': 'test@test.com', 'cards':['tok_test_visa_4242']}"));
    
        customerInfoTransportationWithId = new JSONObject(
            "{ 'customer_id': '" + customer.id + "'," +
            "  'antifraud_info': {" +
            "      'paid_transactions': 9," +
            "  }" +
            "}"
        );
    }

    //@Test
    public void testSuccesfulOrderCreate() throws Exception {
        Order order = Order.create(validOrder);

        assertTrue(order instanceof Order);
        assertTrue(order.livemode == false);
        assertTrue(order.amount == 35000);
        assertTrue(order.customer_id == null);
        assertTrue(order.currency.equals("MXN"));
        assertTrue((Boolean) order.metadata.get("test"));
    }

    //@Test
    public void testSuccesfulOrderUpdate() throws Exception {
        JSONObject newOrderData = new JSONObject();
        newOrderData.put("currency", "USD");
        Order order = Order.create(validOrder);

        order.update(newOrderData);

        assertTrue(order.currency.equals("USD"));
    }

    //@Test
    public void testSuccesfulOrderFind() throws Exception {
        Order order = Order.create(validOrder);

        Order orderFound = Order.find(order.id);

        assertTrue(orderFound instanceof Order);
        assertTrue(orderFound.livemode == false);
        assertTrue(orderFound.amount == 35000);
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
        "    'name': 'Nike SA de CV'," +
        "    'address': {" +
        "        'street1': '250 Alexis St'," +
        "        'internal_number': '19'," +
        "        'external_number': '91'," +
        "        'city': 'Red Deer'," +
        "        'state': 'Alberta'," +
        "        'country': 'MX'," +
        "        'postal_code': '78215'" +
        "    }" +
        "}");

        Order order = Order.create(validOrder);

        order.createFiscalEntity(fiscalEntityParams);

        assertTrue(order.fiscal_entity instanceof FiscalEntity);
    }

    // @Test
    public void testSuccessfulDiscountLineCreate() throws JSONException, Error, ErrorList, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        JSONObject discountLineParams = new JSONObject("{" +
            "    'code': 'Cupon de descuento'," +
            "    'amount': 5," +
            "    'type': 'loyalty'" +
            "}");

        Order order = Order.create(validOrder);

        DiscountLine discountLine = order.createDiscountLine(discountLineParams);

        assertTrue(order.discount_lines instanceof ConektaList);
        assertTrue(discountLine instanceof DiscountLine);
        assertTrue(discountLine.code.equals("Cupon de descuento"));
        assertTrue(discountLine.amount == 5);
        assertTrue(discountLine.type.equals("loyalty"));
    }

    public void testSuccessfulShippingContactCreate() throws JSONException, Error, ErrorList {
        JSONObject shippingContactParams = new JSONObject("{"+
        "    'id': '1jap4jmcjnwh34'," +
        "    'email': 'thomas.logan@xmen.org'," +
        "    'phone': '+5213353319758'," +
        "    'receiver': 'Marvin Fuller'," +
        "    'between_streets': 'Ackerman Crescent'," +
        "    'address': {" +
        "        'street1': '250 Alexis St'," +
        "        'internal_number': '19'," +
        "        'external_number': '91'," +
        "        'city': 'Red Deer'," +
        "        'state': 'Alberta'," +
        "        'country': 'MX'," +
        "        'postal_code': '78215'" +
        "    }" +
        "}");

        Order order = Order.create(validOrder);

        order.createShippingContact(shippingContactParams);

        assertTrue(order.shipping_contact instanceof ShippingContact);
    }

    // @Test
    public void testSuccessfulTaxLineCreate() throws JSONException, Error, ErrorList, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
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
        validOrder.put("preauthorize", true);
        validOrder.put("customer_info", customerInfo);
        JSONArray chargesArray = new JSONArray();
        chargesArray.put(validCharge);
        validOrder.put("charges", chargesArray);

        Order order = Order.create(validOrder);

        order.capture();
        
        Charge charge = (Charge) order.charges.get(0);

        assertTrue(charge.status.equals("paid"));
    }

    // @Test
    public void testSuccessfulChargeCreate() throws JSONException, Error, ErrorList, NoSuchFieldException, IllegalAccessException {
        JSONObject chargeParams = new JSONObject("{"
                + "'payment_method': {"
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
                + "'payment_method': {"
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
                + "'payment_method': {"
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
                + "'payment_method': {"
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
        JSONObject chargeParams = new JSONObject("{"
                + "'payment_method': {"
                + "    'type': 'card',"
                + "    'token_id': 'tok_test_card_declined'"
                + "}, "
                + "'amount': 35000"
                + "}");

        Order order = Order.create(validOrder.put("customer_info", customerInfo));

        try {
            Charge charge = order.createCharge(chargeParams);
        } catch(ErrorList e) {
            assertTrue(e.details.get(0).message.equals("La tarjeta ingresada ha sido declinada. Por favor intenta con otro método de pago."));
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

    public void testSuccessfulShippingLineCreate() throws JSONException, Error, ErrorList, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
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

    public void testSuccessfulLineItemCreate() throws JSONException, Error, ErrorList, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
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
            "  'reason': 'requested_by_client'," +
            "  'currency': 'MXN'," +
            "  'order_id': '" + order.id + "'" +
            "}"
        );

        OrderReturn orderReturn = order.createReturn(validReturn);

        assertTrue(order.payment_status.equals("returned"));
        assertTrue(orderReturn instanceof OrderReturn);
        assertTrue(order.returns instanceof ConektaList);
        assertTrue(order.returns.size() == 1);
    }
    
    //@Test
    public void testSuccesfulOrderTransportationCreate() throws Exception {
        validOrder.put("customer_info", customerTransportationInfo);
        Order order = Order.create(validOrder);

        assertTrue(order instanceof Order);
        assertTrue(order.livemode == false);
        assertTrue(order.amount == 35000);
        assertTrue(order.customer_id == null);
        assertTrue(order.currency.equals("MXN"));
        assertTrue((Boolean) order.metadata.get("test"));
        assertTrue((Integer) order.customer_info.antifraud_info.get("account_created_at") == 1484040996);
        assertTrue((Integer) order.customer_info.antifraud_info.get("first_paid_at") == 1484040996);
        assertTrue((Integer) order.customer_info.antifraud_info.get("paid_transactions") == 9);
    }
    
        //@Test
    public void testSuccesfulOrderTransportationWithCustomerIDCreate() throws Exception {
        validOrder.put("customer_info", customerInfoTransportationWithId);
        Order order = Order.create(validOrder);

        assertTrue(order instanceof Order);
        assertTrue(order.livemode == false);
        assertTrue(order.amount == 35000);
        assertTrue(order.customer_id == null);
        assertTrue(order.currency.equals("MXN"));
        assertTrue((Boolean) order.metadata.get("test"));
        assertTrue((Integer) order.customer_info.antifraud_info.get("paid_transactions") == 9);
    }
    
    
}
