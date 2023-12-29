package com.ku.quizzical.app.controller.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.ku.quizzical.app.helper.UserHelper;

@Component
public class UserDtoRowMapper implements RowMapper<UserDto> {
        @Override
        public UserDto mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
                return new UserDto(resultSet.getString("id"), resultSet.getString("username"),
                                resultSet.getString("email"),
                                resultSet.getString("profile_picture"),
                                resultSet.getString("thumbnail"), UserHelper.makeDefaultRoleList());
        }
}
