package com.ku.quizzical.app.controller.quiz;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.ku.quizzical.app.helper.DatabaseValidationHelper;
import com.ku.quizzical.app.helper.TextValidationHelper;
import com.ku.quizzical.common.helper.ComparatorHelper;
import com.ku.quizzical.common.helper.ConditionalHelper;
import com.ku.quizzical.common.helper.ListHelper;
import com.ku.quizzical.common.helper.number.IdHelper;

/**
 * Service class for Quiz Database related operations
 */
@Service
public class QuizOrdinaryDatabaseService implements QuizDatabaseService {
    // Instance Fields
    private final JdbcTemplate jdbcTemplate;
    private final QuizRepository repository;
    private final QuizDtoMapper dtoMapper;
    private final QuizDtoRowMapper dtoRowMapper;

    // Constructor Method
    public QuizOrdinaryDatabaseService(JdbcTemplate jdbcTemplate, QuizRepository repository,
            QuizDtoMapper dtoMapper, QuizDtoRowMapper dtoRowMapper) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        this.repository = repository;
        this.dtoMapper = dtoMapper;
        this.dtoRowMapper = dtoRowMapper;
    }

    // Interface Methods
    @Override
    public QuizDto saveQuiz(QuizAddRequest quiz) {
        this.validateAddQuizRequest(quiz);
        String id = this.nextId();
        var sql = """
                INSERT INTO quiz(id, title, description, picture, thumbnail, user_id, subject_id)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        int result = this.jdbcTemplate.update(sql, id, quiz.title(), quiz.description(),
                quiz.picture(), quiz.thumbnail(), quiz.userId(), quiz.subjectId());
        System.out.println("POST QUIZ RESULT = " + result);
        return this.dtoMapper.apply(this.repository.findById(id).get());
    }

    @Override
    public List<QuizDto> getAllQuizzes(String userId, String subjectId, String titleQuery,
            int limit) {
        List<QuizDto> q = ListHelper.toArrayList(ListHelper
                .shuffleWithFallthrough(this.repository.findAll()).stream()
                .filter(this.makeQuizUserIdFilter(userId))
                .filter(this.makeQuizSubjectIdFilter(subjectId))
                .filter(this.makeQuizTitleQueryFilter(titleQuery)).map(this.dtoMapper::apply)
                .sorted(ComparatorHelper.newReversedOrdinalComparator(QuizDto::numberOfLikes))
                .limit(limit).toList());
        return q;
    }

    @Override
    public QuizDto getQuiz(String id) {
        var sql = """
                SELECT id, title, description, picture, thumbnail, user_id, subject_id
                FROM quiz
                WHERE id = ?
                """;
        DatabaseValidationHelper.validateExistingResource("Quiz", "id", id,
                this.repository::findById);
        return ListHelper.getApparentValue(this.jdbcTemplate.query(sql, this.dtoRowMapper, id), 0);
    }

    @Override
    public QuizDto updateQuiz(String id, QuizUpdateRequest update) {
        this.updateQuizAttribute(id, update, "title", QuizUpdateRequest::title);
        this.updateQuizAttribute(id, update, "description", QuizUpdateRequest::description);
        this.updateQuizAttribute(id, update, "picture", QuizUpdateRequest::picture);
        this.updateQuizAttribute(id, update, "thumbnail", QuizUpdateRequest::thumbnail);
        this.updateQuizAttribute(id, update, "subject_id", QuizUpdateRequest::subjectId);
        TextValidationHelper.validateIfExists(update::title, this::validateTitle);
        TextValidationHelper.validateIfExists(update::description, this::validateDescription);
        return this.getQuiz(id);
    }

    @Override
    public void deleteQuiz(String id) {
        this.repository.deleteById(id);
        System.out.println("DELETE QUIZ RESULT = " + 1);
    }

    private Predicate<Quiz> makeQuizUserIdFilter(String userId) {
        return (Quiz quiz) -> userId == null || quiz.getUserId().contains(userId);
    }

    private Predicate<Quiz> makeQuizSubjectIdFilter(String subjectId) {
        return (Quiz quiz) -> subjectId == null || quiz.getSubject().getId().contains(subjectId);
    }

    private Predicate<Quiz> makeQuizTitleQueryFilter(String titleQuery) {
        return (Quiz quiz) -> titleQuery == null
                || quiz.getTitle().toLowerCase().contains(titleQuery.toLowerCase());
    }

    private void updateQuizAttribute(String id, QuizUpdateRequest update, String attributeName,
            Function<QuizUpdateRequest, String> attributeCollector) {
        String attribute = attributeCollector.apply(update);
        ConditionalHelper.ifThen(attribute != null, () -> {
            String sql = String.format("UPDATE quiz SET %s = ? WHERE id = ?", attributeName);
            int result = this.jdbcTemplate.update(sql, attributeCollector.apply(update), id);
            System.out.println("UPDATE QUIZ " + attributeName + " RESULT = " + result);
        });
    }

    // Validation Major Methods
    private void validateAddQuizRequest(QuizAddRequest quiz) {
        validateTitle(quiz.title());
        validateDescription(quiz.description());
    }

    // Validation Minor Methods
    private void validateTitle(String text) {
        TextValidationHelper.validateNonNull("Title", text);
        TextValidationHelper.validateLength("Title", text, 1, 64);
    }

    private void validateDescription(String text) {
        TextValidationHelper.validateNonNull("Description", text);
        TextValidationHelper.validateLength("Description", text, 1, 250);
    }

    // Id Methods
    private String nextId() {
        return IdHelper.nextMockId();
    }
}
