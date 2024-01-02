package com.ku.quizzical.app.controller.quiz;

import java.util.List;

public interface QuizDatabaseService {
    QuizDto saveQuiz(QuizDto quiz);

    List<QuizDto> getAllQuizzes();

    List<QuizPostDto> getAllQuizzesAsPosts(String subjectId, int limit);

    List<QuizPostDto> getAllQuizzesFromUser(String userId);

    List<QuizPostDto> getAllQuizzesByTitleQuery(String titleQuery);

    List<QuizDto> getAllQuizzesByUserId(String userId);

    List<QuizDto> getAllQuizzesBySubjectId(String subjectId);

    QuizDto getQuiz(String id);

    QuizPostDto getQuizAsPost(String id);

    QuizDto updateQuiz(String id, QuizUpdateRequest quiz);

    void deleteQuiz(String id);
}
