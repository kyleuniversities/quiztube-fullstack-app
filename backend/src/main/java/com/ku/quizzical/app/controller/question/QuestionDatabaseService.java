package com.ku.quizzical.app.controller.question;

import java.util.List;

public interface QuestionService {
    QuestionDto saveQuestion(String userId, String quizId, QuestionDto question);

    List<QuestionDto> getAllQuestionsByQuizId(String quizId);

    QuestionDto getQuestionById(String quizId, String id);

    QuestionDto updateQuestion(String userId, String quizId, String id, QuestionDto question);

    void deleteQuestion(String userId, String quizId, String id);
}
