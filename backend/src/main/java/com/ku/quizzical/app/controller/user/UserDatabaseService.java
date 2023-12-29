package com.ku.quizzical.app.controller.user;

import java.util.List;

public interface UserDatabaseService {
    void saveUser(UserRegistrationRequest user);

    List<UserDto> getAllUsers();

    UserDto getUser(String id);

    UserDto getUserByUsername(String username);

    void updateUser(String id, UserUpdateRequest user);

    void deleteUser(String id);
}
