/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package io.conekta;

import java.lang.reflect.Field;
import java.util.HashMap;
import locales.Lang;
import org.json.JSONArray;
import org.json.JSONObject;

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
    public Order order;
    public Boolean shippable;
    public JSONArray tags;
    public String brand;
    public HashMap metadata;
    public Boolean deleted;
    
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
    
    @Override
    public String instanceUrl() throws Error {
        if (id == null || id.length() == 0) {
            HashMap parameters = new HashMap();
            parameters.put("RESOURCE", this.getClass().getSimpleName());
            throw new Error(Lang.translate("error.resource.id", parameters, Lang.EN),
                    Lang.translate("error.resource.id_purchaser", parameters, Conekta.locale), null, null, null);
        }
        String base = this.order.instanceUrl();
        return base + "/line_items/" + id;
    }
    
    @Override
    public void update(JSONObject params) throws Error, ErrorList {
        super.update(params);
    }

    public LineItems delete() throws Error, ErrorList {
        return ((LineItems) this.delete(null, null));
    } 
}
