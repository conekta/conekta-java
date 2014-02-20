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
    public void setApiKey() {
        Conekta.apiKey = "1tv5yJp3xnVZ7eK67m4h";
    }

    public void unSetApiKey() {
        Conekta.apiKey = null;
    }
}
