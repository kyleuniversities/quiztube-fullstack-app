package com.ku.quizzical.app.controller.user;

import java.util.List;
import java.util.Optional;

public interface UserDataAccessService {
    void postUser(User user);

    List<User> getAllUsers();

    Optional<User> getUser(String id);

    Optional<User> getUserByUsername(String username);

    Optional<User> getUserByEmail(String email);

    void updateUser(User user);

    void deleteUser(String id);

    boolean userExistsWithId(String id);

    boolean userExistsWithUsername(String username);

    boolean userExistsWithEmail(String email);
}
