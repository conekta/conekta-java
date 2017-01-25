package com.conekta;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author L.Carlos
 */
public class ErrorList extends Exception{
    
    public ArrayList<Error> details = new ArrayList<Error>();
    public String object;
    public String type;
    
    public ErrorList(String object, String type){
        this.object = object;
        this.type = type;
    }
    
    public static void errorHandle(JSONObject response, int httpStatus) throws JSONException, ErrorList {
        JSONArray errors = response.getJSONArray("details");
  
        ErrorList errorList = new ErrorList(response.getString("object"), response.getString("type"));
        if(response.has("details") && errors.length() > 0) {
            for(int x = 0; x < errors.length(); x++){
                JSONObject error = errors.getJSONObject(x);
                
                errorList.details.add(new Error(error, httpStatus));
            }
        }
        
        throw errorList;
    }
    
}
