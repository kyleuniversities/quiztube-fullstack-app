package com.ku.quizzical.app.controller.like;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.ku.quizzical.app.helper.NullValueHelper;
import com.ku.quizzical.app.util.dto.IntegerDto;
import com.ku.quizzical.common.helper.list.ListHelper;
import com.ku.quizzical.common.helper.number.IdHelper;

/**
 * Service class for Like Database related operations
 */
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
    public LikeDto saveLike(String userId, String quizId, LikeDto likeDto) {
        var sql = """
                INSERT INTO _like(id, quiz_id, user_id)
                VALUES (?, ?, ?)
                """;
        String id = IdHelper.nextMockId();
        int result = this.jdbcTemplate.update(sql, id, likeDto.quizId(), likeDto.userId());
        System.out.println("POST LIKE RESULT = " + result);
        return new LikeDto(id, likeDto.quizId(), likeDto.userId());
    }

    @Override
    public IntegerDto getNumberOfLikesForQuiz(String quizId) {
        var sql = """
                SELECT id, quiz_id, user_id
                FROM _like
                WHERE quiz_id = ?
                """;
        return new IntegerDto(this.jdbcTemplate.query(sql, this.dtoRowMapper, quizId).size());
    }

    @Override
    public LikeDto likeExistsForQuiz(String userId, String quizId) {
        var sql = """
                SELECT id, quiz_id, user_id
                FROM _like
                WHERE quiz_id = ? AND user_id = ?
                """;
        List<LikeDto> likes = this.jdbcTemplate.query(sql, this.dtoRowMapper, quizId, userId);
        if (ListHelper.isEmpty(likes)) {
            return new LikeDto(NullValueHelper.NULL_TEXT, NullValueHelper.NULL_TEXT,
                    NullValueHelper.NULL_TEXT);
        }
        return ListHelper.get(likes, 0);
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
    public LikeDto getLike(String quizId, String id) {
        var sql = """
                SELECT id, quiz_id, user_id
                FROM _like
                WHERE id = ?
                """;
        return ListHelper.getApparentValue(this.jdbcTemplate.query(sql, this.dtoRowMapper, id), 0);
    }

    @Override
    public void deleteLike(String quizId, String id) {
        var sql = """
                DELETE
                FROM _like
                WHERE id = ?
                """;
        int result = this.jdbcTemplate.update(sql, id);
        System.out.println("DELETE LIKE RESULT = " + result);
    }
}
