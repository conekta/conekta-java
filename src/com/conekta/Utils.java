package com.conekta;

import java.util.HashMap;

/**
 *
 * @author L.Carlos
 */
public class Utils {

    HashMap types = new HashMap();
    HashMap classes = new HashMap();

    private Utils() {
        types.put("Order", Order.class.getSimpleName());
        classes.put("fiscal_entities", FiscalEntity.class.getCanonicalName());
        classes.put("discount_lines", DiscountLine.class.getCanonicalName());
        classes.put("shipping_contacts", ShippingContact.class.getCanonicalName());
        classes.put("tax_lines", TaxLine.class.getCanonicalName());
        classes.put("sources", Source.class.getCanonicalName());
    }

    public static Utils getInstance() {
        return UtilsHolder.INSTANCE;
    }

    private static class UtilsHolder {

        private static final Utils INSTANCE = new Utils();
    }
}
