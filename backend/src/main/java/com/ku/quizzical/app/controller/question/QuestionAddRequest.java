package com.ku.quizzical.app.controller.question;

public record QuestionAddRequest(String question, String answer, int numberOfMilliseconds,
        String quizId, String userId) {

}
