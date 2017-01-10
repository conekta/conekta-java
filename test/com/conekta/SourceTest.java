package com.conekta;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author L.Carlos
 */
public class SourceTest  extends ConektaTest {
    JSONObject validCustomer;
    
    public SourceTest() throws JSONException{
        setApiVersion("1.1.0");
        
        validCustomer  = new JSONObject("{" +
        "  'email': 'hola@hola.com'," +
        "  'name': 'John Constantine'," +
        "  'sources': [{" +
        "    'token_id': 'tok_test_visa_4242'," +
        "    'type': 'card'" +
        "  }]" +
        "}");
    }
    
    // @Test
    public void testSuccesfulDeleteSources() throws Error, ErrorList{
        Customer customer = Customer.create(validCustomer);
        
        Source source = (Source) customer.sources.get(0);
        
        source.delete();
        
        assertTrue(source.deleted); 
    }
    
    // @Test
    public void testSuccesfulUpdateSources() throws Error, ErrorList, JSONException{
        Customer customer = Customer.create(validCustomer);
        
        Source source = (Source) customer.sources.get(0);
        
        source.update(new JSONObject("{'exp_month': '11'}"));
        
        assertTrue(source.exp_month.equals("11"));
    }
}
