package io.conekta;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import locales.Lang;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author mauricio
 */
public class Resource extends ConektaObject {

    public Resource(String id) {
        super(id);
    }

    public Resource() {
        super();
    }

    public static String classUrl(String className) {
        String base = "/" + className.toLowerCase().replace("io.conekta.", "") + "s";
        return base;
    }

    public String instanceUrl() throws Error {
        String className = this.getClass().getSimpleName();
        if (id == null || id.length() == 0) {
            HashMap parameters = new HashMap();
            parameters.put("RESOURCE", className);
            throw new Error(Lang.translate("error.resource.id", parameters, Lang.EN),
                    Lang.translate("error.resource.id_purchaser", parameters, Conekta.locale), null, null, null);
        }
        String base = Resource.classUrl(className);
        return base + "/" + id;
    }

    protected static ConektaObject scpFind(String className, String id) throws Error, ErrorList {
        Constructor c;
        ConektaObject resource;
        try {
            c = Class.forName(className).getConstructor(String.class);
            resource = (ConektaObject) c.newInstance(id);
        } catch (Exception e) {
            throw new Error(e.toString(), null, null, null, null);
        }
        Requestor requestor = new Requestor();
        String url = ((Resource) resource).instanceUrl();
        JSONObject jsonObject = (JSONObject) requestor.request("GET", url, null);
        try {
            resource.loadFromObject(jsonObject);
        } catch (Exception e) {
            throw new Error(e.toString(), null, null, null, null);
        }
        return resource;
    }

    protected static ConektaObject scpCreate(String className, JSONObject params) throws Error, ErrorList {
        Requestor requestor = new Requestor();
        String url = Resource.classUrl(className);
        JSONObject jsonObject = (JSONObject) requestor.request("POST", url, params);
        
        ConektaObject resource;
        try {
            resource = (ConektaObject) Class.forName(className).newInstance();
            resource.loadFromObject(jsonObject);
        } catch (Exception e) {
            throw new Error(e.toString(), null, null, null, null);
        }
        return resource;
    }

    protected static ConektaObject scpWhere(String className, JSONObject params) throws Error, ErrorList {
        Requestor requestor = new Requestor();
        String url = Resource.classUrl(className);
        JSONArray jsonArray = (JSONArray) requestor.request("GET", url, params);
        ConektaObject resource = new ConektaObject();
        resource.loadFromArray(jsonArray);
        return resource;
    }
    
    protected static ConektaList scpWhereList(String className, JSONObject params) throws Error, JSONException, ErrorList {
        Requestor requestor = new Requestor();
        String url = Resource.classUrl(className);
        JSONObject jsonObject = (JSONObject) requestor.request("GET", url, params);
        ConektaList resource = new ConektaList(className);
        resource.loadFrom(jsonObject);
        return resource;
    }
    
    protected ConektaObject delete(String parent, String member) throws Error, ErrorList {
        this.customAction("DELETE", null, null);
        return this;
    }

    protected void update(JSONObject params) throws Error, ErrorList {
        Requestor requestor = new Requestor();
        String url = this.instanceUrl();
        JSONObject jsonObject = (JSONObject) requestor.request("PUT", url, params);
        try {
            this.loadFromObject(jsonObject);
        } catch (Exception e) {
            throw new Error(e.toString(), null, null, null, null);
        }

    }

    protected ConektaObject customAction(String method, String action, JSONObject params) throws Error, ErrorList {
        if (method == null) {
            method = "POST";
        }
        Requestor requestor = new Requestor();
        String url = this.instanceUrl();
        if (action != null) {
            url = url + "/" + action;
        }
        JSONObject jsonObject = (JSONObject) requestor.request(method, url, params);
        try {
            this.loadFromObject(jsonObject);
        } catch (Exception e) {
            throw new Error(e.toString(), null, null, null, null);
        }
        return this;
    }
 
    protected ConektaObject createMember(String member, JSONObject params) throws Error, ErrorList {
        Requestor requestor = new Requestor();
        String url = this.instanceUrl() + "/" + member;
        JSONObject jsonObject = (JSONObject) requestor.request("POST", url, params);
        ConektaObject conektaObject = null;

        try {
            Field field = this.getClass().getField(member);
            field.setAccessible(true);
            String className;
            String parentClassName = this.getClass().getSimpleName().substring(0, 1).toLowerCase() + this.getClass().getSimpleName().substring(1);
            if (field.get(this).getClass().getSimpleName().equals("ConektaObject")) {
                className = "io.conekta." + member.substring(0, 1).toUpperCase() + member.substring(1, member.length() - 1);
                conektaObject = (ConektaObject) Class.forName(className).newInstance();
                conektaObject.loadFromObject(jsonObject);

                conektaObject.getClass().getField(parentClassName).set(conektaObject, this);

                ConektaObject objects = ((ConektaObject) field.get(this));
                objects.add(conektaObject);
                field.set(this, objects);
            } else if(field.get(this).getClass().getSimpleName().equals("ConektaList")){
                if(jsonObject.has("type") && jsonObject.get("type").equals("card")) {
                    Card card = new Card();
                    card.loadFromObject(jsonObject);
                    ConektaList list = (ConektaList) field.get(this);

                    list.addElement(card);

                    conektaObject = card;
                } else {
                    className = Utils.getInstance().classes.get(member).toString();
                    conektaObject = (ConektaObject) Class.forName(className).newInstance();
                    conektaObject.loadFromObject(jsonObject);
                    ConektaList list = (ConektaList) field.get(this);

                    list.addElement(conektaObject);
                }
            } else {
                className = "io.conekta." + member.substring(0, 1).toUpperCase() + member.substring(1);
                conektaObject = (ConektaObject) Class.forName(className).newInstance();
                conektaObject.loadFromObject(jsonObject);
                conektaObject.getClass().getField(parentClassName).set(conektaObject, this);

                this.setVal(member, conektaObject);
                field.set(this, conektaObject);
                this.loadFromObject(params);
            }
        } catch (Exception e) {
            throw new Error(e.toString(), null, null, null, null);
        }

        return conektaObject;
    }

    protected ConektaObject createMemberWithRelation(String member, JSONObject params, ConektaObject parent) throws NoSuchFieldException, Error, ErrorList, IllegalArgumentException, IllegalAccessException{
        String parentClass = parent.getClass().getSimpleName().toLowerCase();
        
        ConektaObject child = createMember(member, params);
        
        Field field = child.getClass().getField(parentClass);
        field.setAccessible(true);
        
        field.set(child, this);
        
        return child;
    }
}
