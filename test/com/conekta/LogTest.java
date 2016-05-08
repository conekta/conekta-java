/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conekta;

import org.json.JSONException;
import org.json.JSONObject;
import static org.junit.Assert.*;

/**
 *
 * @author picharras
 */
public class LogTest extends ConektaTest {

    public LogTest() throws JSONException {
        super();
    }

    public void testSuccesfulFind() throws Exception {
        Log log = Log.find("5709515719ce880954005f91");
        assertTrue(log instanceof ConektaObject);
    }

    public void testSuccesfulWhereWithParams() throws Exception {
        JSONObject params = new JSONObject("{'id':'5709515719ce880954005f91'}");
        ConektaObject log = Log.where(params);
        System.out.print(log);
        assertTrue(log instanceof ConektaObject);
    }

    public void testSuccesfulWhere() throws Exception {
        ConektaObject log = Log.where();
       assertTrue(log instanceof ConektaObject);
    }
}