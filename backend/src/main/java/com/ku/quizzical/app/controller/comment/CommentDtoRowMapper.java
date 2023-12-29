package com.ku.quizzical.app.controller.comment;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class CommentDtoRowMapper implements RowMapper<CommentDto> {
        @Override
        public CommentDto mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
                return new CommentDto(resultSet.getString("id"), resultSet.getString("text"),
                                resultSet.getString("quiz_id"), resultSet.getString("user_id"));
        }
}
