package com.ku.quizzical.app.controller.user;

public record UserUpdateRequest(
        String username, String email, String password) {

}
