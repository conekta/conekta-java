package io.conekta;

import static junit.framework.TestCase.assertTrue;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author L.Carlos
 */
public class ShippingContactTest extends ConektaBase{

    JSONObject validVisaCard;

    public ShippingContactTest() throws JSONException {
        super();
        validVisaCard = new JSONObject("{"
                + "'name': 'Nombre de prueba', "
                + "'email': 'test@test.com', "
                + "'cards':['tok_test_visa_4242'],"
                + "'shipping_contacts': [{" +
                "    'receiver': 'John Williams'," +
                "    'phone': '+5213353319758'," +
                "    'email': 'thomas.logan@xmen.org'," +
                "    'address': {" +
                "        'street1': '250 Alexis St'," +
                "        'city': 'Red Deer'," +
                "        'state': 'Alberta'," +
                "        'country': 'CA'," +
                "        'zip': 'T4N 0B8'" +
                "    }" +
                "}," +
                "{" +
                "    'receiver': 'John Williams'," +
                "    'phone': '+5213353319758'," +
                "    'email': 'rogue@xmen.org'," +
                "    'address': {" +
                "        'street1': '250 Alexis St'," +
                "        'city': 'Red Deer'," +
                "        'state': 'Alberta'," +
                "        'country': 'CA'," +
                "        'zip': 'T4N 0B8'" +
                "    }" +
                "}]"
                + "}");
    }

    // @Test
    public void testShippingContactDelete() throws ErrorList, Error{
        Customer customer = Customer.create(validVisaCard);

        ShippingContact shippingContact = (ShippingContact) customer.shipping_contacts.get(0);

        shippingContact.delete();

        assertTrue(shippingContact.deleted);
    }

    // @Test
    public void testShippingContactUpdate() throws ErrorList, Error, JSONException{
        Customer customer = Customer.create(validVisaCard);

        ShippingContact shippingContact = (ShippingContact) customer.shipping_contacts.get(0);

        shippingContact.update(new JSONObject("{ 'email': 'hola@hola.com' }"));

        assertTrue(shippingContact.email.equals("hola@hola.com"));
    }
}
