package com.ku.quizzical.app.controller.user;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ku.quizzical.common.helper.ConditionalHelper;

@Repository("jdbc")
public class UserJdbcDataAccessService implements UserDataAccessService {
    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;

    public UserJdbcDataAccessService(JdbcTemplate jdbcTemplate, UserRowMapper userRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRowMapper = userRowMapper;
    }

    @Override
    public void postUser(User user) {
        var sql = """
                INSERT INTO user(username, email, password)
                VALUES (?, ?, ?)
                """;
        int result = this.jdbcTemplate.update(sql,
                user.getUsername(),
                user.getEmail(),
                user.getPassword());
        System.out.println("POST USER RESULT = " + result);
    }

    @Override
    public List<User> getAllUsers() {
        var sql = """
                SELECT id, username, email, password
                FROM user
                LIMIT 100
                """;
        return this.jdbcTemplate.query(sql, userRowMapper);
    }

    @Override
    public Optional<User> getUser(String id) {
        var sql = """
                SELECT id, username, email, password
                FROM user
                WHERE id = ?
                """;
        return this.jdbcTemplate.query(sql, userRowMapper, id)
                .stream().findFirst();
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        var sql = """
                SELECT id, username, email, password
                FROM user
                WHERE username = ?
                """;
        return this.jdbcTemplate.query(sql, userRowMapper, username)
                .stream().findFirst();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        var sql = """
                SELECT id, username, email, password
                FROM user
                WHERE email = ?
                """;
        return this.jdbcTemplate.query(sql, userRowMapper, email)
                .stream().findFirst();
    }

    @Override
    public void updateUser(User update) {
        this.updateUserAttribute(update, "username", User::getUsername);
        this.updateUserAttribute(update, "email", User::getEmail);
        this.updateUserAttribute(update, "password", User::getPassword);
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

    @Override
    public boolean userExistsWithId(String id) {
        var sql = """
                SELECT count(id)
                FROM user
                WHERE id = ?
                """;
        Integer count = this.jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public boolean userExistsWithUsername(String username) {
        var sql = """
                SELECT count(id)
                FROM user
                WHERE username = ?
                """;
        Integer count = this.jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count != null && count > 0;
    }

    @Override
    public boolean userExistsWithEmail(String email) {
        var sql = """
                SELECT count(id)
                FROM user
                WHERE email = ?
                """;
        Integer count = this.jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    private void updateUserAttribute(User update, String attributeName, Function<User, String> attributeCollector) {
        String attribute = attributeCollector.apply(update);
        ConditionalHelper.ifThen(attribute != null, () -> {
            String sql = String.format("UPDATE user SET %s = ? WHERE id = ?", attributeName);
            int result = this.jdbcTemplate.update(sql, attributeCollector.apply(update), update.getId());
            System.out.println("UPDATE USER " + attributeName + " RESULT = " + result);
        });
    }
}
