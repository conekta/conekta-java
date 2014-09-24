package com.conekta;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * @author mauricio
 */
public class Error extends Exception {

    public String message;
    public String type;
    public Integer code;
    public String params;

    public Error(String message, String type, Integer code, String params) {
        super(message);
        this.message = message;
        this.type = type;
        this.code = code;
        this.params = params;
    }

    @Override
    public String toString() {
        return message;
    }

    static void errorHandler(JSONObject response, Integer responseCode) throws Error {
        String message = null;
        String type = null;
        String params = null;
        if (response.has("message")) {
            try {
                message = response.getString("message");
            } catch (JSONException ex) {
            }
        }
        if (response.has("type")) {
            try {
                type = response.getString("type");
            } catch (JSONException ex) {
            }
        }
        if (response.has("param")) {
            try {
                params = response.getString("param");
            } catch (JSONException ex) {
            }
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
