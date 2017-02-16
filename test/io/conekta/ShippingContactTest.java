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
        setApiVersion("2.0.0");
        validVisaCard = new JSONObject("{"
                + "'name': 'Nombre de prueba', "
                + "'email': 'test@test.com', "
                + "'shipping_contacts': [{" +
                "    'receiver': 'John Williams'," +
                "    'phone': '+5213353319758'," +
                "    'address': {" +
                "        'street1': '250 Alexis St'," +
                "        'city': 'Red Deer'," +
                "        'state': 'Alberta'," +
                "        'country': 'CA'," +
                "        'postal_code': 'T4N 0B8'" +
                "    }" +
                "}," +
                "{" +
                "    'receiver': 'John Williams'," +
                "    'phone': '+5213353319758'," +
                "    'address': {" +
                "        'street1': '250 Alexis St'," +
                "        'city': 'Red Deer'," +
                "        'state': 'Alberta'," +
                "        'country': 'CA'," +
                "        'postal_code': 'T4N 0B8'" +
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

        shippingContact.update(new JSONObject("{ 'phone': '+5213353319759' }"));

        assertTrue(shippingContact.phone.equals("+5213353319759"));
    }
}
