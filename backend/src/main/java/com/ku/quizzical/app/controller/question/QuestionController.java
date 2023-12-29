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
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public final class QuestionController {
    // Instance Fields
    private QuestionDatabaseService service;

    // Constructor Method
    public QuestionController(QuestionDatabaseService service) {
        super();
        this.service = service;
    }

    // CREATE Method
    // Saves a Question
    @PostMapping("/quizzes/{quizId}/questions")
    public ResponseEntity<QuestionDto> saveQuestion(@PathVariable String quizId,
            @RequestBody QuestionDto question) {
        return new ResponseEntity<QuestionDto>(this.service.saveQuestion(quizId, question),
                HttpStatus.OK);
    }

    // READ Method
    // Gets all Questions for a Quiz
    @GetMapping("/quizzes/{quizId}")
    public List<QuestionDto> getAllQuestionsByQuizId(@PathVariable String quizId) {
        return this.service.getAllQuestionsByQuizId(quizId);
    }

    // READ Method
    // Gets a Question by its id
    @GetMapping("/quizzes/{quizId}/questions/{id}")
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable("quizId") String quizId,
            @PathVariable("id") String id) {
        return new ResponseEntity<QuestionDto>(this.service.getQuestion(quizId, id), HttpStatus.OK);
    }

    // UPDATE Method
    // Updates a Question
    @PatchMapping("/quizzes/{quizId}/questions/{id}")
    public ResponseEntity<QuestionDto> updateQuestion(@PathVariable("quizId") String quizId,
            @PathVariable("id") String id, @RequestBody QuestionUpdateRequest question) {
        return new ResponseEntity<QuestionDto>(this.service.updateQuestion(quizId, id, question),
                HttpStatus.OK);
    }

    // DELETE Method
    // Deletes a Question
    @DeleteMapping("/quizzes/{quizId}/questions/{id}")
    public String deleteQuestion(@PathVariable("quizId") String quizId,
            @PathVariable("id") String id) {
        try {
            this.service.deleteQuestion(quizId, id);
        } catch (Exception e) {
            // Do Nothing
        }
        return "\"The Question with id \\\"" + id + "\\\" has been deleted.\"";
    }
}
