package com.conekta;


import junit.framework.TestCase;

/**
 *
 * @author mauricio
 */
public class ConektaTest extends TestCase {
    public ConektaTest() {
        setApiKey();
    }

    public ConektaTest(String key) {
        setPublicApiKey();
    }

    public void setApiKey() {
        Conekta.apiKey = "key_eYvWV7gSDkNYXsmr";
    }

    public void setPublicApiKey() {
        Conekta.apiKey = "key_KJysdbf6PotS2ut2";
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
