package com.ku.quizzical.app.helper.controller;

import java.util.List;
import org.json.JSONObject;
import com.ku.quizzical.app.controller.auth.AuthenticationRequest;
import com.ku.quizzical.app.controller.auth.AuthenticationResponse;
import com.ku.quizzical.app.controller.user.UserDto;
import com.ku.quizzical.app.controller.user.UserRegistrationRequest;
import com.ku.quizzical.app.helper.JsonHelper;
import com.ku.quizzical.app.util.TestRestTemplateContainer;
import com.ku.quizzical.app.util.dto.BooleanDto;
import com.ku.quizzical.common.helper.ConditionalHelper;
import com.ku.quizzical.common.helper.RandomHelper;
import com.ku.quizzical.common.helper.number.IndexHelper;

/**
 * Helper class for User Test Operations
 */
public class UserTestHelper {
    /**
     * Checks if the username is already taken. Deletes if it is already present, and then saves the
     * user.
     */
    public static UserDto clearSaveUser(UserRegistrationRequest request,
            TestRestTemplateContainer container) {
        UserTestHelper.deleteUserIfUsernameExists(request.username(), container);
        return UserTestHelper.saveUser(request, container);
    }

    /**
     * Deletes user by id
     */
    public static void deleteUserById(String id, TestRestTemplateContainer container) {
        container.delete("/users/" + id);
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
     * Receives Log In token
     */
    public static String logIn(AuthenticationRequest request, TestRestTemplateContainer container) {
        JSONObject object = JsonHelper.newJsonObject();
        JsonHelper.put(object, "username", request.username());
        JsonHelper.put(object, "password", request.password());
        return container.post("/auth/login", object, AuthenticationResponse.class).token();
    }

    /**
     * Creates a random registration request
     */
    public static UserRegistrationRequest newRandomRegistrationRequest() {
        String tag = "test" + IndexHelper.toIndexText(RandomHelper.nextInt(1000000), 6);
        String username = tag;
        String email = tag + "@gamil.com";
        String password = tag + "!";
        String picture = "static/user/user-picture-t.png";
        String thumbnail = "static/user/user-picture-t_T.png";
        return new UserRegistrationRequest(tag, username, email, password, picture, thumbnail);
    }

    /**
     * Saves user
     */
    public static UserDto saveUser(UserRegistrationRequest request,
            TestRestTemplateContainer container) {
        JSONObject object = JsonHelper.newJsonObject();
        JsonHelper.put(object, "username", request.username());
        JsonHelper.put(object, "email", request.email());
        JsonHelper.put(object, "password", request.password());
        JsonHelper.put(object, "picture", request.picture());
        JsonHelper.put(object, "thumbnail", request.thumbnail());
        return container.post("/users", object, UserDto.class);
    }

    /**
     * Checks if a user with a username exists
     */
    public static BooleanDto userByIdExists(String id, TestRestTemplateContainer container) {
        return container.getObject("/users/exists/" + id, BooleanDto.class);
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
