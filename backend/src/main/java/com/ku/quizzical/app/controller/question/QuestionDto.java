package com.ku.quizzical.app.controller.question;

public record QuestionDto(String id, String question, String answer, int numberOfMilliseconds,
        String quizId) {

}
