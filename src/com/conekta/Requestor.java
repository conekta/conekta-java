package com.conekta;

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
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import org.json.JSONArray;
import org.json.JSONException;
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

    private void setHeaders() throws Error {
        JSONObject userAgent = new JSONObject();
        try {
            userAgent.put("bindings_version", Conekta.VERSION);
            userAgent.put("lang", "java");
            userAgent.put("lang_version", System.getProperty("java.version"));
            userAgent.put("publisher", "conekta");
            // Set Headers
            this.connection.setRequestProperty("X-Conekta-Client-User-Agent", userAgent.toString());
            this.connection.setRequestProperty("User-Agent", "Conekta/v1 JavaBindings/" + Conekta.VERSION);
            this.connection.setRequestProperty("Accept", "application/vnd.conekta-v"+ Conekta.apiVersion +"+json");
            this.connection.setRequestProperty("Content-Type", " application/x-www-form-urlencoded");
            this.connection.setRequestProperty("Authorization", "Basic " + Base64.encode((Conekta.apiKey).getBytes()));
        } catch (Exception e) {
            throw new Error(e.toString(), null, null, null);
        }
    }

    public Object request(String method, String url, JSONObject params) throws Error {
        URL urlObj;
        try {
            // SSL
            InputStream is = getClass().getResourceAsStream("/ssl_data/ca_bundle.crt");
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate caCert = (X509Certificate) cf.generateCertificate(is);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
            ks.load(null); // You don't need the KeyStore instance to come from a file.
            ks.setCertificateEntry("caCert", caCert);
            tmf.init(ks);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);

            urlObj = new URL(Requestor.apiUrl(url));
            connection = (HttpURLConnection) urlObj.openConnection();
            connection.setReadTimeout(60000);
            connection.setConnectTimeout(15000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod(method);
            this.setHeaders();
        } catch (Exception e) {
            throw new Error(e.toString(), null, null, null);
        }

        if (params != null) {
            OutputStream os = null;
            try {
                os = connection.getOutputStream();
            } catch (Exception e) {
                throw new NoConnectionError("Could not connect to " + Conekta.apiBase, null, null, null);
            }
            try {
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(Requestor.getQuery(params, null));
                writer.flush();
                writer.close();
                os.close();
            } catch (Exception e) {
                throw new Error(e.toString(), null, null, null);
            }

        }
        int responseCode;
        try {
            responseCode = connection.getResponseCode();
        } catch (Exception e) {
            throw new Error(e.toString(), null, null, null);
        }
        BufferedReader in;
        if (responseCode != 200) {
            in = new BufferedReader(
                    new InputStreamReader(connection.getErrorStream()));
        } else {
            try {
                in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
            } catch (Exception e) {
                throw new Error(e.toString(), null, null, null);
            }
        }
        String inputLine;
        StringBuffer response = new StringBuffer();
        Object object = null;
        try {
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
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
                    throw new Error("invalid response: " + response.toString(), null, null, null);
                // Other
            }
            if (responseCode != 200) {
                Error.errorHandler((JSONObject) object, responseCode);
            }
            in.close();
        } catch (Exception e) {
            JSONObject error = null;
            try {
                error = new JSONObject("{'message':'" + URLEncoder.encode(e.toString()) + "'}");
            } catch (JSONException ex) {
                System.out.println("");
            }
            Error.errorHandler(error, responseCode);

        }
        return object;
    }

    private static String getQuery(JSONObject jsonObject, String index) throws Exception {
        StringBuilder result = new StringBuilder();
        Iterator itr = jsonObject.keys();
        Boolean first = true;
        while (itr.hasNext()) {
            if (first) {
                first = false;
            } else {
                result.append("&");
            }
            String key = itr.next().toString();
            Object value = jsonObject.get(key);
            if (value instanceof JSONObject) {
                if (index != null) {
                    key = index + "[" + key + "]";
                }
                result.append(Requestor.getQuery(((JSONObject) value), key));
            } else if (value instanceof JSONArray) {
                JSONArray array = ((JSONArray) value);
                    for (int i = 0; i < array.length(); i++) {
                        if (array.get(i) instanceof JSONObject) {
                            if (index != null) {
                                key = index + "[" + key + "][]";
                            }
                            result.append(Requestor.getQuery(array.getJSONObject(i), key));
                        } else {
                            if (index != null) {
                                result.append(URLEncoder.encode(index + "[" + key + "]" + "[]", "UTF-8"));
                            } else {
                                result.append(URLEncoder.encode(key + "[]", "UTF-8"));
                            }
                            result.append("=");
                            result.append(URLEncoder.encode(array.getString(i), "UTF-8"));
                        }
                    }
                
            } else {
                if (index != null) {
                    result.append(URLEncoder.encode(index + "[" + key + "]", "UTF-8"));
                } else {
                    result.append(key);

                }
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));
            }

        }
        return result.toString();
    }
}
