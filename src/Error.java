/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author mauricio
 */
public class Error extends Exception {

    String message;
    String type;
    Integer code;
    String params;

    public Error(String message, String type, Integer code, String params) {
        super(message);
        this.message = message;
        this.type = type;
        this.code = code;
        this.params = params;
    }

    static void errorHandler(JSONObject response, Integer responseCode) throws JSONException, Error {
        String message = null;
        String type = null;
        String params = null;
        if (response.has("message")) {
            message = response.getString("message");
        }
        if (response.has("type")) {
            type = response.getString("type");
        }
        if (response.has("param")) {
            params = response.getString("param");
        }
        switch (responseCode) {
            case 400:
                throw new MalformedRequestError(message, type, responseCode, params);
            case 401:
                throw new AuthenticationError(message, type, responseCode, params);
            case 402:
                throw new ProcessingError(message, type, responseCode, params);
            case 404:
                throw new ResourceNotFoundError(message, type, responseCode, params);
            case 422:
                throw new ParameterValidationError(message, type, responseCode, params);
            case 500:
                throw new ApiError(message, type, responseCode, params);
            default:
                throw new Error(message, type, responseCode, params);
        }
    }
}

class ApiError extends Error {

    ApiError(String message, String type, Integer code, String params) {
        super(message, type, code, params);
    }
}

class NoConnectionError extends Error {

    NoConnectionError(String message, String type, Integer code, String params) {
        super(message, type, code, params);
    }
}

class AuthenticationError extends Error {

    AuthenticationError(String message, String type, Integer code, String params) {
        super(message, type, code, params);
    }
}

class ParameterValidationError extends Error {

    ParameterValidationError(String message, String type, Integer code, String params) {
        super(message, type, code, params);
    }
}

class ProcessingError extends Error {

    ProcessingError(String message, String type, Integer code, String params) {
        super(message, type, code, params);
    }
}

class ResourceNotFoundError extends Error {

    ResourceNotFoundError(String message, String type, Integer code, String params) {
        super(message, type, code, params);
    }
}

class MalformedRequestError extends Error {

    MalformedRequestError(String message, String type, Integer code, String params) {
        super(message, type, code, params);
    }
}
