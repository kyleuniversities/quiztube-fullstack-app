package com.ku.quizzical.app.controller.quiz;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/quizzes")
public final class QuizController {
    // Instance Fields
    private QuizServiceImpl service;

    // Constructor Method
    public QuizController(QuizServiceImpl service) {
        super();
        this.service = service;
    }

    // CREATE Method
    // Saves a Quiz
    @PostMapping()
    public ResponseEntity<QuizDto> saveQuiz(@RequestBody QuizDto quiz) {
        return new ResponseEntity<QuizDto>(this.service.saveQuiz(quiz), HttpStatus.OK);
    }

    // READ Method
    // Gets all Quizs
    @GetMapping()
    public List<QuizDto> getAllQuizs() {
        return this.service.getAllQuizs();
    }

    // READ Method
    // Gets all Quizs by user id
    @GetMapping("/by/{userId}")
    public List<QuizDto> getAllQuizsByUserId(@PathVariable String userId) {
        return this.service.getAllQuizsByUserId(userId);
    }

    // READ Method
    // Gets a Quiz by its id
    @GetMapping("{id}")
    public ResponseEntity<QuizDto> getQuizById(@PathVariable String id) {
        return new ResponseEntity<QuizDto>(this.service.getQuizById(id), HttpStatus.OK);
    }

    // UPDATE Method
    // Updates a Quiz
    @PatchMapping("{id}")
    public ResponseEntity<QuizDto> updateQuiz(@PathVariable String id, @RequestBody QuizDto quiz) {
        return new ResponseEntity<QuizDto>(this.service.updateQuiz(quiz, id), HttpStatus.OK);
    }

    // DELETE Method
    // Deletes a Quiz
    @DeleteMapping("{id}")
    public String deleteQuiz(@PathVariable String id) {
        try {
            this.service.deleteQuiz(id);
        } catch (Exception e) {
            // Do Nothing
        }
        return "\"The Quiz with id \\\"" + id + "\\\" has been deleted.\"";
    }
}
