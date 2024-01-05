package com.ku.quizzical.app.helper.controller;

import java.util.List;
import org.json.JSONObject;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import com.ku.quizzical.app.controller.user.UserDto;
import com.ku.quizzical.app.helper.HttpHelper;
import com.ku.quizzical.app.helper.JsonHelper;
import com.ku.quizzical.app.util.TestRestTemplateContainer;
import com.ku.quizzical.app.util.dto.BooleanDto;
import com.ku.quizzical.common.helper.ConditionalHelper;
import com.ku.quizzical.common.util.string.StringFunction;

/**
 * Helper class for User Test Operations
 */
public class UserTestHelper {
    /**
     * Checks if the username is already taken. Deletes if it is already present, and then saves the
     * user.
     */
    public static UserDto clearSaveUser(String username, String email, String password,
            String picture, String thumbnail, TestRestTemplateContainer container) {
        UserTestHelper.deleteUserIfUsernameExists(username, container);
        return UserTestHelper.saveUser(username, email, password, picture, thumbnail, container);
    }

    /**
     * Deletes user by id
     */
    public static void deleteUserById(String id, TestRestTemplateContainer container) {
        container.delete("/users" + id);
    }

    /**
     * Deletes user by id
     */
    public static void deleteUserIfUsernameExists(String username,
            TestRestTemplateContainer container) {
        BooleanDto usernameExists = UserTestHelper.userByUsernameExists(username, container);
        ConditionalHelper.ifThen(usernameExists.value(), () -> {
            UserDto existingUser = UserTestHelper.getByUsername(username, container);
            UserTestHelper.deleteUserById(existingUser.id(), container);
        });
    }

    /**
     * Gets a list of all Users
     */
    public static List<UserDto> getAllUsers(TestRestTemplateContainer container) {
        return container.getList("/users");
    }

    /**
     * Gets a User by id
     */
    public static UserDto getById(String id, TestRestTemplateContainer container) {
        return container.getObject("/users/" + id, UserDto.class);
    }

    /**
     * Gets a User by username
     */
    public static UserDto getByUsername(String username, TestRestTemplateContainer container) {
        return container.getObject("/users/username/" + username, UserDto.class);
    }

    /**
     * Saves user
     */
    public static UserDto saveUser(String username, String email, String password, String picture,
            String thumbnail, TestRestTemplateContainer container) {
        JSONObject object = JsonHelper.newJsonObject();
        JsonHelper.put(object, "username", username);
        JsonHelper.put(object, "email", email);
        JsonHelper.put(object, "password", password);
        JsonHelper.put(object, "picture", password);
        JsonHelper.put(object, "thumbnail", password);
        HttpEntity<String> request = HttpHelper.newGeneralHttpEntity(object);
        return container.post("/users", request, UserDto.class);
    }

    /**
     * Checks if a user with a username exists
     */
    public static BooleanDto userByUsernameExists(String username,
            TestRestTemplateContainer container) {
        return container.getObject("/users/username-exists/" + username, BooleanDto.class);
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private UserTestHelper() {
        super();
    }
}
