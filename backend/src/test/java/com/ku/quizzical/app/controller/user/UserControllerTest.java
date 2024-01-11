package com.ku.quizzical.app.controller.user;

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
import com.ku.quizzical.app.helper.controller.AuthenticationTestHelper;
import com.ku.quizzical.app.helper.controller.TestHelper;
import com.ku.quizzical.app.helper.controller.UserTestHelper;
import com.ku.quizzical.app.util.TestRestTemplateContainer;
import com.ku.quizzical.common.util.wrapper.OrdinaryWrapper;

/**
 * Test Class for User Controller
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    // CREATE Method Test
    // Tests the User Post Operation
    @Test
    void userPostTest() throws Exception {
        // Set up test user variablees
        UserRegistrationRequest registrationRequest = UserTestHelper.newRandomRegistrationRequest();
        String username = registrationRequest.username();

        // Set up template container
        TestRestTemplateContainer container =
                TestRestTemplateContainer.newInstance(this.restTemplate, this::toFullUrl);

        // Set up user clearance
        UserTestHelper.deleteUserIfUsernameExists(username, container);

        // Test POST method
        List<UserDto> users1 = UserTestHelper.getAllUsers(container);
        boolean exists1 = UserTestHelper.userByUsernameExists(username, container).value();
        UserDto user = UserTestHelper.saveUser(registrationRequest, container);
        List<UserDto> users2 = UserTestHelper.getAllUsers(container);
        boolean exists2 = UserTestHelper.userByUsernameExists(username, container).value();
        assertThat(exists1).isFalse();
        assertThat(exists2).isTrue();
        assertThat(users1.size() + 1).isEqualTo(users2.size());

        // Cleanup
        AuthenticationRequest loginRequest = AuthenticationTestHelper
                .newLoginRequest(registrationRequest.username(), registrationRequest.password());
        AuthenticationTestHelper.logIn(loginRequest, container);
        UserTestHelper.deleteUserById(user.id(), container);
        AuthenticationTestHelper.logOut(container);
    }

    // READ Method Test
    // Tests the Get Users Operation
    @Test
    void usersGetTest() throws Exception {
        // Set up template container
        TestRestTemplateContainer container =
                TestRestTemplateContainer.newInstance(this.restTemplate, this::toFullUrl);

        // Test GET
        List<UserDto> users = UserTestHelper.getAllUsers(container);
        assertThat(users.size()).isGreaterThan(-1);
    }

    // READ Method Test
    // Tests the Get User by Id Operation
    @Test
    void userGetByIdTest() throws Exception {
        this.testWithNewUser((UserDto user, TestRestTemplateContainer container) -> {
            UserDto matchingUser = UserTestHelper.getById(user.id(), container);
            assertThat(user.id()).isEqualTo(matchingUser.id());
        });
    }

    // READ Method Test
    // Tests the Get User by Username Operation
    @Test
    void userGetByUsernameTest() throws Exception {
        this.testWithNewUser((UserDto user, TestRestTemplateContainer container) -> {
            UserDto matchingUser = UserTestHelper.getByUsername(user.username(), container);
            assertThat(user.id()).isEqualTo(matchingUser.id());
        });
    }

    // READ Method Test
    // Tests the User Exists by Id Operation
    @Test
    void userUsernameExistsByIdTest() throws Exception {
        OrdinaryWrapper<UserDto> userWrapper = OrdinaryWrapper.newInstance(null);
        OrdinaryWrapper<TestRestTemplateContainer> containerWrapper =
                OrdinaryWrapper.newInstance(null);
        this.testWithNewUser((UserDto user, TestRestTemplateContainer container) -> {
            assertThat(UserTestHelper.userByIdExists(user.id(), container).value()).isEqualTo(true);
            userWrapper.setValue(user);
            containerWrapper.setValue(container);
        });
        UserDto user = userWrapper.getValue();
        TestRestTemplateContainer container = containerWrapper.getValue();
        assertThat(UserTestHelper.userByIdExists(user.id(), container).value()).isEqualTo(false);
    }

    // READ Method Test
    // Tests the User Exists by Username Operation
    @Test
    void userUsernameExistsByUsernameTest() throws Exception {
        OrdinaryWrapper<UserDto> userWrapper = OrdinaryWrapper.newInstance(null);
        OrdinaryWrapper<TestRestTemplateContainer> containerWrapper =
                OrdinaryWrapper.newInstance(null);
        this.testWithNewUser((UserDto user, TestRestTemplateContainer container) -> {
            assertThat(UserTestHelper.userByUsernameExists(user.username(), container).value())
                    .isEqualTo(true);
            userWrapper.setValue(user);
            containerWrapper.setValue(container);
        });
        UserDto user = userWrapper.getValue();
        TestRestTemplateContainer container = containerWrapper.getValue();
        assertThat(UserTestHelper.userByIdExists(user.username(), container).value())
                .isEqualTo(false);
    }

    // DELETE Method Test
    // Tests the Delete User by Id Operation
    @Test
    void userDeleteByIdTest() throws Exception {
        this.testWithNewUser((UserDto user, TestRestTemplateContainer container) -> {
            boolean exists1 = UserTestHelper.userByIdExists(user.id(), container).value();
            UserTestHelper.deleteUserById(user.id(), container);
            AuthenticationTestHelper.resetHeaders(container);
            boolean exists2 = UserTestHelper.userByIdExists(user.id(), container).value();
            assertThat(exists1).isTrue();
            assertThat(exists2).isFalse();
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
