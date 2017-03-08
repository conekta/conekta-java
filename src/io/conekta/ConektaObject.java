package io.conekta;

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
    public String id;

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

    public void loadFromArray(JSONArray jsonArray) throws Error {
        loadFromArray(jsonArray, null);
    }

    public void loadFromArray(JSONArray jsonArray, String className) throws Error {
        for (int i = 0; i < jsonArray.length(); i++) {
            ConektaObject conektaObject = aux(jsonArray, className, i);
            this.add(conektaObject);
        }
    }

    protected static ConektaObject aux(JSONArray jsonArray, String className, Integer i) throws Error {
        String key;
        JSONObject jsonObject;
        try {
            if (className != null) {
                key = className;
            } else {
                key = jsonArray.getJSONObject(i).getString("object");
            }
            jsonObject = jsonArray.getJSONObject(i);
        } catch (Exception e) {
            throw new Error(e.toString(), null, null, null, null);
        }
        ConektaObject conektaObject = ConektaObjectFromJSONFactory.ConektaObjectFactory(jsonObject, key);
        try {
            conektaObject.loadFromObject(jsonObject);
        } catch (Exception e) {
            throw new Error(e.toString(), null, null, null, null);
        }
        return conektaObject;
    }

    public void loadFromObject(JSONObject jsonObject) throws Exception {
        Iterator itr = jsonObject.keys();
        while (itr.hasNext()) {
            String key = itr.next().toString();
            Object obj = jsonObject.get(key);
            
            try {
                Field field = this.getClass().getField(key);
                if (obj instanceof JSONObject &&
                        ((JSONObject) obj).has("object") &&
                        field.getType().getPackage().getName().equals("io.conekta")) {
                    setFromConektaObject((JSONObject) obj, key);
                } else if (obj instanceof JSONArray) {
                    JSONArray jsonArray = (JSONArray) obj;
                    if (jsonArray.length() > 0) {
                        setFromJSONArray(jsonArray, key);
                    }
                } else if(obj instanceof JSONObject && !obj.equals(null)){
                    setFromHash(jsonObject, key);
                } else {
                    setValue(key, obj);
                }
            } catch (NoSuchFieldException e) {
                setFromVerticalFields(obj, key);
            }
        }
    }
    
    private void setValue(String key, Object value) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field field = this.getClass().getField(key);
        field.setAccessible(true);
        field.set(this, value);
        this.setVal(key, value);
    }
    
    private void setFromJSONArray(JSONArray array, String key) throws Error, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        ConektaObject conektaObject = new ConektaObject();
        for (int i = 0; i < array.length(); i++) {
            if ((array.getJSONObject(0).has("object"))) {
                conektaObject.add(ConektaObjectFromJSONFactory.ConektaObjectFactory(array.getJSONObject(i), array.getJSONObject(i).getString("object")));
            } else {
                conektaObject.add(ConektaObjectFromJSONFactory.ConektaObjectFactory(array.getJSONObject(i), key));
            }
        }
        setValue(key, conektaObject);
    }
    
    private void setFromHash(JSONObject jsonObject, String key) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        JSONObject hashObject = jsonObject.getJSONObject(key);
                            
        Iterator jsonKeys = hashObject.keys();
        HashMap map = new HashMap();
        while(jsonKeys.hasNext()){
            String k = jsonKeys.next().toString();
            Object value = hashObject.get(k);

            map.put(k, value);
        }
        setValue(key, map);   
    }    
    
    private void setFromConektaObject(JSONObject obj, String key) throws Error, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        ConektaObject conektaObject = ConektaObjectFromJSONFactory.ConektaObjectFactory((JSONObject) obj, key);
        setValue(key, conektaObject);
    }
    
    private void setFromVerticalFields(Object obj, String key){
        if (this.getClass().getCanonicalName().equals("io.conekta.LineItems")) {
            ((LineItems)this).addVerticalRelatedField(key, obj.toString());
            this.setVal(key, obj);
        }
    }

    @Override
    public String toString() {
        if (this.getClass().getSimpleName().equals("ConektaObject")) {
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
