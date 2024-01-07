package com.ku.quizzical.app.controller.quiz;

import java.util.function.Function;
import org.springframework.stereotype.Service;
import com.ku.quizzical.app.controller.subject.Subject;
import com.ku.quizzical.app.controller.user.User;

@Service
public class QuizDtoMapper implements Function<Quiz, QuizDto> {
    @Override
    public QuizDto apply(Quiz quiz) {
        User user = quiz.getUser();
        Subject subject = quiz.getSubject();
        return new QuizDto(quiz.getId(), quiz.getTitle(), quiz.getDescription(), quiz.getPicture(),
                quiz.getThumbnail(), user.getId(), subject.getId(), user.getUsername(),
                subject.getText(), quiz.getQuestions().size(), quiz.getLikes().size());
    }
}
