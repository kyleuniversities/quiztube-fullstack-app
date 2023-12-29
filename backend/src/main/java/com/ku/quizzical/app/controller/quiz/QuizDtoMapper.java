package com.ku.quizzical.app.controller.quiz;

import java.util.function.Function;
import org.springframework.stereotype.Service;

@Service
public class QuizDtoMapper implements Function<Quiz, QuizDto> {
    @Override
    public QuizDto apply(Quiz quiz) {
        return new QuizDto(quiz.getId(), quiz.getTitle(), quiz.getDescription(), quiz.getPicture(),
                quiz.getThumbnail(), quiz.getUser().getId(), quiz.getSubject().getId());
    }
}
