package com.ku.quizzical.app.controller.question;

public record QuestionUpdateRequest(String id, String question, String answer,
                int numberOfMilliseconds) {

}
