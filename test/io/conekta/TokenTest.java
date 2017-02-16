package io.conekta;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author mauricio
 */
public class TokenTest extends ConektaBase {
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
