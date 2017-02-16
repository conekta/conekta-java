package io.conekta;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author mauricio
 */
public class ErrorTest extends ConektaBase {
    JSONObject valid_visa_card;
    JSONObject invalid_visa_card;
    JSONObject invalid_payment_method;
    JSONObject valid_payment_method;
    public ErrorTest() throws JSONException {
        super();
        setApiVersion("1.0.0");
        valid_visa_card = new JSONObject("{'name': 'test', 'cards':['tok_test_visa_4242']}");
        invalid_visa_card = new JSONObject("{'name': 'test', 'cards':[{0:'tok_test_visa_4242'}]}");
        invalid_payment_method = new JSONObject("{'description':'Stogies'," +
                "'reference_id':'9839-wolf_pack'," +
                "'amount':10," +
                "'currency':'MXN'}");
        valid_payment_method = new JSONObject("{'description':'Stogies'," +
                "'reference_id':'9839-wolf_pack'," +
                "'amount':20000," +
                "'currency':'MXN',"+
                "'details':{"+
                "   'name': 'Box of Cohiba S1s',"+
                "   'phone': '403-342-0642',"+
                "   'email': 'logan@x-men.org',"+
                "   'line_items': [{'name':'Box of Cohiba S1s', 'description':'Imported from Mex.'}]"+
                " }"+
                "}");
    }

    // @Test
    public void testNoIdError() throws Error, ErrorList {
        try {
            Charge charge = Charge.find(null);
        } catch (Error e) {
            assertTrue(e.message.equals("Could not get the id of Charge instance."));
        }
    }

    public void testNoConnectionError() throws Error, ErrorList {
        String base = Conekta.apiBase;
        Conekta.apiBase = "http://localhost:3001";
        try{
        Customer customer = Customer.create(valid_visa_card);
        } catch (NoConnectionError e) {
            assertTrue(e instanceof NoConnectionError);
        }
        Conekta.apiBase = base;
    }

    public void testApiError() throws Error, ErrorList {
        try{
        Customer customer = Customer.create(invalid_visa_card);
        } catch (ParameterValidationError e) {
            assertTrue(e instanceof ParameterValidationError);
        }
    }
    
    public void testParameterValidationError() throws Error, JSONException, ErrorList {
        JSONObject invalidCustomer = new JSONObject("{}");
        try {
            Customer customer = Customer.create(invalidCustomer);
        } catch(ParameterValidationError e) {
            assertTrue(e instanceof ParameterValidationError);
        }
    }

    public void testAuthenticationError() throws Error, ErrorList {
        unSetApiKey();
        try{
        Customer customer = Customer.create(valid_visa_card);
        } catch (AuthenticationError e) {
            assertTrue(e instanceof AuthenticationError);
        }
        setApiKey();
    }

    public void testParameterValidationError() throws Error, JSONException, ErrorList {
        valid_visa_card = new JSONObject("{'card':'tok_test_visa_4242'}");
        JSONObject params = invalid_payment_method.put("card", valid_visa_card.get("card"));
        try {
            Charge.create(params);
            assertTrue(false);
        } catch (ParameterValidationError e) {
            assertTrue(e instanceof ParameterValidationError);
        }
    }

    public void testProcessingError() throws Error, JSONException, ErrorList {
        JSONObject capture = new JSONObject("{'capture': false}");
        valid_visa_card = new JSONObject("{'card':'tok_test_visa_4242'}");
        JSONObject params = valid_payment_method.put("card", valid_visa_card.get("card")).put("capture", capture.get("capture"));
        Charge charge = Charge.create(params);
        assertTrue(charge.status.equals("pre_authorized"));
        charge.capture();
        try {
            charge.refund();
        } catch(ProcessingError e) {
            assertTrue(e instanceof ProcessingError);
        }
    }

    public void testResourceNotFoundError() throws Error, ErrorList {
        try {
            Charge charge = Charge.find("1");
        } catch (ResourceNotFoundError e) {
            assertTrue(e instanceof ResourceNotFoundError);
        }
    }

}
