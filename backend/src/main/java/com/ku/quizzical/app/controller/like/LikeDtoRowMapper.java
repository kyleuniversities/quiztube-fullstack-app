package com.ku.quizzical.app.controller.like;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class LikeDtoRowMapper implements RowMapper<LikeDto> {
        @Override
        public LikeDto mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
                return new LikeDto(resultSet.getString("id"), resultSet.getString("quiz_id"),
                                resultSet.getString("user_id"));
        }
}
