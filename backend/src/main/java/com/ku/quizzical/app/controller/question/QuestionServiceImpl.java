package com.ku.quizzical.app.controller.question;

import java.util.List;
import org.springframework.stereotype.Service;
import com.ku.quizzical.app.controller.quiz.Quiz;
import com.ku.quizzical.app.controller.quiz.QuizRepository;
import com.ku.quizzical.common.helper.ListHelper;

@Service
public class QuestionServiceImpl implements QuestionService {
    // Instance Fields
    private QuizRepository quizRepository;
    private QuestionRepository repository;
    private QuestionDtoMapper dtoMapper;

    // Constructor Method
    public QuestionServiceImpl(QuizRepository quizRepository, QuestionRepository repository) {
        super();
        this.quizRepository = quizRepository;
        this.repository = repository;
        this.dtoMapper = new QuestionDtoMapper();
    }

    // Interface Methods
    @Override
    public QuestionDto saveQuestion(QuestionDto questionDto) {
        Quiz quiz = this.quizRepository.findById(questionDto.quizId()).get();
        Question question = new Question();
        question.setQuestion(questionDto.question());
        question.setAnswer(questionDto.answer());
        question.setNumberOfMilliseconds(questionDto.numberOfMilliseconds());
        question.setQuiz(quiz);
        return this.dtoMapper.apply(this.repository.save(question));
    }

    @Override
    public List<QuestionDto> getAllQuestions() {
        return ListHelper.map(this.repository.findAll(), this.dtoMapper::apply);
    }

    @Override
    public QuestionDto getQuestionById(String id) {
        return this.dtoMapper.apply(this.repository.findById(id).get());
    }

    @Override
    public List<QuestionDto> getAllQuestionsByQuizId(String quizId) {
        return ListHelper.map(this.quizRepository.findById(quizId).get().getQuestions(), this.dtoMapper::apply);
    }
                

    @Override
    public QuestionDto updateQuestion(QuestionDto question, String id) {
        Question existingQuestion = this.repository.findById(id).get();
        existingQuestion.setQuestion(question.question());
        existingQuestion.setAnswer(question.answer());
        existingQuestion.setNumberOfMilliseconds(question.numberOfMilliseconds());
        this.repository.save(existingQuestion);
        return this.dtoMapper.apply(existingQuestion);
    }

    @Override
    public void deleteQuestion(String id) {
        this.repository.deleteById(id);
    }
}
