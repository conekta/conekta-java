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
        setApiVersion("1.0.0");
        Log log = Log.find("5da8d93882c2724a31a78f79");
        assertTrue(log instanceof ConektaObject);
    }

    public void testSuccesfulWhereWithParams() throws Exception {
        setApiVersion("1.0.0");
        JSONObject params = new JSONObject("{'id':'5aa6bb38edbb6e3d63c7156d'}");
        ConektaObject log = Log.where(params);
        assertTrue(log instanceof ConektaObject);
    }

    public void testSuccesfulWhere() throws Exception {
        setApiVersion("1.0.0");
        ConektaObject log = Log.where();
        assertTrue(log instanceof ConektaObject);
    }
}