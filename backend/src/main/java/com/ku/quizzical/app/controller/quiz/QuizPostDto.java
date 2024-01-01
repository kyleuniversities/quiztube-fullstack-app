package com.ku.quizzical.app.controller.quiz;

public record QuizPostDto(String id, String title, String description, String picture, String thumbnail, String userId,
                String subjectId,
                String authorUsername, String subject, int numberOfLikes) {

}
