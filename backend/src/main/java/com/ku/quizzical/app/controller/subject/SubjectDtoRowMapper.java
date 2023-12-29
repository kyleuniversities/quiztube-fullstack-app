package com.ku.quizzical.app.controller.subject;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class SubjectDtoRowMapper implements RowMapper<SubjectDto> {
        @Override
        public SubjectDto mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
                return new SubjectDto(resultSet.getString("id"), resultSet.getString("text"),
                                resultSet.getString("profile_picture"),
                                resultSet.getString("thumbnail"));
        }
}
