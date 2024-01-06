package com.ku.quizzical.app.helper.controller;

import org.json.JSONObject;
import com.ku.quizzical.app.controller.auth.AuthenticationRequest;
import com.ku.quizzical.app.controller.auth.AuthenticationResponse;
import com.ku.quizzical.app.controller.user.UserRegistrationRequest;
import com.ku.quizzical.app.helper.JsonHelper;
import com.ku.quizzical.app.util.TestRestTemplateContainer;
import com.ku.quizzical.common.helper.RandomHelper;
import com.ku.quizzical.common.helper.number.IndexHelper;

/**
 * Helper class for Authentication Test Operations
 */
public class AuthenticationTestHelper {
    /**
     * Rest Template Container receives Login Token
     */
    public static void logIn(AuthenticationRequest request, TestRestTemplateContainer container) {
        JSONObject object = JsonHelper.newJsonObject();
        JsonHelper.put(object, "username", request.username());
        JsonHelper.put(object, "password", request.password());
        String token = container.post("/auth/login", object, AuthenticationResponse.class).token();
        AuthenticationTestHelper.setBearerToken(container, token);
    }

    /**
     * Rest Template Container removes Login Token
     */
    public static void logOut(TestRestTemplateContainer container) {
        AuthenticationTestHelper.resetHeaders(container);
    }

    /**
     * Creates a random registration request
     */
    public static AuthenticationRequest newLoginRequest(String username, String password) {
        return new AuthenticationRequest(username, password);
    }

    /**
     * Resets the headers Test Rest Template Container
     */
    public static void resetHeaders(TestRestTemplateContainer container) {
        container.resetHeaders();
    }

    /**
     * Sets the bearer token of a Test Rest Template Container
     */
    public static void setBearerToken(TestRestTemplateContainer container, String token) {
        container.setBearerToken(token);
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private AuthenticationTestHelper() {
        super();
    }
}
