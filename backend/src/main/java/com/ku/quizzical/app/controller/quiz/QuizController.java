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
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public final class QuizController {
    // Instance Fields
    private QuizDatabaseService service;

    // Constructor Method
    public QuizController(QuizDatabaseService service) {
        super();
        this.service = service;
    }

    // CREATE Method
    // Saves a Quiz
    @PostMapping("/quizzes")
    public ResponseEntity<QuizDto> saveQuiz(@RequestBody QuizDto quiz) {
        return new ResponseEntity<QuizDto>(this.service.saveQuiz(quiz), HttpStatus.OK);
    }

    // READ Method
    // Gets all Quizzes
    @GetMapping("/quizzes")
    public List<QuizDto> getAllQuizzes() {
        return this.service.getAllQuizzes();
    }

    // Gets all Quizzes as Posts from a Given User
    @GetMapping("/users/{userId}/quizzes")
    public List<QuizPostDto> getAllQuizzesFromUser(@PathVariable String userId) {
        return this.service.getAllQuizzesFromUser(userId);
    }

    // Gets all Quizzes as Posts
    @GetMapping("/quizzes/posts")
    public List<QuizPostDto> getAllQuizzesAsPosts() {
        return this.service.getAllQuizzesAsPosts(null);
    }

    // Gets all Quizzes as Posts from a Given Subject
    @GetMapping("/quizzes/posts/{subjectId}")
    public List<QuizPostDto> getAllQuizzesAsPosts(@PathVariable String subjectId) {
        return this.service.getAllQuizzesAsPosts(subjectId);
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
            @RequestBody QuizUpdateRequest quiz) {
        return new ResponseEntity<QuizDto>(this.service.updateQuiz(id, quiz), HttpStatus.OK);
    }

    // DELETE Method
    // Deletes a Quiz
    @DeleteMapping("/quizzes/{id}")
    public String deleteQuiz(@PathVariable String id) {
        try {
            this.service.deleteQuiz(id);
        } catch (Exception e) {
            // Do Nothing
        }
        return "\"The Quiz with id \\\"" + id + "\\\" has been deleted.\"";
    }
}
