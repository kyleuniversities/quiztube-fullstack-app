package com.ku.quizzical.app.controller.user;

import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class UserDtoMapper implements Function<User, UserDto> {
    @Override
    public UserDto apply(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
    }
}
