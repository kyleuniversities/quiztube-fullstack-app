package com.ku.quizzical.app.controller.quiz;

import java.util.List;

public interface QuizService {
    QuizDto saveQuiz(String userId, QuizDto quiz);

    List<QuizDto> getAllQuizzes();

    List<QuizDto> getAllQuizzesByTitleQuery(String titleQuery);

    List<QuizDto> getAllQuizzesByUserId(String userId);

    List<QuizDto> getAllQuizzesBySubjectId(String subjectId);

    QuizDto getQuizById(String id);

    QuizDto updateQuiz(String userId, String id, QuizDto quiz);

    void deleteQuiz(String userId, String id);
}
