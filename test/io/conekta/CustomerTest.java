package io.conekta;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author mauricio
 */
public class CustomerTest extends ConektaBase {

    JSONObject valid_payment_method;
    JSONObject invalid_payment_method;
    JSONObject valid_visa_card;
    JSONObject validCustomerAndCardInfov2;
    JSONObject travelCustomerInfo;

    public CustomerTest() throws JSONException {
        super();
        valid_visa_card = new JSONObject("{"
                + "'name': 'test name',"
                + "'email': 'test@test.com',"
                + "'cards':['tok_test_visa_4242']"
                + "}");
        
        validCustomerAndCardInfov2 = new JSONObject("{"
                + "'name': 'test name',"
                + "'email': 'test@test.com',"
                + "'payment_sources':[{"
                + "    'token_id': 'tok_test_visa_4242',"
                + "    'type': 'card' }]"
                + "}");

        travelCustomerInfo = new JSONObject("{" +
                "  'account_created_at': 1484040996," +
                "  'first_paid_at': 1485151007," +
                "  'requires_receipt': true" +
                "}");
    }

    //@Test
    public void testSuccesfulCustomerFind() throws Exception {
        Customer customer = Customer.create(valid_visa_card);
        customer = Customer.find(customer.id);
        assertTrue(customer instanceof Customer);
    }

    //@Test
    public void testSuccesfulCustomerCreate() throws Exception {
        setApiVersion("1.0.0");
        Customer customer = Customer.create(valid_visa_card);
        assertTrue(customer instanceof Customer);
        assertTrue(customer.cards.get(0) instanceof Card);
        assertTrue(((Card)customer.cards.get(0)).last4.equals("4242"));
        assertTrue(((Card) customer.cards.get(0)).customer ==  customer);
    }

    //@Test
    public void testSuccesfulCustomerWhere() throws Exception {
        setApiVersion("1.0.0");
        ConektaObject customers = Customer.where();
        assertTrue(customers instanceof ConektaObject);
        assertTrue(customers.get(0) instanceof Customer);
    }

    //@Test
    public void testSuccesfulDeleteCustomer() throws Exception {
        Customer customer = Customer.create(valid_visa_card);
        customer.delete();
        assertTrue(customer.deleted);
    }

    //@Test
    public void testSuccesfulCustomerUpdate() throws Exception {
        setApiVersion("1.0.0");
        Customer customer = Customer.create(valid_visa_card);
        JSONObject params = new JSONObject("{'name':'Logan', 'email':'logan@x-men.org'}");
        customer.update(params);
        assertTrue(customer.name.equals("Logan"));
    }

    //@Test
    public void testAddCardToCustomer() throws Exception {
        setApiVersion("1.0.0");
        Customer customer = Customer.create(valid_visa_card);
        JSONObject params = new JSONObject("{'token':'tok_test_visa_1881'}");
        customer.createCard(params);
        assertTrue(((Card)customer.cards.get(0)).last4.equals("4242"));
        assertTrue(((Card)customer.cards.get(1)).last4.equals("1881"));
        assertTrue(((Card) customer.cards.get(1)).customer ==  customer);
    }

    //@Test
    public void testDeleteCard() throws Exception {
        setApiVersion("1.0.0");
        Customer cus = Customer.create(valid_visa_card);
        ((Card) cus.cards.get(0)).delete();
        assertTrue(((Card) cus.cards.get(0)).deleted);
    }

    //@Test
    public void testUpdateCard() throws Exception {
        setApiVersion("1.0.0");
        Customer customer = Customer.create(valid_visa_card);
        JSONObject params = new JSONObject("{'token':'tok_test_mastercard_4444'}");
        ((Card) customer.cards.get(0)).update(params);
        assertTrue(((Card) customer.cards.get(0)).last4.equals("4444"));
        assertTrue(((Card) customer.cards.get(0)).customer ==  customer);
    }

    //@Test
    public void testAddCardPaymentSourceToCustomerV2() throws Exception {
        setApiVersion("2.0.0");
        Customer customer = Customer.create(validCustomerAndCardInfov2);
        JSONObject params = new JSONObject("{'type': 'card','token_id':'tok_test_visa_1881'}");
        customer.createCard(params);
        assertTrue(((Card) customer.payment_sources.get(0)).last4.equals("4242"));
        assertTrue(((Card) customer.payment_sources.get(1)).last4.equals("1881"));
        assertTrue(((Card) customer.payment_sources.get(1)).customer == customer);
    }

    //@Test
    public void testDeletePaymentSourceCardV2() throws Exception {
        setApiVersion("2.0.0");
        Customer cus = Customer.create(validCustomerAndCardInfov2);
        ((Card) cus.payment_sources.get(0)).delete();
        assertTrue(((Card) cus.payment_sources.get(0)).deleted);
    }

    //@Test
    public Customer testSuccesfulSubscriptionCreateV2() throws Exception {
        Customer customer = Customer.create(valid_visa_card);
        JSONObject params = new JSONObject("{'plan':'gold-plan'}");
        customer.createSubscription(params);
        assertTrue(customer.subscription instanceof Subscription);
        assertTrue(customer.subscription.status.equals("in_trial"));
        assertTrue(customer.subscription.plan_id.equals("gold-plan"));
        assertTrue(customer.subscription.card_id.equals(customer.default_card_id));
        return customer;
    }
    
