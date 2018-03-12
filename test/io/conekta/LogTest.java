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
        Log log = Log.find("5aa6bb38edbb6e3d63c7156d");
        assertTrue(log instanceof ConektaObject);
    }

    public void testSuccesfulWhereWithParams() throws Exception {
        JSONObject params = new JSONObject("{'id':'5aa6bb38edbb6e3d63c7156d'}");
        ConektaObject log = Log.where(params);
        assertTrue(log instanceof ConektaObject);
    }

    public void testSuccesfulWhere() throws Exception {
        ConektaObject log = Log.where();
        assertTrue(log instanceof ConektaObject);
    }
}