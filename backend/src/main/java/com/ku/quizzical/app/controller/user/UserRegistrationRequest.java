package com.ku.quizzical.app.controller.user;

public record UserRegistrationRequest(String id, String username, String email, String password,
        String picture, String thumbnail) {
}