    //@Test
    public Customer testSuccesfulSubscriptionCreateV1() throws Exception {
        Customer customer = Customer.create(valid_visa_card);
        JSONObject params = new JSONObject("{'plan':'gold-plan'}");
        customer.createSubscription(params);
        assertTrue(customer.subscription instanceof Subscription);
        assertTrue(customer.subscription.status.equals("in_trial"));
        assertTrue(customer.subscription.plan_id.equals("gold-plan"));
        assertTrue(customer.subscription.card_id.equals(customer.default_card_id));
        return customer;
    }

    //@Test
    public void testSuccesfulSubscriptionUpdateV1() throws Exception {
        setApiVersion("1.0.0");
        Customer customer = testSuccesfulSubscriptionCreateV1();
        Plan plan = null;
        try {
            plan = Plan.find("gold-plan2");
        } catch(Error e) {
            JSONObject params = new JSONObject("{'id':'gold-plan2','name':'Gold plan', 'amount':1000, 'currency':'MXN','interval':'month','frequency':1,'trial_period_days':15,'expiry_count':12}");
            plan = Plan.create(params);
        }
        customer.subscription.update(new JSONObject("{'plan':'"+plan.id+"'}"));
        assertTrue(customer.subscription.plan_id.equals(plan.id));
    }
   
    //@Test
    public void testSuccesfulSubscriptionUpdateV2() throws Exception {
        Customer customer = testSuccesfulSubscriptionCreateV2();
        Plan plan = null;
        try {
            plan = Plan.find("gold-plan2");
        } catch(Error e) {
            JSONObject params = new JSONObject("{'id':'gold-plan2','name':'Gold plan', 'amount':1000, 'currency':'MXN','interval':'month','frequency':1,'trial_period_days':15,'expiry_count':12}");
            plan = Plan.create(params);
        }
        customer.subscription.update(new JSONObject("{'plan':'"+plan.id+"'}"));
        assertTrue(customer.subscription.plan_id.equals(plan.id));
    }

    //@Test
    public void testUnSuccesfulSubscriptionCreate() throws Exception {
        setApiVersion("1.0.0");
        Customer customer = Customer.create(valid_visa_card);
        JSONObject params = new JSONObject("{'plan':'unexistent-plan'}");
        try {
            customer.createSubscription(params);
            assertTrue(false);
        } catch (Error e) {
            assertTrue(e instanceof ResourceNotFoundError);
        }
    }

    //@Test
    public void testSuccesfulSubscriptionPause() throws Exception {
        setApiVersion("1.0.0");
        Customer customer = testSuccesfulSubscriptionCreateV1();
        customer.subscription.pause();
        assertTrue(customer.subscription.status.equals("paused"));
    }

    //@Test
    public void testSuccesfulSubscriptionResume() throws Exception {
        setApiVersion("1.0.0");
        Customer customer = testSuccesfulSubscriptionCreateV1();
        customer.subscription.pause();
        assertTrue(customer.subscription.status.equals("paused"));
        customer.subscription.resume();
        assertTrue(customer.subscription.status.equals("in_trial"));
    }

    //@Test
    public void testSuccesfulSubscriptionCancel() throws Exception {
        setApiVersion("1.0.0");
        Customer customer = testSuccesfulSubscriptionCreateV1();
        customer.subscription.cancel();
        assertTrue(customer.subscription.status.equals("canceled"));
    }

    // @Test
    public void testSuccessfulShippingContactCreate() throws JSONException, Error, ErrorList, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        setApiVersion("2.0.0");
        JSONObject shippingContactParams = new JSONObject("{"+
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

        Customer customer = Customer.create(valid_visa_card);

        customer.createShippingContact(shippingContactParams);

        assertTrue(((ShippingContact) customer.shipping_contacts.get(0)) instanceof ShippingContact);
    }

    public void testSuccessfulSourceCreate() throws JSONException, Error, ErrorList, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        setApiVersion("2.0.0");
        JSONObject sourceParams = new JSONObject("{" +
        "    'token_id': 'tok_test_visa_4242'," +
        "    'type': 'card'" +
        "  }");

        Customer customer = Customer.create(valid_visa_card);

        PaymentSource source = customer.createPaymentSource(sourceParams);

        assertTrue(source instanceof PaymentSource);
        assertTrue(customer.payment_sources.size() == 1);
    }
    
    //@Test
    public void testSuccesfulCustomerCreateTravel() throws Exception {
        setApiVersion("2.0.0");
        validCustomerAndCardInfov2.put("antifraud_info", travelCustomerInfo);
        Customer customer = Customer.create(validCustomerAndCardInfov2);
        assertTrue(customer instanceof Customer);
        assertTrue(customer.antifraud_info.get("account_created_at").equals(1484040996));
        assertTrue(customer.antifraud_info.get("first_paid_at").equals(1485151007));
    }
}
