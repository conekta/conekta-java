package io.conekta;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author L.Carlos
 */
public class FiscalEntityTest extends ConektaBase {

    JSONObject validVisaCard;

    public FiscalEntityTest() throws JSONException {
        super();
        setApiVersion("2.0.0");
        validVisaCard = new JSONObject("{"
                + "'name': 'Nombre de prueba', "
                + "'email': 'test@test.com', "
                + "'cards':['tok_test_visa_4242'],"
                + "'fiscal_entities': [{" +
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
                "}]"
                + "}");

    }

    // @Test
    public void testFiscalEntityDelete() throws ErrorList, Error{
        Customer customer = Customer.create(validVisaCard);

        FiscalEntity fiscalEntity = (FiscalEntity) customer.fiscal_entities.get(0);

        fiscalEntity.delete();

        assertTrue(fiscalEntity.deleted);
    }

    // @Test
    public void testFiscalEntityUpdate() throws ErrorList, Error, JSONException{
        Customer customer = Customer.create(validVisaCard);

        FiscalEntity fiscalEntity = (FiscalEntity) customer.fiscal_entities.get(0);

        fiscalEntity.update(new JSONObject("{'name': 'second name'}"));

        assertTrue(fiscalEntity.name.equals("second name"));
    }
}
