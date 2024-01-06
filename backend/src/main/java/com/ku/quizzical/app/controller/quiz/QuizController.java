package com.ku.quizzical.app.controller.quiz;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ku.quizzical.app.controller.user.User;
import com.ku.quizzical.app.controller.user.UserRepository;
import com.ku.quizzical.app.helper.AuthorizationValidationHelper;
import com.ku.quizzical.app.helper.DatabaseValidationHelper;
import com.ku.quizzical.common.helper.ConditionalHelper;
import com.ku.quizzical.common.helper.PrintHelper;

/**
 * Controller class for Quiz related operations
 */
@CrossOrigin
@RestController
public final class QuizController {
    // Instance Fields
    private QuizDatabaseService service;
    private UserRepository userRepository;
    private QuizRepository repository;

    // Constructor Method
    public QuizController(QuizDatabaseService service, UserRepository userRepository,
            QuizRepository repository) {
        super();
        this.service = service;
        this.userRepository = userRepository;
        this.repository = repository;
    }

    // CREATE Method
    // Saves a Quiz
    @PostMapping("/quizzes")
    public ResponseEntity<QuizDto> saveQuiz(@RequestBody QuizAddRequest quiz,
            @RequestHeader("Authorization") String authorizationHeader) {
        PrintHelper.printLine("1");
        PrintHelper.printEntry("title", quiz.title());
        PrintHelper.printEntry("description", quiz.description());
        PrintHelper.printEntry("picture", quiz.picture());
        PrintHelper.printEntry("thumbnail", quiz.thumbnail());
        PrintHelper.printEntry("subjectId", quiz.subjectId());
        PrintHelper.printEntry("userId", quiz.userId());
        User matchingUser = DatabaseValidationHelper.validateExistingResourceWithFallthrough(
                "Quiz User", quiz.userId(), this.userRepository::findById);
        PrintHelper.printLine("2");
        AuthorizationValidationHelper.validateAuthorization(authorizationHeader,
                matchingUser.getId());
        PrintHelper.printLine("3");
        return new ResponseEntity<QuizDto>(this.service.saveQuiz(quiz), HttpStatus.OK);
    }

    // READ Method
    // Gets all Quizzes
    @GetMapping("/quizzes")
    public List<QuizDto> getAllQuizzes() {
        return this.service.getAllQuizzes();
    }

    // READ Method
    // Gets all Quizzes as Posts from a Given User
    @GetMapping("/users/{userId}/quizzes")
    public List<QuizPostDto> getAllQuizzesFromUser(@PathVariable String userId) {
        return this.service.getAllQuizzesFromUser(userId);
    }

    // READ Method
    // Gets all Quizzes as Posts
    @GetMapping("/quizzes/posts")
    public List<QuizPostDto> getAllQuizzesAsPosts(
            @RequestParam("title") Optional<String> titleQuery,
            @RequestParam("limit") Optional<String> limit) {
        if (titleQuery.isPresent()) {
            return this.service.getAllQuizzesByTitleQuery(titleQuery.get());
        }
        return this.service.getAllQuizzesAsPosts(null, this.parseLimit(limit));
    }

    // READ Method
    // Gets all Quizzes as Posts from a Given Subject
    @GetMapping("/quizzes/posts/{subjectId}")
    public List<QuizPostDto> getAllQuizzesAsPosts(@PathVariable String subjectId,
            @RequestParam("limit") Optional<String> limit) {
        return this.service.getAllQuizzesAsPosts(subjectId, this.parseLimit(limit));
    }

    // READ Method
    // Gets a Quiz by its id
    @GetMapping("/quizzes/{id}")
    public ResponseEntity<QuizPostDto> getQuizById(@PathVariable String id) {
        return new ResponseEntity<QuizPostDto>(this.service.getQuizAsPost(id), HttpStatus.OK);
    }

    // UPDATE Method
    // Updates a Quiz
    @PatchMapping("/quizzes/{id}")
    public ResponseEntity<QuizDto> updateQuiz(@PathVariable String id,
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody QuizUpdateRequest quiz) {
        PrintHelper.printEntry("title", quiz.title());
        PrintHelper.printEntry("description", quiz.description());
        PrintHelper.printEntry("picture", quiz.picture());
        PrintHelper.printEntry("thumbnail", quiz.thumbnail());
        PrintHelper.printEntry("subjectId", quiz.subjectId());
        PrintHelper.printLine("1.0");
        Quiz matchingQuiz = DatabaseValidationHelper.validateExistingResourceWithFallthrough("Quiz",
                id, this.repository::findById);
        PrintHelper.printLine("2.0");
        AuthorizationValidationHelper.validateAuthorization(authorizationHeader,
                matchingQuiz.getUserId());
        PrintHelper.printLine("3.0");
        return new ResponseEntity<QuizDto>(this.service.updateQuiz(id, quiz), HttpStatus.OK);
    }

    // DELETE Method
    // Deletes a Quiz
    @DeleteMapping("/quizzes/{id}")
    public String deleteQuiz(@PathVariable String id,
            @RequestHeader("Authorization") String authorizationHeader) {
        Quiz matchingQuiz = DatabaseValidationHelper.validateExistingResourceWithFallthrough("Quiz",
                id, this.repository::findById);
        AuthorizationValidationHelper.validateAuthorization(authorizationHeader,
                matchingQuiz.getUserId());
        this.service.deleteQuiz(id);
        return "\"The Quiz with id \\\"" + id + "\\\" has been deleted.\"";
    }

    private int parseLimit(Optional<String> limit) {
        return ConditionalHelper.newTernaryOperation(limit.isPresent(),
                () -> Integer.parseInt(limit.get()), () -> Integer.MAX_VALUE);
    }
}
