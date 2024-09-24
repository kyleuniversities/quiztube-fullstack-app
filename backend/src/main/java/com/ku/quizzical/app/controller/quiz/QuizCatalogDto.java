package com.ku.quizzical.app.controller.quiz;

import java.util.List;
import java.util.Map;

public record QuizCatalogDto(List<QuizDto> popularQuizzes,
        Map<String, List<QuizDto>> subjectQuizzes, List<QuizDto> allQuizzes) {

}
