package com.ku.quizzical.app.controller.user;

import java.util.function.Function;
import org.springframework.stereotype.Service;
import com.ku.quizzical.app.helper.UserHelper;

@Service
public class UserDtoMapper implements Function<User, UserDto> {
    @Override
    public UserDto apply(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getEmail(),
                user.getProfilePicture(), user.getThumbnail(), UserHelper.makeDefaultRoleList());
    }
}
