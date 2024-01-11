package com.ku.quizzical.app.helper.controller;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.springframework.boot.test.web.client.TestRestTemplate;
import com.ku.quizzical.app.controller.auth.AuthenticationRequest;
import com.ku.quizzical.app.controller.quiz.QuizAddRequest;
import com.ku.quizzical.app.controller.quiz.QuizDto;
import com.ku.quizzical.app.controller.user.User;
import com.ku.quizzical.app.controller.user.UserDto;
import com.ku.quizzical.app.controller.user.UserRegistrationRequest;
import com.ku.quizzical.app.util.TestRestTemplateContainer;
import com.ku.quizzical.common.util.function.TriConsumer;
import com.ku.quizzical.common.util.string.StringFunction;

/**
 * Helper class for Subject Test Operations
 */
public class TestHelper {
    /**
     * Performs a test after registering and logging in with a new user and creating a new quiz
     */
    public static void testWithNewQuiz(TestRestTemplate restTemplate, StringFunction toFullUrl,
            TriConsumer<QuizDto, UserDto, TestRestTemplateContainer> action) {
        TestHelper.testWithNewUser(restTemplate, toFullUrl,
                (UserDto user, TestRestTemplateContainer container) -> {
                    // Create new quiz
                    QuizAddRequest request =
                            QuizTestHelper.newRandomQuizAddRequest(user.id(), container);
                    QuizDto quiz = QuizTestHelper.saveQuiz(request, container);

                    // Perform action
                    action.accept(quiz, user, container);

                    // Cleanup
                    QuizTestHelper.deleteQuizById(quiz.id(), container);
                });
    }

    /**
     * Performs a test after creating a list of realized users
     */
    public static void testWithNewRealizedUsers(TestRestTemplate restTemplate,
            StringFunction toFullUrl, Consumer<List<User>> action) {
        TestRestTemplateContainer container =
                TestRestTemplateContainer.newInstance(restTemplate, toFullUrl);
        IntegrationTestHelper.testWithRealizedUsers(container, action);
    }

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
