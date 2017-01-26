package io.conekta;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mauricio
 */
public abstract class Conekta {

    public static String apiKey;
    public static String apiBase = "https://api.conekta.io";
    public static String apiVersion = "2.0.0";
    public static final String VERSION = "2.1.0";
    public static String locale = "es";
    

    public static void setApiKey(String apiKey) {
        Conekta.apiKey = apiKey;
    }

    public static void setApiVerion(String version) {
        Conekta.apiVersion = version;
    }
    
    public static void setLocale(String locale) {
        Conekta.locale = locale;
    }
}
