package com.ku.quizzical.app.controller.question;

import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;
import com.ku.quizzical.app.controller.quiz.Quiz;
import com.ku.quizzical.app.controller.quiz.QuizRepository;
import com.ku.quizzical.app.helper.AuthorizationValidationHelper;
import com.ku.quizzical.app.helper.BackendValidationHelper;

@CrossOrigin
@RestController
public final class QuestionController {
        // Instance Fields
        private QuestionDatabaseService service;
        private QuizRepository quizRepository;
        private QuestionRepository repository;

        // Constructor Method
        public QuestionController(QuestionDatabaseService service, QuizRepository quizRepository,
                        QuestionRepository repository) {
                super();
                this.service = service;
                this.quizRepository = quizRepository;
                this.repository = repository;
        }

        // CREATE Method
        // Saves a Question
        @PostMapping("/quizzes/{quizId}/questions")
        public ResponseEntity<QuestionDto> saveQuestion(@PathVariable String quizId,
                        @RequestHeader("Authorization") String authorizationHeader,
                        @RequestBody QuestionDto question) {
                Quiz matchingQuiz = BackendValidationHelper.validateExistingResourceWithFallthrough(
                                "Question Quiz", question.quizId(), this.quizRepository::findById);
                AuthorizationValidationHelper.validateAuthorization(authorizationHeader,
                                matchingQuiz.getUserId());
                return new ResponseEntity<QuestionDto>(this.service.saveQuestion(quizId, question),
                                HttpStatus.OK);
        }

        // READ Method
        // Gets all Questions for a Quiz
        @GetMapping("/quizzes/{quizId}/questions")
        public List<QuestionDto> getAllQuestionsByQuizId(@PathVariable String quizId) {
                return this.service.getAllQuestionsByQuizId(quizId);
        }

        // READ Method
        // Gets a Question by its id
        @GetMapping("/quizzes/{quizId}/questions/{id}")
        public ResponseEntity<QuestionDto> getQuestionById(@PathVariable("quizId") String quizId,
                        @PathVariable("id") String id) {
                return new ResponseEntity<QuestionDto>(this.service.getQuestion(quizId, id),
                                HttpStatus.OK);
        }

        // UPDATE Method
        // Updates a Question
        @PatchMapping("/quizzes/{quizId}/questions/{id}")
        public ResponseEntity<QuestionDto> updateQuestion(@PathVariable("quizId") String quizId,
                        @PathVariable("id") String id,
                        @RequestHeader("Authorization") String authorizationHeader,
                        @RequestBody QuestionUpdateRequest question) {
                Question matchingQuestion =
                                BackendValidationHelper.validateExistingResourceWithFallthrough(
                                                "Question", id, this.repository::findById);
                AuthorizationValidationHelper.validateAuthorization(authorizationHeader,
                                matchingQuestion.getUserId());
                return new ResponseEntity<QuestionDto>(
                                this.service.updateQuestion(quizId, id, question), HttpStatus.OK);
        }

        // DELETE Method
        // Deletes a Question
        @DeleteMapping("/quizzes/{quizId}/questions/{id}")
        public String deleteQuestion(@PathVariable("quizId") String quizId,
                        @PathVariable("id") String id,
                        @RequestHeader("Authorization") String authorizationHeader) {
                Question matchingQuestion =
                                BackendValidationHelper.validateExistingResourceWithFallthrough(
                                                "Question", id, this.repository::findById);
                AuthorizationValidationHelper.validateAuthorization(authorizationHeader,
                                matchingQuestion.getUserId());
                this.service.deleteQuestion(quizId, id);
                return "\"The Question with id \\\"" + id + "\\\" has been deleted.\"";
        }
}
