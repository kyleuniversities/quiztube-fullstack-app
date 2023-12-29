package com.ku.quizzical.app.controller.user;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void saveUser(UserDto user);

    List<UserDto> getAllUsers();

    Optional<UserDto> getUser(String id);

    Optional<UserDto> getUserByUsername(String username);

    void updateUser(String id, UserDto user);

    void deleteUser(String id);
}
