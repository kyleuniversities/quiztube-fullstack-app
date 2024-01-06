package com.ku.quizzical.app.controller.quiz;

public record QuizAddRequest(String title, String description, String picture, String thumbnail,
        String userId, String subjectId) {

}
