package com.ku.quizzical.app.controller.quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class QuizDtoRowMapper implements RowMapper<QuizDto> {
        private QuizRepository repository;
        private QuizDtoMapper dtoMapper;

        public QuizDtoRowMapper(QuizRepository repository, QuizDtoMapper dtoMapper) {
                this.repository = repository;
                this.dtoMapper = dtoMapper;
        }

        @Override
        public QuizDto mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
                Quiz quiz = this.repository.findById(resultSet.getString("id")).get();
                return this.dtoMapper.apply(quiz);
        }
}
