package com.ku.quizzical.app.controller.quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class QuizDtoRowMapper implements RowMapper<QuizDto> {
        @Override
        public QuizDto mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
                return new QuizDto(resultSet.getString("id"), resultSet.getString("title"),
                                resultSet.getString("description"), resultSet.getString("picture"),
                                resultSet.getString("thumbnail"), resultSet.getString("user_id"),
                                resultSet.getString("subject_id"));
        }
}
