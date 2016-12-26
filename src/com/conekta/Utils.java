package com.conekta;

import java.util.HashMap;

/**
 *
 * @author L.Carlos
 */
public class Utils {
    
    HashMap types = new HashMap();
    
    private Utils() {
        types.put("Order", Order.class.getSimpleName());
    }
    
    public static Utils getInstance() {
        return UtilsHolder.INSTANCE;
    }
    
    private static class UtilsHolder {

        private static final Utils INSTANCE = new Utils();
    }
}
