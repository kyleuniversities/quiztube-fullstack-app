package com.ku.quizzical.app.controller.user;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import com.ku.quizzical.app.helper.controller.UserTestHelper;
import com.ku.quizzical.app.util.TestRestTemplateContainer;
import com.ku.quizzical.common.helper.RandomHelper;
import com.ku.quizzical.common.helper.number.IndexHelper;

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

    /**
     * CREATE Method Test Tests the User Post Operation
     */
    @Test
    void userPostTest() throws Exception {
        // Set up variables
        String tag = "test" + IndexHelper.toIndexText(RandomHelper.nextInt(1000000), 6);
        String username = tag;
        String email = tag + "@gamil.com";
        String password = tag + "!";
        String picture = "static/user/user-picture-t.png";
        String thumbnail = "static/user/user-picture-t_T.png";

        // Set up template container
        TestRestTemplateContainer container =
                TestRestTemplateContainer.newInstance(this.restTemplate, this::toFullUrl);

        // Set up user clearance
        UserTestHelper.deleteUserIfUsernameExists(username, container);

        // Test POST method
        List<UserDto> users1 = UserTestHelper.getAllUsers(container);
        boolean exists1 = UserTestHelper.userByUsernameExists(username, container).value();
        UserDto user =
                UserTestHelper.saveUser(username, email, password, picture, thumbnail, container);
        List<UserDto> users2 = UserTestHelper.getAllUsers(container);
        boolean exists2 = UserTestHelper.userByUsernameExists(username, container).value();
        assertThat(exists1).isFalse();
        assertThat(exists2).isTrue();
        assertThat(users1.size() + 1).isEqualTo(users2.size());

        // Cleanup
        UserTestHelper.deleteUserById(user.id(), container);
    }

    private String toFullUrl(String url) {
        return "http://localhost:" + port + url;
    }
}
