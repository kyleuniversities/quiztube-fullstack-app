package com.ku.quizzical.app.controller.question;

public record QuestionUpdateRequest(String question, String answer, int numberOfMilliseconds) {

}
