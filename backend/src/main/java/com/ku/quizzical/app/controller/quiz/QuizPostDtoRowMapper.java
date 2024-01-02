package com.ku.quizzical.app.controller.quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class QuizPostDtoRowMapper implements RowMapper<QuizPostDto> {
        private QuizRepository repository;
        private QuizPostDtoMapper dtoMapper;

        public QuizPostDtoRowMapper(QuizRepository repository, QuizPostDtoMapper dtoMapper) {
                this.repository = repository;
                this.dtoMapper = dtoMapper;
        }

        @Override
        public QuizPostDto mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
                Quiz quiz = this.repository.findById(resultSet.getString("id")).get();
                return this.dtoMapper.apply(quiz);
        }
}
