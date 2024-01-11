package com.ku.quizzical.app.util.integration;

import java.util.Map;
import com.ku.quizzical.app.controller.auth.AuthenticationRequest;
import com.ku.quizzical.app.controller.question.Question;
import com.ku.quizzical.app.controller.question.QuestionAddRequest;
import com.ku.quizzical.app.controller.question.QuestionDto;
import com.ku.quizzical.app.controller.quiz.Quiz;
import com.ku.quizzical.app.controller.quiz.QuizAddRequest;
import com.ku.quizzical.app.controller.quiz.QuizDto;
import com.ku.quizzical.app.controller.subject.Subject;
import com.ku.quizzical.app.controller.subject.SubjectDto;
import com.ku.quizzical.app.controller.user.User;
import com.ku.quizzical.app.controller.user.UserDto;
import com.ku.quizzical.app.controller.user.UserRegistrationRequest;
import com.ku.quizzical.app.helper.controller.AuthenticationTestHelper;
import com.ku.quizzical.app.helper.controller.QuestionTestHelper;
import com.ku.quizzical.app.helper.controller.QuizTestHelper;
import com.ku.quizzical.app.helper.controller.SubjectTestHelper;
import com.ku.quizzical.app.helper.controller.UserTestHelper;
import com.ku.quizzical.app.util.TestRestTemplateContainer;
import com.ku.quizzical.common.helper.FunctionHelper;
import com.ku.quizzical.common.helper.IterationHelper;
import com.ku.quizzical.common.helper.ListHelper;
import com.ku.quizzical.common.helper.MapHelper;
import com.ku.quizzical.common.helper.PrintHelper;

/**
 * Function class for creating test users
 */
public final class RealizedTestUserMaker {
    // Instance Fields
    private TestRestTemplateContainer container;
    private int numberOfUsers;
    private Map<String, UserRegistrationRequest> userRegistrationRequests;
    private Map<String, UserDto> userDtos;
    private Map<String, User> users;
    private Map<String, SubjectDto> subjectDtos;
    private Map<String, Subject> subjects;
    private Map<String, QuizDto> quizDtos;
    private Map<String, Quiz> quizzes;
    private Map<String, QuestionDto> questionDtos;
    private Map<String, Question> questions;

    // New Instance Method
    public static RealizedTestUserMaker newInstance() {
        return new RealizedTestUserMaker();
    }

    // Constructor Method
    private RealizedTestUserMaker() {
        super();
    }

    // Accessor Methods
    public Map<String, Question> getQuestions() {
        return MapHelper.clone(this.questions);
    }

    // Main Instance Method
    public Map<String, User> perform(TestRestTemplateContainer container, int numberOfUsers) {
        this.reset(container, numberOfUsers);
        this.compileTestUserRegistrationRequests();
        this.compileTestUserDtos();
        this.compileTestUsers();
        this.compileSubjectDtos();
        this.compileSubjects();
        this.compileTestQuizDtos();
        this.compileTestQuizzes();
        this.compileTestQuestionDtos();
        this.compileTestQuestions();
        return MapHelper.clone(this.users);
    }

    // Major Methods
    private void compileTestUserRegistrationRequests() {
        this.userRegistrationRequests = this.makeTestUserRegistrationRequests();
        PrintHelper.printMap("USER_REGISTRATION_REQUESTS", this.userRegistrationRequests);
    }

    private void compileTestUserDtos() {
        this.userDtos = this.makeTestUserDtos();
    }

    private void compileTestUsers() {
        this.users = this.makeTestUsers();
        PrintHelper.printMap("USERS", this.users);
    }

    private void compileSubjectDtos() {
        this.subjectDtos = this.makeSubjectDtos();
    }

    private void compileSubjects() {
        this.subjects = this.makeSubjects();
        PrintHelper.printMap("SUBJECTS", this.subjects);
    }

    private void compileTestQuizDtos() {
        this.quizDtos = this.makeTestQuizDtos();
    }

    private void compileTestQuizzes() {
        this.quizzes = this.makeTestQuizzes();
        PrintHelper.printMap("QUIZZES", this.quizzes);
    }

    private void compileTestQuestionDtos() {
        this.questionDtos = this.makeTestQuestionDtos();
    }

    private void compileTestQuestions() {
        this.questions = this.makeTestQuestions();
    }

    // Minor Methods
    private Map<String, UserRegistrationRequest> makeTestUserRegistrationRequests() {
        Map<String, UserRegistrationRequest> userRegistrationRequests =
                MapHelper.newLinkedHashMap();
        IterationHelper.forEach(this.numberOfUsers, (Integer i) -> {
            UserRegistrationRequest request =
                    UserTestHelper.newRandomRegistrationRequest("testrxx" + i);
            MapHelper.put(userRegistrationRequests, request.username(), request);
        });
        return userRegistrationRequests;
    }

