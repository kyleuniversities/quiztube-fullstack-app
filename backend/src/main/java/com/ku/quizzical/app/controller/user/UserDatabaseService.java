package com.ku.quizzical.app.controller.user;

import java.util.List;
import com.ku.quizzical.app.util.dto.BooleanDto;

public interface UserDatabaseService {
    UserDto saveUser(UserRegistrationRequest user);

    List<UserDto> getAllUsers();

    UserDto getUser(String id);

    UserDto getUserByUsername(String username);

    UserDto getUserByEmail(String email);

    BooleanDto userByIdExists(String username);

    BooleanDto userByUsernameExists(String username);

    void deleteUser(String id);
}
