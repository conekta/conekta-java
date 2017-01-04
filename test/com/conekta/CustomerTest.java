package com.conekta;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author mauricio
 */
public class CustomerTest extends ConektaTest {

    JSONObject valid_payment_method;
    JSONObject invalid_payment_method;
    JSONObject valid_visa_card;

    public CustomerTest() throws JSONException {
        super();
        valid_visa_card = new JSONObject("{'name': 'test name', 'email': 'test@test.com', 'cards':['tok_test_visa_4242']}");
    }

    //@Test
    public void testSuccesfulCustomerFind() throws Exception {
        Customer customer = Customer.create(valid_visa_card);
        customer = Customer.find(customer.id);
        assertTrue(customer instanceof Customer);
    }

    //@Test
    public void testSuccesfulCustomerCreate() throws Exception {
        Customer customer = Customer.create(valid_visa_card);
        assertTrue(customer instanceof Customer);
        assertTrue(customer.cards.get(0) instanceof Card);
        assertTrue(((Card)customer.cards.get(0)).last4.equals("4242"));
        assertTrue(((Card) customer.cards.get(0)).customer ==  customer);
    }

    //@Test
    public void testSuccesfulCustomerWhere() throws Exception {
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
        Customer customer = Customer.create(valid_visa_card);
        JSONObject params = new JSONObject("{'name':'Logan', 'email':'logan@x-men.org'}");
        customer.update(params);
        assertTrue(customer.name.equals("Logan"));
    }

    //@Test
    public void testAddCardToCustomer() throws Exception {
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
        Customer customer = Customer.create(valid_visa_card);
        JSONObject params = new JSONObject("{'token':'tok_test_mastercard_4444'}");
        ((Card) customer.cards.get(0)).update(params);
        assertTrue(((Card) customer.cards.get(0)).last4.equals("4444"));
        assertTrue(((Card) customer.cards.get(0)).customer ==  customer);
    }
    
    //@Test
    public Customer testSuccesfulSubscriptionCreate() throws Exception {
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
    public void testSuccesfulSubscriptionUpdate() throws Exception {
        Customer customer = testSuccesfulSubscriptionCreate();
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
        Customer customer = testSuccesfulSubscriptionCreate();
        customer.subscription.pause();
        assertTrue(customer.subscription.status.equals("paused"));
    }

    //@Test
    public void testSuccesfulSubscriptionResume() throws Exception {
        Customer customer = testSuccesfulSubscriptionCreate();
        customer.subscription.pause();
        assertTrue(customer.subscription.status.equals("paused"));
        customer.subscription.resume();
        assertTrue(customer.subscription.status.equals("in_trial"));
    }

    //@Test
    public void testSuccesfulSubscriptionCancel() throws Exception {
        Customer customer = testSuccesfulSubscriptionCreate();
        customer.subscription.cancel();
        assertTrue(customer.subscription.status.equals("canceled"));
    }
 
    // @Test
    public void testSuccessfulFiscalEntityCreate() throws JSONException, Error, ErrorList {
        setApiVersion("1.1.0");

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
        
        Customer customer = Customer.create(valid_visa_card);
        
        FiscalEntity fiscalEntity = customer.createFiscalEntity(fiscalEntityParams);
                
        assertTrue(fiscalEntity instanceof FiscalEntity);
        assertTrue(customer.fiscal_entities.size() == 1);
    }
    
    // @Test
    public void testSuccessfulShippingContactCreate() throws JSONException, Error, ErrorList {
        setApiVersion("1.1.0");
         
        JSONObject shippingContactParams = new JSONObject("{"+
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

        Customer customer = Customer.create(valid_visa_card);

        customer.createShippingContact(shippingContactParams);

        assertTrue(((ShippingContact) customer.shipping_contacts.get(0)) instanceof ShippingContact);
    }
}
