package com.ku.quizzical.app.controller.user;

public record UserUpdateRequest(String id, String username, String email, String password,
                String profilePicture, String thumbnail) {

}
