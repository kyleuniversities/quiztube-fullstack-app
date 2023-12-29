package com.ku.quizzical.app.controller.user;

import java.util.List;
import java.util.function.Function;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.ku.quizzical.common.helper.ConditionalHelper;
import com.ku.quizzical.common.helper.ListHelper;

@Service
public class UserOrdinaryDatabaseService implements UserDatabaseService {
    // Instance Fields
    private final JdbcTemplate jdbcTemplate;
    private final UserDtoRowMapper dtoRowMapper;

    // Constructor Method
    public UserOrdinaryDatabaseService(JdbcTemplate jdbcTemplate, UserDtoRowMapper dtoRowMapper) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        this.dtoRowMapper = dtoRowMapper;
    }

    // Interface Methods
    @Override
    public void saveUser(UserRegistrationRequest registrationRequest) {
        var sql = """
                INSERT INTO user(id, username, email, password, profile_picture, thumbnail)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        int result = this.jdbcTemplate.update(sql, registrationRequest.id(),
                registrationRequest.username(), registrationRequest.email(),
                registrationRequest.password(), registrationRequest.profilePicture(),
                registrationRequest.thumbnail());
        System.out.println("POST USER RESULT = " + result);
    }

    @Override
    public List<UserDto> getAllUsers() {
        var sql = """
                SELECT id, username, email, password, profile_picture, thumbnail
                FROM user
                LIMIT 100
                """;
        return this.jdbcTemplate.query(sql, this.dtoRowMapper);
    }

    @Override
    public UserDto getUser(String id) {
        var sql = """
                SELECT id, username, email, password, profile_picture, thumbnail
                FROM user
                WHERE id = ?
                """;
        return ListHelper.getApparentValue(this.jdbcTemplate.query(sql, this.dtoRowMapper, id), 0);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        var sql = """
                SELECT id, username, email, password, profile_picture, thumbnail
                FROM user
                WHERE username = ?
                """;
        return ListHelper
                .getApparentValue(this.jdbcTemplate.query(sql, this.dtoRowMapper, username), 0);
    }

    @Override
    public void updateUser(String id, UserUpdateRequest update) {
        this.updateUserAttribute(update, "username", UserUpdateRequest::username);
        this.updateUserAttribute(update, "email", UserUpdateRequest::email);
        this.updateUserAttribute(update, "password", UserUpdateRequest::password);
        this.updateUserAttribute(update, "profile_picture", UserUpdateRequest::profilePicture);
        this.updateUserAttribute(update, "thumbnail", UserUpdateRequest::thumbnail);
    }

    @Override
    public void deleteUser(String id) {
        var sql = """
                DELETE
                FROM user
                WHERE id = ?
                """;
        int result = this.jdbcTemplate.update(sql, id);
        System.out.println("DELETE USER RESULT = " + result);
    }

    private void updateUserAttribute(UserUpdateRequest update, String attributeName,
            Function<UserUpdateRequest, String> attributeCollector) {
        String attribute = attributeCollector.apply(update);
        ConditionalHelper.ifThen(attribute != null, () -> {
            String sql = String.format("UPDATE user SET %s = ? WHERE id = ?", attributeName);
            int result =
                    this.jdbcTemplate.update(sql, attributeCollector.apply(update), update.id());
            System.out.println("UPDATE USER " + attributeName + " RESULT = " + result);
        });
    }
}
