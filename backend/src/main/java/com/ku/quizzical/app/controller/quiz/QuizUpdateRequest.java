package com.ku.quizzical.app.controller.quiz;

public record QuizUpdateRequest(String title, String description, String picture, String thumbnail,
                String subjectId) {

}
