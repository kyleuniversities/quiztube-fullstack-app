package com.ku.quizzical.app.controller.comment;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public final class CommentController {
    // Instance Fields
    private CommentDatabaseService service;

    // Constructor Method
    public CommentController(CommentDatabaseService service) {
        super();
        this.service = service;
    }

    // CREATE Method
    // Saves a Comment
    @PostMapping("/quizzes/{quizId}/comments")
    public ResponseEntity<CommentDto> saveComment(@PathVariable String quizId,
            @RequestBody CommentDto comment) {
        return new ResponseEntity<CommentDto>(this.service.saveComment(quizId, comment),
                HttpStatus.OK);
    }

    // READ Method
    // Gets all Comments for a Quiz
    @GetMapping("/quizzes/{quizId}/comments")
    public List<CommentDto> getNumberOfCommentsForQuiz(@PathVariable String quizId) {
        return this.service.getAllCommentsByQuizId(quizId);
    }

    // READ Method
    // Get a Comment
    @GetMapping("/quizzes/{quizId}/comments/{id}")
    public ResponseEntity<CommentDto> commentExistsForQuiz(@PathVariable("quizId") String quizId,
            @PathVariable("id") String id) {
        return new ResponseEntity<CommentDto>(this.service.getComment(quizId, id), HttpStatus.OK);
    }

    // DELETE Method
    // Deletes a Comment
    @DeleteMapping("/quizzes/{quizId}/comments/{id}")
    public String deleteComment(@PathVariable("quizId") String quizId,
            @PathVariable("id") String id) {
        try {
            this.service.deleteComment(quizId, id);
        } catch (Exception e) {
            // Do Nothing
        }
        return "\"The Comment with id \\\"" + id + "\\\" has been deleted.\"";
    }
}
