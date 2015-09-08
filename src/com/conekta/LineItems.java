/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.conekta;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 *
 * @author mauricio
 */
public class LineItems extends Resource {
    public String name;
    public String sku;
    public Integer unit_price;
    public String description;
    public Integer quantity;
    public String type;
    public String category;
    public HashMap vertical_related_fields = new HashMap();
    
    // Helper method to access line item fields
    public String get(String key) {
        try {
            Field field;
            field = this.getClass().getField(key);
            return (String) field.get(this);
        } catch(NoSuchFieldException e) {
            return (String) vertical_related_fields.get(key);
        } catch(IllegalAccessException e) {
            return "";
        }
    }
    
    // Helper method to push key values to vertical related fields
    public void addVerticalRelatedField(String key, String value) {
        vertical_related_fields.put(key, value);
    }
}
