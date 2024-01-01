package com.ku.quizzical.app.controller.question;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.ku.quizzical.app.controller.quiz.Quiz;
import com.ku.quizzical.app.controller.quiz.QuizRepository;

@Component
public class QuestionDtoRowMapper implements RowMapper<QuestionDto> {
        private QuizRepository quizRepository;

        public QuestionDtoRowMapper(QuizRepository quizRepository) {
                this.quizRepository = quizRepository;
        }

        @Override
        public QuestionDto mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
                Quiz quiz = this.quizRepository.findById(resultSet.getString("quiz_id")).get();
                return new QuestionDto(resultSet.getString("id"), resultSet.getString("question"),
                                resultSet.getString("answer"),
                                resultSet.getInt("number_of_milliseconds"),
                                resultSet.getString("quiz_id"), quiz.getUser().getId());
        }
}
