package com.ku.quizzical.app.helper;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * Helper class for HTTP Operations
 */
public class HttpHelper {
    /**
     * Creates new Http Entity
     */
    public static HttpEntity<String> newGeneralHttpEntity(JSONObject object) {
        return HttpHelper.newHttpEntity(object, HttpHelper.newGeneralHttpHeaders());
    }

    /**
     * Creates new Http Entity
     */
    public static HttpEntity<String> newHttpEntity(JSONObject object, HttpHeaders headers) {
        return new HttpEntity<>(object.toString(), headers);
    }

    /**
     * Creates new Http Headers
     */
    public static HttpHeaders newHttpHeaders() {
        return new HttpHeaders();
    }

    /**
     * Creates new unauthorized Http Headers
     */
    public static HttpHeaders newGeneralHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private HttpHelper() {
        super();
    }
}
