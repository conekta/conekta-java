package com.conekta;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author mauricio
 */
public class TokenTest extends ConektaTest {
    JSONObject params;
    Integer id;

    public TokenTest() throws JSONException, Error {
        super("public");
    }

    // @Test
    public void testSuccesfulGetToken() throws Error {
       assertTrue(true);
    }
}
