package com.ku.quizzical.app.controller.user;

import java.util.List;

public record UserDto(String id, String username, String email, String picture, String thumbnail,
        List<String> roles) {

}
