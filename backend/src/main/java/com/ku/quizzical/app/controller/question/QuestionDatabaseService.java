package com.ku.quizzical.app.controller.question;

import java.util.List;

public interface QuestionDatabaseService {
    void saveQuestion(String userId, String quizId, QuestionDto question);

    List<QuestionDto> getAllQuestionsByQuizId(String quizId);

    QuestionDto getQuestion(String quizId, String id);

    void updateQuestion(String userId, String quizId, String id, QuestionUpdateRequest question);

    void deleteQuestion(String userId, String quizId, String id);
}
