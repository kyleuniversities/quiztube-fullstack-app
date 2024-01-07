package com.ku.quizzical.app.controller.quiz;

import java.util.List;

public interface QuizDatabaseService {
    QuizDto saveQuiz(QuizAddRequest quiz);

    List<QuizDto> getAllQuizzes(String userId, String subjectId, String titleQuery, int limit);

    QuizDto getQuiz(String id);

    QuizDto updateQuiz(String id, QuizUpdateRequest quiz);

    void deleteQuiz(String id);
}
