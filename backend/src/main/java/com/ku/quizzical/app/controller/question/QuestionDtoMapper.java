package com.ku.quizzical.app.controller.question;

import java.util.function.Function;
import org.springframework.stereotype.Service;

@Service
public class QuestionDtoMapper implements Function<Question, QuestionDto> {
    @Override
    public QuestionDto apply(Question question) {
        return new QuestionDto(question.getId(), question.getQuestion(), question.getAnswer(),
                question.getNumberOfMilliseconds(), question.getQuiz().getId(), question.getQuiz().getUser().getId());
    }
}
