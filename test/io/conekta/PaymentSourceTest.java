package io.conekta;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author L.Carlos
 */
public class PaymentSourceTest extends ConektaBase {
    JSONObject validCustomer;

    public PaymentSourceTest() throws JSONException{
        validCustomer  = new JSONObject("{" +
                "  'email': 'hola@hola.com'," +
                "  'name': 'John Constantine'," +
                "  'payment_sources': [{" +
                "    'token_id': 'tok_test_visa_4242'," +
                "    'type': 'card'" +
                "  }]" +
                "}");
    }

    public void testSuccesfulCardPaymentSource() throws Error, ErrorList{
        Customer customer = Customer.create(validCustomer);
        Card card = (Card) customer.payment_sources.get(0);
        assertTrue(card instanceof Card);
        assertTrue(card.last4.equals("4242"));
    }

    // @Test
    public void testSuccesfulDeleteSources() throws Error, ErrorList{
        Customer customer = Customer.create(validCustomer);

        PaymentSource source = (PaymentSource) customer.payment_sources.get(0);

        source.delete();

        assertTrue(source.deleted);
    }

    // @Test
    public void testSuccesfulUpdateSources() throws Error, ErrorList, JSONException{
        Customer customer = Customer.create(validCustomer);

        PaymentSource source = (PaymentSource) customer.payment_sources.get(0);

        source.update(new JSONObject("{'exp_month': '11'}"));

        Card card = (Card) source;

        assertTrue(card.exp_month.equals("11"));
    }
}
