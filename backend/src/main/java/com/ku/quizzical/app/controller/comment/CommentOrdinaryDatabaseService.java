package com.ku.quizzical.app.controller.comment;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.ku.quizzical.common.helper.ListHelper;

/**
 * Service class for Comment Database related operations
 */
@Service
public class CommentOrdinaryDatabaseService implements CommentDatabaseService {
        // Instance Fields
        private final JdbcTemplate jdbcTemplate;
        private final CommentDtoRowMapper dtoRowMapper;

        // Constructor Method
        public CommentOrdinaryDatabaseService(JdbcTemplate jdbcTemplate,
                        CommentDtoRowMapper dtoRowMapper) {
                super();
                this.jdbcTemplate = jdbcTemplate;
                this.dtoRowMapper = dtoRowMapper;
        }

        // Interface Methods
        @Override
        public CommentDto saveComment(String quizId, CommentDto commentDto) {
                var sql = """
                                INSERT INTO comment(id, text, quiz_id, user_id)
                                VALUES (?, ?, ?, ?)
                                """;
                int result = this.jdbcTemplate.update(sql, commentDto.id(), commentDto.text(),
                                commentDto.quizId(), commentDto.userId());
                System.out.println("POST COMMENT RESULT = " + result);
                return new CommentDto(commentDto.id(), commentDto.text(), commentDto.quizId(),
                                commentDto.userId());
        }

        @Override
        public List<CommentDto> getAllCommentsByQuizId(String quizId) {
                var sql = """
                                SELECT id, text, quiz_id, user_id
                                FROM comment
                                WHERE quiz_id = ?
                                """;
                return this.jdbcTemplate.query(sql, this.dtoRowMapper, quizId);
        }

        @Override
        public CommentDto getComment(String quizId, String id) {
                var sql = """
                                SELECT id, text, quiz_id, user_id
                                FROM comment
                                WHERE id = ?
                                """;
                return ListHelper.getApparentValue(
                                this.jdbcTemplate.query(sql, this.dtoRowMapper, id), 0);
        }

        @Override
        public void deleteComment(String quizId, String id) {
                var sql = """
                                DELETE
                                FROM comment
                                WHERE id = ?
                                """;
                int result = this.jdbcTemplate.update(sql, id);
                System.out.println("DELETE COMMENT RESULT = " + result);
        }
}
