package com.ku.quizzical.app.controller.quiz;

import java.util.List;

public interface QuizDatabaseService {
    void saveQuiz(String userId, QuizDto quiz);

    List<QuizDto> getAllQuizzes();

    List<QuizDto> getAllQuizzesByTitleQuery(String titleQuery);

    List<QuizDto> getAllQuizzesByUserId(String userId);

    List<QuizDto> getAllQuizzesBySubjectId(String subjectId);

    QuizDto getQuiz(String id);

    void updateQuiz(String userId, String id, QuizUpdateRequest quiz);

    void deleteQuiz(String userId, String id);
}
