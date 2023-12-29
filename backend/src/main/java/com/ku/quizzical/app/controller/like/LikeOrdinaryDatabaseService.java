package com.ku.quizzical.app.controller.like;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.ku.quizzical.common.helper.ListHelper;

@Service
public class LikeOrdinaryDatabaseService implements LikeDatabaseService {
    // Instance Fields
    private final JdbcTemplate jdbcTemplate;
    private final LikeDtoRowMapper dtoRowMapper;

    // Constructor Method
    public LikeOrdinaryDatabaseService(JdbcTemplate jdbcTemplate, LikeDtoRowMapper dtoRowMapper) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        this.dtoRowMapper = dtoRowMapper;
    }

    // Interface Methods
    @Override
    public void saveLike(String userId, String quizId, LikeDto likeDto) {
        var sql = """
                INSERT INTO _like(id, quiz_id, user_id)
                VALUES (?, ?, ?)
                """;
        int result =
                this.jdbcTemplate.update(sql, likeDto.id(), likeDto.quizId(), likeDto.userId());
        System.out.println("POST LIKE RESULT = " + result);
    }

    @Override
    public int getNumberOfLikesForQuiz(String quizId) {
        var sql = """
                SELECT id, quiz_id, user_id
                FROM _like
                WHERE quiz_id = ?
                """;
        return this.jdbcTemplate.query(sql, this.dtoRowMapper, quizId).size();
    }

    @Override
    public boolean likeExistsForQuiz(String userId, String quizId) {
        var sql = """
                SELECT id, quiz_id, user_id
                FROM _like
                WHERE quiz_id = ? AND user_id = ?
                """;
        return this.jdbcTemplate.query(sql, this.dtoRowMapper, quizId, userId).size() > 0;
    }

    @Override
    public List<LikeDto> getAllLikesByUserId(String userId) {
        var sql = """
                SELECT id, quiz_id, user_id
                FROM _like
                WHERE user_id = ?
                """;
        return this.jdbcTemplate.query(sql, this.dtoRowMapper, userId);
    }

    @Override
    public LikeDto getLike(String userId, String quizId, String id) {
        var sql = """
                SELECT id, quiz_id, user_id
                FROM _like
                WHERE id = ?
                """;
        return ListHelper.getApparentValue(this.jdbcTemplate.query(sql, this.dtoRowMapper, id), 0);
    }

    @Override
    public void deleteLike(String userId, String quizId, String id) {
        var sql = """
                DELETE
                FROM _like
                WHERE id = ?
                """;
        int result = this.jdbcTemplate.update(sql, id);
        System.out.println("DELETE LIKE RESULT = " + result);
    }
}
