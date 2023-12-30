package com.ku.quizzical.app.controller.quiz;

import java.util.function.Function;
import org.springframework.stereotype.Service;

@Service
public class QuizPostDtoMapper implements Function<Quiz, QuizPostDto> {
    @Override
    public QuizPostDto apply(Quiz quiz) {
        return new QuizPostDto(quiz.getId(), quiz.getTitle(), quiz.getDescription(),
                quiz.getPicture(), quiz.getThumbnail(), quiz.getUser().getUsername(),
                quiz.getSubject().getText(), quiz.getLikes().size());
    }
}
