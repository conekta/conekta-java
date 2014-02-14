/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mauricio
 */
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

public class ConektaObject extends ArrayList {

    private HashMap values;
    String id;

    public ConektaObject(String id) {
        this.values = new HashMap();
        this.id = id;
    }

    public ConektaObject() {
        this.values = new HashMap();
        this.id = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getVal(String key) {
        return this.values.get(key);
    }

    public void setVal(String key, Object value) {
        this.values.put(key, value);
    }

    public void loadFromArray(JSONArray jsonArray) throws Exception {
        for (int i = 0; i < jsonArray.length(); i++) {
            Object conektaObject = (Object) Class.forName(
                    ConektaObject.toCamelCase(jsonArray.getJSONObject(i).getString("object"))).newInstance();
            jsonArray.getJSONObject(i).getString("object");
            ((ConektaObject) conektaObject).loadFromObject(jsonArray.getJSONObject(i));
            this.add(conektaObject);
        }
    }

    public void loadFromObject(JSONObject jsonObject) throws Exception {
        Iterator itr = jsonObject.keys();
        while (itr.hasNext()) {
            String key = itr.next().toString();
            Object obj = jsonObject.get(key);
            if (obj instanceof JSONObject) {
                // convert to conekta object
                if (((JSONObject) obj).has("object")) {
                    if (((JSONObject) obj).getString("object").equals("card_payment")) {
                        PaymentMethod payment_method = new CardPayment((JSONObject)obj);
                        payment_method.loadFromObject((JSONObject)obj);
                        this.setVal(key, payment_method);
                    } else {
                        ConektaObject conektaObject = (ConektaObject) Class.forName(ConektaObject.toCamelCase(key)).newInstance();
                        conektaObject.loadFromObject(((JSONObject) obj));
                        this.setVal(key, conektaObject);
                    }
                }
            } else if (obj instanceof JSONArray) {
                if (((JSONArray) obj).length() > 0) {
                    this.setVal(key, obj);
                }
            } else {
                if (!obj.equals(null)) {
                    // Set field of instance
                    try {
                        Field field = this.getClass().getField(key);
                        Object o = new Object();
                        field.setAccessible(true);
                        field.set(this, obj);
                    } catch (Exception e) {
                        // field was not found or type did not match
                    }
                    this.setVal(key, obj);
                }
            }
        }
    }

    @Override
    public String toString() {
        if (this.getClass().getName().equals("ConektaObject")) {
            StringBuilder result = new StringBuilder();
            result.append("[");
            for (int i = 0; i < this.size(); i++) {
                result.append(((ConektaObject) this.get(i)).values.toString());
                if ((i + 1) < this.size()) {
                    result.append(",");
                }
            }
            result.append("]");
            return result.toString();
        } else {
            return this.values.toString();
        }
    }

    protected static String toCamelCase(String s) {
        String[] parts = s.split("_");
        String camelCaseString = "";
        for (String part : parts) {
            camelCaseString = camelCaseString + toProperCase(part);
        }
        return camelCaseString;
    }

    protected static String toProperCase(String s) {
        return s.substring(0, 1).toUpperCase() +
                s.substring(1).toLowerCase();
    }
}
