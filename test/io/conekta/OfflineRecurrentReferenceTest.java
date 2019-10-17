package io.conekta;

import org.json.JSONObject;

public class OfflineRecurrentReferenceTest extends ConektaBase {
    JSONObject validCustomer;
    JSONObject validCustomerWithOfflineRecurrentReference;

    public OfflineRecurrentReferenceTest() {
        validCustomer  = new JSONObject("{" +
                "  'email': 'hola@hola.com'," +
                "  'name': 'John Constantine'," +
                "  'payment_sources': [{ " +
                "     'type': 'oxxo_recurrent', " +
                "     'expires_at': '1760472787' " +
                "  }]" +
                "}");

        validCustomerWithOfflineRecurrentReference  = new JSONObject("{" +
                "  'email': 'hola@hola.com'," +
                "  'name': 'John Constantine'," +
                "  'payment_sources': [{ " +
                "     'type': 'oxxo_recurrent', " +
                "     'expires_at': '1760472787' " +
                "  }]" +
                "}");
    }

    //@Test
    public void testAddOfflineRecurrentReferencePaymentSourceToCustomerV2() throws Exception {
        setApiVersion("2.0.0");
        Customer customer = Customer.create(validCustomer);
        JSONObject params = new JSONObject("{ " +
                "      'type': 'oxxo_recurrent'," +
                "      'expires_at': '1760472787'" +
                "    }");
        customer.createOfflineRecurrentReference(params);
        assertTrue(((OfflineRecurrentReference) customer.payment_sources.get(0)).reference.length() == 14);
        assertTrue(((OfflineRecurrentReference) customer.payment_sources.get(0)).customer == customer);
    }

    //@Test
    public void testSuccesfulDeletePaymentSource() throws Error, ErrorList {
        Customer customer = Customer.create(validCustomerWithOfflineRecurrentReference);

        OfflineRecurrentReference source = (OfflineRecurrentReference) customer.payment_sources.get(0);

        source.delete();

        assertTrue(source.deleted);
    }
}
