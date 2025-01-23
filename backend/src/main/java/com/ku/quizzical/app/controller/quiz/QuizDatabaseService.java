package com.ku.quizzical.app.controller.quiz;

import java.util.List;
import com.ku.quizzical.app.util.dto.IntegerDto;

public interface QuizDatabaseService {
    QuizDto saveQuiz(QuizAddRequest quiz);

    List<QuizDto> getAllQuizzes(String userId, String subjectId, String titleQuery, int limit,
            int offset);

    IntegerDto getNumberOfQuizzes(String userId, String subjectId, String titleQuery);

    QuizCatalogDto getQuizCatalog(int limit);

    QuizDto getQuiz(String id);

    QuizDto updateQuiz(String id, QuizUpdateRequest quiz);

    void deleteQuiz(String id);
}
