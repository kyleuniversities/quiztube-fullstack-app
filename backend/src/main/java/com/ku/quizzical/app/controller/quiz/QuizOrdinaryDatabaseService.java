package com.ku.quizzical.app.controller.quiz;

import java.util.List;
import java.util.function.Function;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.ku.quizzical.common.helper.ConditionalHelper;
import com.ku.quizzical.common.helper.ListHelper;

@Service
public class QuizOrdinaryDatabaseService implements QuizDatabaseService {
    // Instance Fields
    private final JdbcTemplate jdbcTemplate;
    private final QuizDtoRowMapper dtoRowMapper;

    // Constructor Method
    public QuizOrdinaryDatabaseService(JdbcTemplate jdbcTemplate, QuizDtoRowMapper dtoRowMapper) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        this.dtoRowMapper = dtoRowMapper;
    }

    // Interface Methods
    @Override
    public void saveQuiz(String userId, QuizDto quizDto) {
        var sql = """
                INSERT INTO quiz(id, title, description, picture, thumbnail, user_id, subject_id)
                VALUES (?, ?, ?, ?, ?)
                """;
        int result = this.jdbcTemplate.update(sql, quizDto.id(), quizDto.title(),
                quizDto.description(), quizDto.picture(), quizDto.thumbnail());
        System.out.println("POST QUIZ RESULT = " + result);
    }

    @Override
    public List<QuizDto> getAllQuizzes() {
        var sql = """
                SELECT id, title, description, picture, thumbnail, user_id, subject_id
                FROM quiz
                LIMIT 100
                """;
        return this.jdbcTemplate.query(sql, this.dtoRowMapper);
    }

    @Override
    public QuizDto getQuiz(String id) {
        var sql = """
                SELECT id, title, description, picture, thumbnail, user_id, subject_id
                FROM quiz
                WHERE id = ?
                """;
        return ListHelper.getApparentValue(this.jdbcTemplate.query(sql, this.dtoRowMapper, id), 0);
    }

    @Override
    public List<QuizDto> getAllQuizzesByTitleQuery(String titleQuery) {
        var sql = """
                SELECT id, title, description, picture, thumbnail, user_id, subject_id
                FROM quiz
                WHERE title LIKE ?
                """;
        return this.jdbcTemplate.query(sql, this.dtoRowMapper, "%" + titleQuery + "%s");
    }

    @Override
    public List<QuizDto> getAllQuizzesByUserId(String userId) {
        var sql = """
                SELECT id, title, description, picture, thumbnail, user_id, subject_id
                FROM quiz
                WHERE user_id = ?
                """;
        return this.jdbcTemplate.query(sql, this.dtoRowMapper, userId);
    }

    @Override
    public List<QuizDto> getAllQuizzesBySubjectId(String subjectId) {
        var sql = """
                SELECT id, title, description, picture, thumbnail, user_id, subject_id
                FROM quiz
                WHERE user_id = ?
                """;
        return this.jdbcTemplate.query(sql, this.dtoRowMapper, subjectId);
    }

    @Override
    public void updateQuiz(String userId, String id, QuizUpdateRequest update) {
        this.updateQuizAttribute(update, "title", QuizUpdateRequest::title);
        this.updateQuizAttribute(update, "description", QuizUpdateRequest::description);
        this.updateQuizAttribute(update, "picture", QuizUpdateRequest::picture);
        this.updateQuizAttribute(update, "thumbnail", QuizUpdateRequest::thumbnail);
    }

    @Override
    public void deleteQuiz(String userId, String id) {
        var sql = """
                DELETE
                FROM quiz
                WHERE id = ?
                """;
        int result = this.jdbcTemplate.update(sql, id);
        System.out.println("DELETE QUIZ RESULT = " + result);
    }

    private void updateQuizAttribute(QuizUpdateRequest update, String attributeName,
            Function<QuizUpdateRequest, String> attributeCollector) {
        String attribute = attributeCollector.apply(update);
        ConditionalHelper.ifThen(attribute != null, () -> {
            String sql = String.format("UPDATE quiz SET %s = ? WHERE id = ?", attributeName);
            int result =
                    this.jdbcTemplate.update(sql, attributeCollector.apply(update), update.id());
            System.out.println("UPDATE QUIZ " + attributeName + " RESULT = " + result);
        });
    }
}
