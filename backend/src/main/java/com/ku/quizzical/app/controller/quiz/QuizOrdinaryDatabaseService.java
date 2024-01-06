package com.ku.quizzical.app.controller.quiz;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.ku.quizzical.app.controller.user.UserRepository;
import com.ku.quizzical.app.helper.DatabaseValidationHelper;
import com.ku.quizzical.app.helper.TextValidationHelper;
import com.ku.quizzical.common.helper.ComparatorHelper;
import com.ku.quizzical.common.helper.ConditionalHelper;
import com.ku.quizzical.common.helper.ListHelper;
import com.ku.quizzical.common.helper.PrintHelper;
import com.ku.quizzical.common.helper.number.IdHelper;

/**
 * Service class for Quiz Database related operations
 */
@Service
public class QuizOrdinaryDatabaseService implements QuizDatabaseService {
    // Instance Fields
    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;
    private final QuizRepository repository;
    private final QuizDtoRowMapper dtoRowMapper;
    private final QuizPostDtoMapper postDtoMapper;
    private final QuizPostDtoRowMapper postDtoRowMapper;

    // Constructor Method
    public QuizOrdinaryDatabaseService(JdbcTemplate jdbcTemplate, UserRepository userRepository,
            QuizRepository repository, QuizDtoRowMapper dtoRowMapper,
            QuizPostDtoMapper postDtoMapper, QuizPostDtoRowMapper postDtoRowMapper) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
        this.repository = repository;
        this.dtoRowMapper = dtoRowMapper;
        this.postDtoMapper = postDtoMapper;
        this.postDtoRowMapper = postDtoRowMapper;
    }

    // Interface Methods
    @Override
    public QuizDto saveQuiz(QuizAddRequest quiz) {
        PrintHelper.printLine("3.1");
        this.validateAddQuizRequest(quiz);
        PrintHelper.printLine("3.2");
        String id = this.nextId();
        var sql = """
                INSERT INTO quiz(id, title, description, picture, thumbnail, user_id, subject_id)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        PrintHelper.printLine("3.3");
        PrintHelper.printEntry("id", id);
        int result = this.jdbcTemplate.update(sql, id, quiz.title(), quiz.description(),
                quiz.picture(), quiz.thumbnail(), quiz.userId(), quiz.subjectId());
        System.out.println("POST QUIZ RESULT = " + result);
        PrintHelper.printLine("3.4");
        return new QuizDto(id, quiz.title(), quiz.description(), quiz.picture(), quiz.thumbnail(),
                quiz.userId(), quiz.subjectId());
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
    public List<QuizPostDto> getAllQuizzesAsPosts(String subjectId, int limit) {
        return ListHelper.shuffleWithFallthrough(this.repository.findAll()).stream()
                .filter(this.makeQuizSubjectFilter(subjectId)).map(this.postDtoMapper::apply)
                .sorted(ComparatorHelper.newReversedOrdinalComparator(QuizPostDto::numberOfLikes))
                .limit(limit).toList();
    }

    @Override
    public List<QuizPostDto> getAllQuizzesFromUser(String userId) {
        DatabaseValidationHelper.validateExistingResource("User", "id", userId,
                this.userRepository::findById);
        return this.repository.findAll().stream().filter(this.makeQuizUserFilter(userId))
                .map(this.postDtoMapper::apply)
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
        DatabaseValidationHelper.validateExistingResource("Quiz", "id", id,
                this.repository::findById);
        return ListHelper.getApparentValue(this.jdbcTemplate.query(sql, this.dtoRowMapper, id), 0);
    }

    @Override
    public QuizPostDto getQuizAsPost(String id) {
        QuizDto quizDto = this.getQuiz(id);
        Quiz quiz = this.repository.findById(quizDto.id()).get();
        return this.postDtoMapper.apply(quiz);
    }

    @Override
    public List<QuizPostDto> getAllQuizzesByTitleQuery(String titleQuery) {
        var sql = """
                SELECT id, title, description, picture, thumbnail, user_id, subject_id
                FROM quiz
                WHERE title LIKE ?
                """;
        String text = "%" + titleQuery + "%";
        System.out.println("TEXT: " + text);
        return this.jdbcTemplate.query(sql, this.postDtoRowMapper, text);
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
        PrintHelper.printLine("1");
        this.updateQuizAttribute(id, update, "title", QuizUpdateRequest::title);
        this.updateQuizAttribute(id, update, "description", QuizUpdateRequest::description);
        this.updateQuizAttribute(id, update, "picture", QuizUpdateRequest::picture);
        this.updateQuizAttribute(id, update, "thumbnail", QuizUpdateRequest::thumbnail);
        this.updateQuizAttribute(id, update, "subject_id", QuizUpdateRequest::subjectId);
        PrintHelper.printLine("2");
        TextValidationHelper.validateIfExists(update::title, this::validateTitle);
        TextValidationHelper.validateIfExists(update::description, this::validateDescription);
        PrintHelper.printLine("3");
        return this.getQuiz(id);
    }

    @Override
    public void deleteQuiz(String id) {
        this.repository.deleteById(id);
        System.out.println("DELETE QUIZ RESULT = " + 1);
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
