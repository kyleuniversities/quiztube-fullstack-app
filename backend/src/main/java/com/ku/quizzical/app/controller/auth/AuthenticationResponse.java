package com.ku.quizzical.app.controller.auth;

import com.ku.quizzical.app.controller.user.UserDto;

public record AuthenticationResponse(
                String token, UserDto userDTO) {
}
