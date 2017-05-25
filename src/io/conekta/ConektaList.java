package io.conekta;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author L.Carlos
 */
public class ConektaList extends ConektaObject{
    public String elements_type;
    public String next_page_url;
    public String previous_page_url;
    public boolean has_more;
    public int total;

    public ConektaList(String elements_type, JSONObject params) {
        this.elements_type = elements_type;
    }
    
    public ConektaList(String elements_type) {
        this.elements_type = elements_type;
    }
    
    public void addElement(ConektaObject element){
        this.add(element);
        total = total + 1;
    }
    
    public void loadFrom(JSONObject values) throws JSONException, Error {
        this.has_more = values.getBoolean("has_more");
        this.total = values.getInt("total");
        this.next_page_url = values.optString("next_page_url");
        this.previous_page_url = values.optString("previous_page_url");
        
        this.clear();

        this.loadFromArray(values.getJSONArray("data"));
    }
    
    public ConektaList previous() throws Error, JSONException, ErrorList{        
        this.moveCursor(this.previous_page_url);
        
        return this;
    }

    public ConektaList next() throws JSONException, Error, ErrorList{
        this.moveCursor(this.next_page_url);
        
        return this;
    }
    
    
    public ConektaList moveCursor(String url) throws JSONException, Error, ErrorList{        
        Requestor requestor = new Requestor();
        JSONObject response = (JSONObject) requestor.request("GET", url, null);
        this.loadFrom(response);
        
        return this;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{elements_type=");
		builder.append(elements_type);
		builder.append(", next_page_url=");
		builder.append(next_page_url != null && !next_page_url.isEmpty() ? next_page_url : "null");
		builder.append(", previous_page_url=");
		builder.append(previous_page_url != null && !previous_page_url.isEmpty() ? previous_page_url : "null");
		builder.append(", has_more=");
		builder.append(has_more);
		builder.append(", total=");
		builder.append(total);
		builder.append(", data: [");
		int size = this.size();
		if (size > 0){
			for(int i = 0; i < size; i++){
				builder.append(super.get(i).toString());
				if (i != size -1 )
					builder.append(", ");
			}
		}
		builder.append(" ]");
		builder.append("}");
		return builder.toString();
	}
 
    
}
