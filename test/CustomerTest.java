/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.json.JSONException;
import org.json.JSONObject;
import static org.junit.Assert.*;

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
        valid_visa_card = new JSONObject("{'name': 'test', 'cards':['tok_test_visa_4242']}");
    }

    // @Test
    public void testSuccesfulCustomerFind() throws Exception {
        Customer customer = testSuccesfulCustomerCreate();
        customer = Customer.find(customer.id);
        assertTrue(customer instanceof Customer);
    }

    //@Test
    public Customer testSuccesfulCustomerCreate() throws Exception {
        Customer customer = Customer.create(valid_visa_card);
        assertTrue(customer instanceof Customer);
        assertTrue(customer.cards.get(0) instanceof Card);
        assertTrue(((Card)customer.cards.get(0)).last4.equals("4242"));
        return customer;
    }

    //@Test
    public void testSuccesfulCustomerWhere() throws Exception {
        ConektaObject customers = Customer.where();
        assertTrue(customers instanceof ConektaObject);
        assertTrue(customers.get(0) instanceof Customer);
    }

    //@Test
    public void testSuccesfulDeleteCustomer() throws Exception {
        Customer customer = testSuccesfulCustomerCreate();
        customer.delete();
        assertTrue(customer.deleted);
    }

    //@Test
    public void testSuccesfulCustomerUpdate() throws Exception {
        Customer customer = testSuccesfulCustomerCreate();
        JSONObject params = new JSONObject("{'name':'Logan', 'email':'logan@x-men.org'}");
        customer.update(params);
        assertTrue(customer.name.equals("Logan"));
    }

}
