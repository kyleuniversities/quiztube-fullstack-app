package com.ku.quizzical.app.controller.integration;

import java.util.List;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import com.ku.quizzical.app.controller.user.User;
import com.ku.quizzical.app.helper.controller.TestHelper;
import com.ku.quizzical.common.helper.FunctionHelper;

/**
 * Test Class for integration Controller
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class IntegrationControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Creates a List of Realized Users and deletes them
     */
    @Test
    void integrationRealizedUsersTest() throws Exception {
        this.testWithNewRealizedUsers((List<User> users) -> FunctionHelper.doNothing());
    }

    // Method to test operation with new realized users
    private void testWithNewRealizedUsers(Consumer<List<User>> action) {
        TestHelper.testWithNewRealizedUsers(this.restTemplate, this::toFullUrl, action);
    }

    // Method to return full requets URL
    private String toFullUrl(String url) {
        return TestHelper.toFullUrl(this.port, url);
    }
}