    private Map<String, UserDto> makeTestUserDtos() {
        Map<String, UserDto> userDtos = MapHelper.newLinkedHashMap();
        MapHelper.forEach(this.userRegistrationRequests,
                (String username, UserRegistrationRequest request) -> {
                    UserDto userDto = UserTestHelper.saveUser(request, container);
                    MapHelper.put(userDtos, userDto.id(), userDto);
                });
        return userDtos;
    }

    private Map<String, User> makeTestUsers() {
        return MapHelper.mapValues(this.userDtos,
                (UserDto userDto) -> new User(userDto.id(), userDto.username(), userDto.email(),
                        null, userDto.picture(), userDto.thumbnail(), ListHelper.newArrayList(),
                        ListHelper.newArrayList(), ListHelper.newArrayList()));
    }

    private Map<String, SubjectDto> makeSubjectDtos() {
        return ListHelper.mapToMap(SubjectTestHelper.getAllSubjects(this.container),
                (SubjectDto subjectDto) -> subjectDto.id(), FunctionHelper::identityMapping);
    }

    private Map<String, Subject> makeSubjects() {
        return MapHelper.mapValues(this.subjectDtos,
                (SubjectDto subjectDto) -> new Subject(subjectDto.id(), subjectDto.text(),
                        subjectDto.picture(), subjectDto.thumbnail(), ListHelper.newArrayList()));
    }

    private Map<String, QuizDto> makeTestQuizDtos() {
        Map<String, QuizDto> quizDtos = MapHelper.newLinkedHashMap();
        MapHelper.forEach(this.userDtos, (String userId, UserDto userDto) -> {
            this.logIn(userDto);
            IterationHelper.forEach(5, () -> {
                QuizAddRequest request =
                        QuizTestHelper.newRandomQuizAddRequest(userId, this.container);
                QuizDto quizDto = QuizTestHelper.saveQuiz(request, this.container);
                MapHelper.put(quizDtos, quizDto.id(), quizDto);
            });
            this.logOut();
        });
        return quizDtos;
    }

    private Map<String, Quiz> makeTestQuizzes() {
        return MapHelper.mapValues(this.quizDtos, (QuizDto quizDto) -> {
            User user = this.users.get(quizDto.userId());
            Subject subject = this.subjects.get(quizDto.subjectId());
            Quiz quiz = new Quiz(quizDto.id(), quizDto.title(), quizDto.description(),
                    quizDto.picture(), quizDto.thumbnail(), user, subject,
                    ListHelper.newArrayList(), ListHelper.newArrayList(),
                    ListHelper.newArrayList());
            ListHelper.add(user.getQuizzes(), quiz);
            ListHelper.add(subject.getQuizzes(), quiz);
            return quiz;
        });
    }

    private Map<String, QuestionDto> makeTestQuestionDtos() {
        Map<String, QuestionDto> questionDtos = MapHelper.newLinkedHashMap();
        MapHelper.forEach(this.quizDtos, (String quizId, QuizDto quizDto) -> {
            String userId = quizDto.userId();
            UserDto userDto = this.userDtos.get(userId);
            this.logIn(userDto);
            IterationHelper.forEach(5, () -> {
                QuestionAddRequest request = QuestionTestHelper.newRandomQuestionAddRequest(quizId,
                        quizDto.userId(), this.container);
                QuestionDto questionDto =
                        QuestionTestHelper.saveQuestion(quizId, request, this.container);
                MapHelper.put(questionDtos, questionDto.id(), questionDto);
            });
            this.logOut();
        });
        return questionDtos;
    }

    private Map<String, Question> makeTestQuestions() {
        return MapHelper.mapValues(this.questionDtos, (QuestionDto questionDto) -> {
            Quiz quiz = this.quizzes.get(questionDto.quizId());
            Question question = new Question(questionDto.id(), questionDto.question(),
                    questionDto.answer(), questionDto.numberOfMilliseconds(), quiz);
            ListHelper.add(quiz.getQuestions(), question);
            return question;
        });
    }

    // Private Helper Methods
    private void logIn(UserDto userDto) {
        AuthenticationRequest request = new AuthenticationRequest(userDto.username(),
                this.userRegistrationRequests.get(userDto.username()).password());
        AuthenticationTestHelper.logIn(request, this.container);
    }

    private void logOut() {
        this.container.resetHeaders();
    }

    // Initialization Methods
    private void reset(TestRestTemplateContainer container, int numberOfUsers) {
        this.container = container;
        this.numberOfUsers = numberOfUsers;
    }
}
