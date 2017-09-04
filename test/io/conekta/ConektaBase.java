package io.conekta;

import junit.framework.TestCase;

/**
 *
 * @author mauricio
 */
public class ConektaBase extends TestCase {
    public ConektaBase() {
        setApiKey();
    }

    public ConektaBase(String key) {
        setPublicApiKey();
    }

    public void setApiKey() {
        Conekta.apiKey = "key_ZLy4aP2szht1HqzkCezDEA";
    }

    public void setPublicApiKey() {
        Conekta.apiKey = "key_OfWoJc2fw6oEydKspmyr76Q";
    }

    public void unSetApiKey() {
        Conekta.apiKey = null;
    }
    
    public void setApiVersion(String version) {
        Conekta.apiVersion = version;
    }
    
    public void setApiBase(String base) {
        Conekta.apiBase = base;
    }
    
    public void setApiKey(String key) {
        Conekta.apiKey = key;
    }
}
