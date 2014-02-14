
import java.lang.reflect.Constructor;
import org.json.JSONArray;
import org.json.JSONObject;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
        String base = "/" + className.toLowerCase() + "s";
        return base;
    }
    public String instanceUrl() throws Exception {
        if (id.length() == 0) {
            throw new Exception("no id");
        }
        String className = this.getClass().getName();
        String base = Resource.classUrl(className);
        return base + "/" + id;
    }
    protected static Object scpFind(String className, String id) throws Exception {
        Constructor c = Class.forName(className).getConstructor(String.class);
        Object resource = c.newInstance(id);
        Requestor requestor = new Requestor();
        String url = ((Resource)resource).instanceUrl();
        JSONObject jsonObject = (JSONObject) requestor.request("GET", url, null);
        ((ConektaObject)resource).loadFromObject(jsonObject);
        return resource;
    }
    protected static Object scpCreate(String className, JSONObject params) throws Exception {
        Requestor requestor = new Requestor();
        String url = Resource.classUrl(className);
        JSONObject jsonObject = (JSONObject) requestor.request("POST", url, params);
        Object resource = Class.forName(className).newInstance();
        ((ConektaObject)resource).loadFromObject(jsonObject);
        return resource;
    }

    protected static ConektaObject scpWhere(String className, JSONObject params) throws Exception {
        Requestor requestor = new Requestor();
        String url = Resource.classUrl(className);
        JSONArray jsonArray =  (JSONArray) requestor.request("GET", url, params);
        ConektaObject resource = new ConektaObject();
        resource.loadFromArray(jsonArray);
        return resource;
    }
}
