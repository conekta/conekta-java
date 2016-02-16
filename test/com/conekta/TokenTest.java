package com.conekta;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.conekta.Plan;
import com.conekta.Error;
import com.conekta.ConektaObject;
import com.conekta.Token;
import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;
import static org.junit.Assert.*;

/**
 *
 * @author mauricio
 */
public class TokenTest extends ConektaTest {
    JSONObject params;
    Integer id;

    public TokenTest() throws JSONException, Error {
        super();
    }

    // @Test
    public void testSuccesfulGetToken() throws Error {
       assertTrue(true);
    }
}
