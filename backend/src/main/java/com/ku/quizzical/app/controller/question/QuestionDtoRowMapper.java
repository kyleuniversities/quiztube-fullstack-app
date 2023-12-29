package com.ku.quizzical.app.controller.question;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class QuizDtoRowMapper implements RowMapper<QuestionDto> {
        @Override
        public QuestionDto mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
                return new QuestionDto(resultSet.getString("id"), resultSet.getString("question"),
                                resultSet.getString("answer"),
                                resultSet.getInt("number_of_milliseconds"),
                                resultSet.getString("quiz_id"));
        }
}
