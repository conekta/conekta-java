/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import junit.framework.TestCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mauricio
 */
public class ChargeTest extends TestCase {

    JSONObject valid_payment_method;
    JSONObject valid_visa_card;

    public ChargeTest() throws JSONException {
        valid_payment_method = new JSONObject("{'description':'Stogies'," +
                "'reference_id':'9839-wolf_pack'," +
                "'amount':20000," +
                "'currency':'MXN'," +
                "'card':'tok_test_visa_4242'}");
        valid_visa_card = new JSONObject("{'card':'tok_test_visa_4242'}");
    }

    // @Test
    public void testSuccesfulFindCharge() throws Exception {
        JSONObject params = valid_payment_method.put("card", valid_visa_card.get("card"));
        Charge charge = Charge.create(params);
        assertTrue(charge.status.equals("paid"));
        charge = Charge.find(charge.id);
        assertTrue(charge instanceof Charge);
    }
}
