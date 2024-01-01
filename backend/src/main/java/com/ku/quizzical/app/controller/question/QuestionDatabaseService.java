package com.ku.quizzical.app.controller.question;

import java.util.List;

public interface QuestionDatabaseService {
    QuestionDto saveQuestion(String quizId, QuestionDto question);

    List<QuestionDto> getAllQuestionsByQuizId(String quizId);

    QuestionDto getQuestion(String quizId, String id);

    QuestionDto updateQuestion(String quizId, String id, QuestionUpdateRequest question);

    void deleteQuestion(String quizId, String id);
}
