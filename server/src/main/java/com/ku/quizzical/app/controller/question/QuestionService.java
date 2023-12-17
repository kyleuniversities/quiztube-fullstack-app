package com.ku.quizzical.app.controller.question;

import java.util.List;

public interface QuestionService {
    QuestionDto saveQuestion(QuestionDto question);

    List<QuestionDto> getAllQuestions();

    List<QuestionDto> getAllQuestionsByQuizId(String quizId);

    QuestionDto getQuestionById(String id);

    QuestionDto updateQuestion(QuestionDto question, String id);

    void deleteQuestion(String id);
}
