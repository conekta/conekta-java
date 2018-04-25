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
        setApiVersion("0.3.0");
    }

    public void testSuccesfulFind() throws Exception {
        Log log = Log.find("59ad9325b795b02ebdfef2be");
        assertTrue(log instanceof ConektaObject);
    }

    public void testSuccesfulWhereWithParams() throws Exception {
        JSONObject params = new JSONObject("{'id':'59ad9325b795b02ebdfef2be'}");
        ConektaObject log = Log.where(params);
        assertTrue(log instanceof ConektaObject);
    }

    public void testSuccesfulWhere() throws Exception {
        ConektaObject log = Log.where();
        assertTrue(log instanceof ConektaObject);
    }
}