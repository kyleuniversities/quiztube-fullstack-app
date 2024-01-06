package com.ku.quizzical.app.helper.controller;

import java.util.function.BiConsumer;
import org.springframework.boot.test.web.client.TestRestTemplate;
import com.ku.quizzical.app.controller.auth.AuthenticationRequest;
import com.ku.quizzical.app.controller.user.UserDto;
import com.ku.quizzical.app.controller.user.UserRegistrationRequest;
import com.ku.quizzical.app.util.TestRestTemplateContainer;
import com.ku.quizzical.common.util.string.StringFunction;

/**
 * Helper class for Subject Test Operations
 */
public class TestHelper {
    /**
     * Performs a test after registering and logging in with a new user
     */
    public static void testWithNewUser(TestRestTemplate restTemplate, StringFunction toFullUrl,
            BiConsumer<UserDto, TestRestTemplateContainer> action) {
        // Set up test user variablees
        UserRegistrationRequest registrationRequest = UserTestHelper.newRandomRegistrationRequest();

        // Set up template container
        TestRestTemplateContainer container =
                TestRestTemplateContainer.newInstance(restTemplate, toFullUrl);

        // Register user
        UserDto user = UserTestHelper.clearSaveUser(registrationRequest, container);

        // Log In user
        AuthenticationRequest loginRequest = AuthenticationTestHelper
                .newLoginRequest(registrationRequest.username(), registrationRequest.password());
        AuthenticationTestHelper.logIn(loginRequest, container);

        // Run test
        action.accept(user, container);

        // Cleanup
        UserTestHelper.deleteUserIfUsernameExists(user.username(), container);
        AuthenticationTestHelper.resetHeaders(container);
    }

    /**
     * Transforms an api URL to a full request URL
     */
    public static String toFullUrl(int port, String url) {
        return "http://localhost:" + port + url;
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private TestHelper() {
        super();
    }
}
