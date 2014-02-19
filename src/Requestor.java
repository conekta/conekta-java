/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mauricio
 */
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

public class Requestor {

    public String apiKey;
    protected HttpURLConnection connection;

    public Requestor() {
        this.apiKey = Conekta.apiKey;
    }

    public static String apiUrl(String url) {
        String apiBase = Conekta.apiBase;
        return apiBase + url;
    }

    private void setHeaders() throws Exception {
        JSONObject userAgent = new JSONObject();
        userAgent.put("bindings_version", Conekta.VERSION);
        userAgent.put("lang", "php");
        userAgent.put("lang_version", System.getProperty("java.version"));
        userAgent.put("publisher", "conekta");
        // Set Headers
        this.connection.setRequestProperty("X-Conekta-Client-User-Agent", userAgent.toString());
        this.connection.setRequestProperty("User-Agent", "Conekta/v1 JavaBindings/" + Conekta.VERSION);
        this.connection.setRequestProperty("Accept", "application/vnd.conekta-v0.3.0+json");
        this.connection.setRequestProperty("Authorization", "Basic " + Base64.encode((Conekta.apiKey + " ").getBytes()));
    }

    public Object request(String method, String url, JSONObject params) throws Exception {
        URL obj = new URL(Requestor.apiUrl(url));
        connection = (HttpURLConnection) obj.openConnection();
        connection.setReadTimeout(60000);
        connection.setConnectTimeout(15000);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestMethod(method);
        this.setHeaders();

        if (params != null) {
            try {
                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(Requestor.getQuery(params, null));
                writer.flush();
                writer.close();
                os.close();
            } catch(Exception e) {
                throw new NoConnectionError("Could not connect to " + Conekta.apiBase, null, null, null);
            }
        }

        int responseCode = connection.getResponseCode();
        BufferedReader in;
        if (responseCode != 200) {
            in = new BufferedReader(
                new InputStreamReader(connection.getErrorStream()));
        } else {
            in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        }
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        Object object;
        switch (response.toString().charAt(0)) {
            // {
            case 123:
                object = new JSONObject(response.toString());
                break;
            // [
            case 91:
                object = new JSONArray(response.toString());
                break;
            default:
                throw new Exception("invalid response");
            // Other
        }
        if (responseCode != 200) {
            Error.errorHandler((JSONObject) object, responseCode);
        }
        in.close();
        return object;
    }

    private static String getQuery(JSONObject jsonObject, String arrayKey) throws Exception {
        StringBuilder result = new StringBuilder();
        Iterator itr = jsonObject.keys();
        Boolean first = true;
        while(itr.hasNext()) {
            if (first) {
                first = false;
            } else {
                result.append("&");
            }
            String key = itr.next().toString();
            Object obj = jsonObject.get(key);
            if (obj instanceof JSONObject) {
                //System.out.println(key + " " + obj.toString());
                // convert to conekta object
                //if (((JSONObject) obj).has("object")) {
                    result.append(Requestor.getQuery(((JSONObject) obj), key));
                //}
                //break;
            } else if (obj instanceof JSONArray) {
                JSONArray array = ((JSONArray)obj);
                for (int i = 0; i < array.length(); i ++) {
                    result.append(Requestor.getQuery(array.getJSONObject(i), key));
                }

            } else {
                if (!obj.equals(null)) {
                    if (arrayKey != null) {
                        result.append(URLEncoder.encode((arrayKey + "[" + key + "]"), "UTF-8"));
                    } else {
                        result.append(URLEncoder.encode(key, "UTF-8"));
                    }
                    result.append("=");
                    result.append(URLEncoder.encode(obj.toString(), "UTF-8"));
                }
            }
        }
        return result.toString();
    }
}
