package com.conekta;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author L.Carlos
 */
public class ConektaList extends ConektaObject{
    public String elements_type;
    public JSONObject params;
    public String starting_after;
    public String ending_before;
    public boolean has_more;
    public int total;

    public ConektaList(String elements_type, JSONObject params) {
        this.elements_type = elements_type;
        this.params = params;
    }
    
    public ConektaList(String elements_type) {
        this.elements_type = elements_type;
        this.params = new JSONObject();
    }
    
    public void addElement(ConektaObject element){
        this.add(element);
        total = total + 1;
    }
    
    public void loadFrom(JSONObject values) throws JSONException, Error {
        this.starting_after = values.getString("starting_after");
        this.ending_before = values.getString("ending_before");
        this.has_more = values.getBoolean("has_more");
        this.total = values.getInt("total");
        
        this.clear();

        this.loadFromArray(values.getJSONArray("data"));
    }
    
    public ConektaList previous(JSONObject options) throws JSONException, Error, ErrorList{
        if(!this.isEmpty()){
            ConektaObject object = (ConektaObject) this.get(0);
            this.params.put("ending_before", object.id);
        }
        
        this.params.remove("starting_after");
        this.moveCursor(options.getInt("limit"));
        
        return this;
    }

    public ConektaList next(JSONObject options) throws JSONException, Error, ErrorList{
        int last = this.size() - 1;
        if(!this.isEmpty()){
            ConektaObject object = (ConektaObject) this.get(last);
            this.params.put("starting_after", object.id);
        }
        
        this.params.remove("ending_before");
        this.moveCursor(options.getInt("limit"));
        
        return this;
    }
    
    
    public ConektaList moveCursor(int limit) throws JSONException, Error, ErrorList{
        this.params.put("limit", limit);
        
        String className = (String) Utils.getInstance().types.get(this.elements_type);     
        String url = Resource.classUrl(className);
        
        Requestor requestor = new Requestor();
        JSONObject response = (JSONObject) requestor.request("GET", url, this.params);
        this.loadFrom(response);
        
        return this;
    }
}
