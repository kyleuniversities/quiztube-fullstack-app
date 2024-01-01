package com.ku.quizzical.app.controller.user;

import java.util.List;
import java.util.function.Function;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.ku.quizzical.app.helper.DatabaseValidationHelper;
import com.ku.quizzical.app.helper.TextValidationHelper;
import com.ku.quizzical.app.helper.UserHelper;
import com.ku.quizzical.common.helper.ConditionalHelper;
import com.ku.quizzical.common.helper.ListHelper;
import com.ku.quizzical.common.helper.number.IdHelper;

@Service
public class UserOrdinaryDatabaseService implements UserDatabaseService {
    // Instance Fields
    private final JdbcTemplate jdbcTemplate;
    private UserRepository repository;
    private final UserDtoRowMapper dtoRowMapper;

    // Constructor Method
    public UserOrdinaryDatabaseService(JdbcTemplate jdbcTemplate, UserRepository repository,
            UserDtoRowMapper dtoRowMapper) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        this.repository = repository;
        this.dtoRowMapper = dtoRowMapper;
    }

    // Interface Methods
    @Override
    public UserDto saveUser(UserRegistrationRequest registrationRequest) {
        this.validateRegistrationRequest(registrationRequest);
        var sql = """
                INSERT INTO user(id, username, email, password, picture, thumbnail)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        String id = IdHelper.nextMockId();
        String encryptedPassword =
                new BCryptPasswordEncoder().encode(registrationRequest.password());
        int result = this.jdbcTemplate.update(sql, id, registrationRequest.username(),
                registrationRequest.email(), encryptedPassword, registrationRequest.picture(),
                registrationRequest.thumbnail());
        System.out.println("POST USER RESULT = " + result);
        return new UserDto(id, registrationRequest.username(), registrationRequest.email(),
                registrationRequest.picture(), registrationRequest.thumbnail(),
                UserHelper.makeDefaultRoleList());
    }

    @Override
    public List<UserDto> getAllUsers() {
        var sql = """
                SELECT id, username, email, password, picture, thumbnail
                FROM user
                LIMIT 100
                """;
        return this.jdbcTemplate.query(sql, this.dtoRowMapper);
    }

    @Override
    public UserDto getUser(String id) {
        var sql = """
                SELECT id, username, email, password, picture, thumbnail
                FROM user
                WHERE id = ?
                """;
        return ListHelper.getApparentValue(this.jdbcTemplate.query(sql, this.dtoRowMapper, id), 0);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        var sql = """
                SELECT id, username, email, password, picture, thumbnail
                FROM user
                WHERE username = ?
                """;
        return ListHelper
                .getApparentValue(this.jdbcTemplate.query(sql, this.dtoRowMapper, username), 0);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        var sql = """
                SELECT id, username, email, password, picture, thumbnail
                FROM user
                WHERE email = ?
                """;
        return ListHelper.getApparentValue(this.jdbcTemplate.query(sql, this.dtoRowMapper, email),
                0);
    }

    @Override
    public UserDto updateUser(String id, UserUpdateRequest update) {
        this.updateUserAttribute(update, "username", UserUpdateRequest::username);
        this.updateUserAttribute(update, "email", UserUpdateRequest::email);
        this.updateUserAttribute(update, "password", UserUpdateRequest::password);
        this.updateUserAttribute(update, "picture", UserUpdateRequest::picture);
        this.updateUserAttribute(update, "thumbnail", UserUpdateRequest::thumbnail);
        TextValidationHelper.validateIfExists(update::username, this::validateUsername);
        TextValidationHelper.validateIfExists(update::email, this::validateEmail);
        TextValidationHelper.validateIfExists(update::password, this::validatePassword);
        DatabaseValidationHelper.validateUniqueTextResourceIfExists("Username", update::username,
                this::getUserByUsername);
        DatabaseValidationHelper.validateUniqueTextResourceIfExists("Email", update::email,
                this::getUserByEmail);
        return this.getUser(id);
    }

    @Override
    public void deleteUser(String id) {
        this.repository.deleteById(id);
        System.out.println("DELETE USER RESULT = " + 1);
    }

    // Interface Minor Methods
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

    // Validation Major Methods
    private void validateRegistrationRequest(UserRegistrationRequest registrationRequest) {
        validateUsername(registrationRequest.username());
        validateEmail(registrationRequest.email());
        validatePassword(registrationRequest.password());
    }

    // Validation Minor Methods
    private void validateUsername(String text) {
        final String TAG = "Username";
        TextValidationHelper.validateNonNull(TAG, text);
        TextValidationHelper.validateLength(TAG, text, 3, 30);
        TextValidationHelper.validateToBeAlphanumeric(TAG, text);
        TextValidationHelper.validateFirstCharacterToBeAlphabetical(TAG, text);
        DatabaseValidationHelper.validateUniqueTextResource(TAG, text, this::getUserByUsername);
    }

    private void validateEmail(String text) {
        TextValidationHelper.validateNonNull("Email", text);
        TextValidationHelper.validateEmail(text);
        DatabaseValidationHelper.validateUniqueTextResource("Email", text, this::getUserByEmail);
    }

    private void validatePassword(String text) {
        TextValidationHelper.validateNonNull("Password", text);
        TextValidationHelper.validateLength("Password", text, 3, 30);
    }
}
