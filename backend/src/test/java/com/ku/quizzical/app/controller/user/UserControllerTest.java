package com.ku.quizzical.app.controller.user;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void usersGetTest() throws Exception {
        this.restTemplate.getForObject(this.fullUrl("/users"), List.class);
    }

    @Test
    void userPostTest() throws Exception {
        List<UserDto> users = this.restTemplate.getForObject(this.fullUrl("/users"), List.class);
        System.out.println("---------------");
        System.out.println("<<USERS>>");
        System.out.println(users);
        System.out.println("---------------");
        int initialSize = users.size();
        JSONObject body = new JSONObject();
        body.put("username", "test123");
        body.put("email", "test123@gmail.com");
        body.put("password", "test123@gmail.com");
        body.put("picture", "static/user/user-picture-t.png");
        body.put("thumbnail", "static/user/user-picture-t_T.png");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(body.toString(), headers);
        UserDto userDto = this.restTemplate
                .postForEntity(this.fullUrl("/users"), request, UserDto.class).getBody();
        List<UserDto> users2 = this.restTemplate.getForObject(this.fullUrl("/users"), List.class);
        assertThat(users.size() + 1).isEqualTo(users2.size());
    }

    private String fullUrl(String url) {
        return "http://localhost:" + port + url;
    }
}
