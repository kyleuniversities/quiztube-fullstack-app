package com.ku.quizzical.app.controller.quiz;

public record QuizPostDto(String id, String title, String description, String picture,
                String thumbnail, String authorUsername, String subject, int numberOfLikes) {

}
