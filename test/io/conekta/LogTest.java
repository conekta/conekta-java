package io.conekta;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author picharras
 */
public class LogTest extends ConektaBase {

    public LogTest() throws JSONException {
        super();
        setApiVersion("1.0.0");
    }

    public void testSuccesfulFind() throws Exception {
        Log log = Log.find("5709515719ce880954005f91");
        assertTrue(log instanceof ConektaObject);
    }

    public void testSuccesfulWhereWithParams() throws Exception {
        JSONObject params = new JSONObject("{'id':'5709515719ce880954005f91'}");
        ConektaObject log = Log.where(params);
        assertTrue(log instanceof ConektaObject);
    }

    public void testSuccesfulWhere() throws Exception {
        ConektaObject log = Log.where();
        assertTrue(log instanceof ConektaObject);
    }
}