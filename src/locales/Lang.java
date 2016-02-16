/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locales;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mauriciomurga
 */
public abstract class Lang {
    public static final String EN = "en";
    public static final String ES = "es";
    public static Properties en = new Properties();
    public static Properties es = new Properties();
    
    public static String translate(String key, HashMap parameters, String locale) {
        try {
            readDirectory();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Lang.class.getName()).log(Level.SEVERE, null, ex);
        }
        String translate = "";
        if (locale.equals("en"))
            translate = en.getProperty(key);
        if (locale.equals("es"))
            translate = es.getProperty(key);
        if (!parameters.isEmpty()) {
            Iterator it = parameters.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                String k = (String) pair.getKey();
                String v = (String) pair.getValue();
                translate = translate.replace("%{"+ k.toLowerCase() +"}", v);
                it.remove(); // avoids a ConcurrentModificationException
            }
        }
        return translate;
    }
    
    protected static void readDirectory() throws IOException {
        if (!en.isEmpty() && !es.isEmpty()) {
            return;
        }
        InputStream inEn = Lang.class.getClassLoader().getResourceAsStream("locales/messages/en.properties");
        InputStream inEs = Lang.class.getClassLoader().getResourceAsStream("locales/messages/es.properties");
        en.load(inEn);
        es.load(inEs);
    }
}
