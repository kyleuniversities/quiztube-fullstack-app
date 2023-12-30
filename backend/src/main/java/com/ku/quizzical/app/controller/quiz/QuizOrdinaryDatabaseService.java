package com.ku.quizzical.app.controller.quiz;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.ku.quizzical.common.helper.ComparatorHelper;
import com.ku.quizzical.common.helper.ConditionalHelper;
import com.ku.quizzical.common.helper.ListHelper;
import com.ku.quizzical.common.helper.number.IdHelper;

@Service
public class QuizOrdinaryDatabaseService implements QuizDatabaseService {
    // Instance Fields
    private final JdbcTemplate jdbcTemplate;
    private final QuizRepository repository;
    private final QuizDtoRowMapper dtoRowMapper;
    private final QuizPostDtoMapper postDtoRowMapper;

    // Constructor Method
    public QuizOrdinaryDatabaseService(JdbcTemplate jdbcTemplate, QuizRepository repository,
            QuizDtoRowMapper dtoRowMapper, QuizPostDtoMapper postDtoRowMapper) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        this.repository = repository;
        this.dtoRowMapper = dtoRowMapper;
        this.postDtoRowMapper = postDtoRowMapper;
    }

    // Interface Methods
    @Override
    public QuizDto saveQuiz(QuizDto quizDto) {
        var sql = """
                INSERT INTO quiz(id, title, description, picture, thumbnail, user_id, subject_id)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        String id = IdHelper.nextMockId();
        int result = this.jdbcTemplate.update(sql, id, quizDto.title(), quizDto.description(),
                quizDto.picture(), quizDto.thumbnail(), quizDto.userId(), quizDto.subjectId());
        System.out.println("POST QUIZ RESULT = " + result);
        return new QuizDto(id, quizDto.title(), quizDto.description(), quizDto.picture(),
                quizDto.thumbnail(), quizDto.userId(), quizDto.subjectId());
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
    public List<QuizPostDto> getAllQuizzesAsPosts(String subjectId) {
        return this.repository.findAll().stream().filter(this.makeQuizSubjectFilter(subjectId))
                .map(this.postDtoRowMapper::apply)
                .sorted(ComparatorHelper.newReversedOrdinalComparator(QuizPostDto::numberOfLikes))
                .toList();
    }

    @Override
    public List<QuizPostDto> getAllQuizzesFromUser(String userId) {
        return this.repository.findAll().stream().filter(this.makeQuizUserFilter(userId))
                .map(this.postDtoRowMapper::apply)
                .sorted(ComparatorHelper.newReversedOrdinalComparator(QuizPostDto::numberOfLikes))
                .toList();
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
    public QuizPostDto getQuizAsPost(String id) {
        QuizDto quizDto = this.getQuiz(id);
        Quiz quiz = this.repository.findById(quizDto.id()).get();
        return this.postDtoRowMapper.apply(quiz);
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
    public QuizDto updateQuiz(String id, QuizUpdateRequest update) {
        this.updateQuizAttribute(id, update, "title", QuizUpdateRequest::title);
        this.updateQuizAttribute(id, update, "description", QuizUpdateRequest::description);
        this.updateQuizAttribute(id, update, "picture", QuizUpdateRequest::picture);
        this.updateQuizAttribute(id, update, "thumbnail", QuizUpdateRequest::thumbnail);
        this.updateQuizAttribute(id, update, "subject_id", QuizUpdateRequest::subjectId);
        return this.getQuiz(id);
    }

    @Override
    public void deleteQuiz(String id) {
        var sql = """
                DELETE
                FROM quiz
                WHERE id = ?
                """;
        int result = this.jdbcTemplate.update(sql, id);
        System.out.println("DELETE QUIZ RESULT = " + result);
    }

    private Predicate<Quiz> makeQuizSubjectFilter(String subjectId) {
        return (Quiz quiz) -> subjectId == null || quiz.getSubject().getId().equals(subjectId);
    }

    private Predicate<Quiz> makeQuizUserFilter(String userId) {
        return (Quiz quiz) -> userId == null || quiz.getUser().getId().equals(userId);
    }

    private void updateQuizAttribute(String id, QuizUpdateRequest update, String attributeName,
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
