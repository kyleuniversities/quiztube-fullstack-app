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
import com.ku.quizzical.common.helper.OptionalHelper;

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
                User matchingUser = DatabaseValidationHelper
                                .validateExistingResourceWithFallthrough("Quiz User", quiz.userId(),
                                                this.userRepository::findById);
                AuthorizationValidationHelper.validateAuthorization(authorizationHeader,
                                matchingUser.getId());
                return new ResponseEntity<QuizDto>(this.service.saveQuiz(quiz), HttpStatus.OK);
        }

        // READ Method
        // Gets all Quizzes
        @GetMapping("/quizzes")
        public List<QuizDto> getAllQuizzes(@RequestParam("userId") Optional<String> userId,
                        @RequestParam("subjectId") Optional<String> subjectId,
                        @RequestParam("titleQuery") Optional<String> titleQuery,
                        @RequestParam("limit") Optional<String> limit) {
                return this.service.getAllQuizzes(OptionalHelper.getApparentValue(userId),
                                OptionalHelper.getApparentValue(subjectId),
                                OptionalHelper.getApparentValue(titleQuery),
                                this.parseLimit(limit));
        }

        // READ Method
        // Gets the quiz catalog
        @GetMapping("/quiz-catalog")
        public QuizCatalogDto getQuizCatalog(@RequestParam("limit") Optional<String> limit) {
                return this.service.getQuizCatalog(this.parseLimit(limit));
        }

        // READ Method
        // Gets all Quizzes from User
        @GetMapping("/users/{userId}/quizzes")
        public List<QuizDto> getAllQuizzesByUserId(@PathVariable String userId,
                        @RequestParam("limit") Optional<String> limit) {
                return this.service.getAllQuizzes(userId, null, null, this.parseLimit(limit));
        }

        // READ Method
        // Gets all Quizzes from Subject
        @GetMapping("/subjects/{subjectId}/quizzes")
        public List<QuizDto> getAllQuizzesBySubjectId(@PathVariable String subjectId,
                        @RequestParam("limit") Optional<String> limit) {
                return this.service.getAllQuizzes(null, subjectId, null, this.parseLimit(limit));
        }

        // READ Method
        // Gets a Quiz by its id
        @GetMapping("/quizzes/{id}")
        public ResponseEntity<QuizDto> getQuizById(@PathVariable String id) {
                return new ResponseEntity<QuizDto>(this.service.getQuiz(id), HttpStatus.OK);
        }

        // UPDATE Method
        // Updates a Quiz
        @PatchMapping("/quizzes/{id}")
        public ResponseEntity<QuizDto> updateQuiz(@PathVariable String id,
                        @RequestHeader("Authorization") String authorizationHeader,
                        @RequestBody QuizUpdateRequest quiz) {
                Quiz matchingQuiz =
                                DatabaseValidationHelper.validateExistingResourceWithFallthrough(
                                                "Quiz", id, this.repository::findById);
                AuthorizationValidationHelper.validateAuthorization(authorizationHeader,
                                matchingQuiz.getUserId());
                return new ResponseEntity<QuizDto>(this.service.updateQuiz(id, quiz),
                                HttpStatus.OK);
        }

        // DELETE Method
        // Deletes a Quiz
        @DeleteMapping("/quizzes/{id}")
        public String deleteQuiz(@PathVariable String id,
                        @RequestHeader("Authorization") String authorizationHeader) {
                Quiz matchingQuiz =
                                DatabaseValidationHelper.validateExistingResourceWithFallthrough(
                                                "Quiz", id, this.repository::findById);
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
