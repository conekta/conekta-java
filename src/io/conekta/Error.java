package io.conekta;

import java.util.HashMap;
import locales.Lang;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * @author mauricio
 */
public class Error extends Exception {

    public String message;
    public String message_to_purchaser;
    public String debug_message;
    public String type;
    public Integer code;
    public String params;

    public Error(String message, String message_to_purchaser, String type, Integer code, String params) {
        super(message);
        HashMap parameters = new HashMap();
        parameters.put("BASE", Conekta.apiBase);
        if (message != null) {
            this.message = message;
        } else {
            this.message = Lang.translate("error.requestor.connection", parameters, Lang.EN);
        }
        
        if (message_to_purchaser != null) {
            this.message_to_purchaser = message_to_purchaser;
        } else {
            this.message_to_purchaser = Lang.translate("error.requestor.connection_purchaser", parameters, Conekta.locale);
        }
        
        this.type = type;
        this.code = code;
        this.params = params;
    }
    
    public Error(JSONObject error, int code) throws JSONException {
        super(error.getString("message"));
        String message = error.getString("message");
        this.debug_message = error.getString("debug_message");
        String messageToPurchaser = error.optString("message_to_purchaser");
        String param = error.optString("param", "");
        
        this.code = code;

        HashMap parameters = new HashMap();
        parameters.put("BASE", Conekta.apiBase);
        
        if (message != null){
            this.message = message;
        } else {
            this.message = Lang.translate("error.requestor.connection", parameters, Lang.EN);
        }
        
        if (messageToPurchaser != null){
            this.message_to_purchaser = messageToPurchaser;
        } else {
            this.message_to_purchaser = Lang.translate("error.requestor.connection_purchaser", parameters, Conekta.locale);
        }
        
        this.type = type;
        this.code = code;
        this.params = params;
    }

    @Override
    public String toString() {
        return message;
    }

    static void errorHandler(JSONObject response, Integer responseCode) throws Error {
        String message = response.optString("message");
        String message_to_purchaser = response.optString("message_to_purchaser");
        String type = response.optString("type");
        String params = response.optString("param");

        switch (responseCode) {
            case 400:
                throw new MalformedRequestError(message, message_to_purchaser, type, responseCode, params);
            case 401:
                throw new AuthenticationError(message, message_to_purchaser, type, responseCode, params);
            case 402:
                throw new ProcessingError(message, message_to_purchaser, type, responseCode, params);
            case 404:
                throw new ResourceNotFoundError(message, message_to_purchaser, type, responseCode, params);
            case 422:
                throw new ParameterValidationError(message, message_to_purchaser, type, responseCode, params);
            case 500:
                throw new ApiError(message, message_to_purchaser, type, responseCode, params);
            default:
                throw new Error(message, message_to_purchaser, type, responseCode, params);
        }
    }
}
class ApiError extends Error {

    ApiError(String message, String message_to_purchaser, String type, Integer code, String params) {
        super(message, message_to_purchaser, type, code, params);
    }
}

class NoConnectionError extends Error {

    NoConnectionError(String message, String message_to_purchaser, String type, Integer code, String params) {
        super(message, message_to_purchaser, type, code, params);
    }
}

class AuthenticationError extends Error {

    AuthenticationError(String message, String message_to_purchaser, String type, Integer code, String params) {
        super(message, message_to_purchaser, type, code, params);
    }
}

class ParameterValidationError extends Error {

    ParameterValidationError(String message, String message_to_purchaser, String type, Integer code, String params) {
        super(message, message_to_purchaser, type, code, params);
    }
}

class ProcessingError extends Error {

    ProcessingError(String message, String message_to_purchaser, String type, Integer code, String params) {
        super(message, message_to_purchaser, type, code, params);
    }
}

class ResourceNotFoundError extends Error {

    ResourceNotFoundError(String message, String message_to_purchaser, String type, Integer code, String params) {
        super(message, message_to_purchaser, type, code, params);
    }
}

class MalformedRequestError extends Error {

    MalformedRequestError(String message, String message_to_purchaser, String type, Integer code, String params) {
        super(message, message_to_purchaser, type, code, params);
    }
}
