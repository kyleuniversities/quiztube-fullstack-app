package com.ku.quizzical.app.controller.user;

import java.util.List;

public interface UserDatabaseService {
    UserDto saveUser(UserRegistrationRequest user);

    List<UserDto> getAllUsers();

    UserDto getUser(String id);

    UserDto getUserByUsername(String username);

    UserDto getUserByEmail(String email);

    UserDto updateUser(String id, UserUpdateRequest user);

    void deleteUser(String id);
}
