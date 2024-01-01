package com.ku.quizzical.app.controller.question;

import java.util.List;
import java.util.function.Function;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ku.quizzical.app.controller.quiz.Quiz;
import com.ku.quizzical.app.controller.quiz.QuizRepository;
import com.ku.quizzical.common.helper.ConditionalHelper;
import com.ku.quizzical.common.helper.ListHelper;
import com.ku.quizzical.common.helper.number.IdHelper;

@Service
public class QuestionOrdinaryDatabaseService implements QuestionDatabaseService {
    // Instance Fields
    private final JdbcTemplate jdbcTemplate;
    private QuizRepository quizRepository;
    private final QuestionDtoRowMapper dtoRowMapper;

    // Constructor Method
    public QuestionOrdinaryDatabaseService(JdbcTemplate jdbcTemplate,
            QuizRepository quizRepository,
            QuestionDtoRowMapper dtoRowMapper) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        this.quizRepository = quizRepository;
        this.dtoRowMapper = dtoRowMapper;
    }

    // Interface Methods
    @Override
    public QuestionDto saveQuestion(String quizId, QuestionDto questionDto) {
        var sql = """
                INSERT INTO question(id, question, answer, number_of_milliseconds, quiz_id)
                VALUES (?, ?, ?, ?, ?)
                """;
        String id = IdHelper.nextMockId();
        int result = this.jdbcTemplate.update(sql, id, questionDto.question(), questionDto.answer(),
                questionDto.numberOfMilliseconds(), questionDto.quizId());
        System.out.println("POST QUESTION RESULT = " + result);
        Quiz quiz = this.quizRepository.findById(questionDto.quizId()).get();
        return new QuestionDto(id, questionDto.question(), questionDto.answer(),
                questionDto.numberOfMilliseconds(), questionDto.quizId(), quiz.getUser().getId());
    }

    @Override
    public List<QuestionDto> getAllQuestionsByQuizId(String quizId) {
        var sql = """
                SELECT id, question, answer, number_of_milliseconds, quiz_id
                FROM question
                WHERE quiz_id = ?
                """;
        return this.jdbcTemplate.query(sql, this.dtoRowMapper, quizId);
    }

    @Override
    public QuestionDto getQuestion(String quizId, String id) {
        var sql = """
                SELECT id, question, answer, number_of_milliseconds, quiz_id
                FROM question
                WHERE id = ?
                """;
        return ListHelper.getApparentValue(this.jdbcTemplate.query(sql, this.dtoRowMapper, id), 0);
    }

    @Override
    public QuestionDto updateQuestion(String quizId, String id, QuestionUpdateRequest update) {
        this.updateQuestionAttribute(update, "question", QuestionUpdateRequest::question);
        this.updateQuestionAttribute(update, "answer", QuestionUpdateRequest::answer);
        this.updateQuestionAttribute(update, "number_of_milliseconds",
                QuestionUpdateRequest::numberOfMilliseconds);
        return this.getQuestion(quizId, id);
    }

    @Override
    public void deleteQuestion(String quizId, String id) {
        var sql = """
                DELETE
                FROM question
                WHERE id = ?
                """;
        int result = this.jdbcTemplate.update(sql, id);
        System.out.println("DELETE QUESTION RESULT = " + result);
    }

    private <T> void updateQuestionAttribute(QuestionUpdateRequest update, String attributeName,
            Function<QuestionUpdateRequest, T> attributeCollector) {
        T attribute = attributeCollector.apply(update);
        ConditionalHelper.ifThen(attribute != null, () -> {
            String sql = String.format("UPDATE question SET %s = ? WHERE id = ?", attributeName);
            int result = this.jdbcTemplate.update(sql, attributeCollector.apply(update), update.id());
            System.out.println("UPDATE QUESTION " + attributeName + " RESULT = " + result);
        });
    }
}
