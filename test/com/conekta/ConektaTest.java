package com.conekta;


import junit.framework.TestCase;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
}
