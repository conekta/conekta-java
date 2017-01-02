package com.conekta;

import org.json.JSONObject;

/**
 *
 * @author mauricio
 */
public class Token extends Resource {

    public Boolean livemode;
    public Boolean used;

    public Token(String id) {
        super(id);
    }

    public Token() {
        super();
    }

    public static Token find(String id) throws Error, ErrorList {
        String className = Token.class.getCanonicalName();
        return (Token) scpFind(className, id);
    }

    public static Token create(JSONObject params) throws Error, ErrorList {
        String className = Token.class.getCanonicalName();
        return (Token) scpCreate(className, params);
    }
}
