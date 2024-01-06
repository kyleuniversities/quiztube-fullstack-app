package com.ku.quizzical.app.controller.quiz;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.function.BiConsumer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import com.ku.quizzical.app.controller.auth.AuthenticationRequest;
import com.ku.quizzical.app.controller.user.UserDto;
import com.ku.quizzical.app.controller.user.UserRegistrationRequest;
import com.ku.quizzical.app.helper.controller.AuthenticationTestHelper;
import com.ku.quizzical.app.helper.controller.TestHelper;
import com.ku.quizzical.app.helper.controller.UserTestHelper;
import com.ku.quizzical.app.util.TestRestTemplateContainer;

/**
 * Test Class for Quiz Controller
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class QuizControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    // CREATE Method Test
    // Tests the Quiz Post Operation
    @Test
    void quizPostTest() throws Exception {
        this.testWithNewUser((UserDto user, TestRestTemplateContainer container) -> {
            // TODO
        });
    }

    // Method to test operation with a registered user
    private void testWithNewUser(BiConsumer<UserDto, TestRestTemplateContainer> action) {
        TestHelper.testWithNewUser(this.restTemplate, this::toFullUrl, action);
    }

    // Method to return full requets URL
    private String toFullUrl(String url) {
        return TestHelper.toFullUrl(this.port, url);
    }
}
