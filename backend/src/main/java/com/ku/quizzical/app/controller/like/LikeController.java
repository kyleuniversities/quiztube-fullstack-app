package com.ku.quizzical.app.controller.like;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ku.quizzical.app.util.dto.BooleanDto;
import com.ku.quizzical.app.util.dto.IntegerDto;

@CrossOrigin
@RestController
public final class LikeController {
    // Instance Fields
    private LikeDatabaseService service;

    // Constructor Method
    public LikeController(LikeDatabaseService service) {
        super();
        this.service = service;
    }

    // CREATE Method
    // Saves a Like
    @PostMapping("/quizzes/{quizId}/likes")
    public ResponseEntity<LikeDto> saveLike(@PathVariable String quizId,
            @RequestBody LikeDto like) {
        return new ResponseEntity<LikeDto>(this.service.saveLike(like.userId(), quizId, like),
                HttpStatus.OK);
    }

    // READ Method
    // Gets all Likes for a Quiz
    @GetMapping("/quizzes/{quizId}/likes/count")
    public ResponseEntity<IntegerDto> getNumberOfLikesForQuiz(@PathVariable String quizId) {
        return new ResponseEntity<IntegerDto>(this.service.getNumberOfLikesForQuiz(quizId),
                HttpStatus.OK);
    }

    // READ Method
    // Checks if a user Liked a quiz
    @GetMapping("/quizzes/{quizId}/likes/i-liked-this/{userId}")
    public ResponseEntity<LikeDto> likeExistsForQuizForUser(@PathVariable("quizId") String quizId,
            @PathVariable("userId") String userId) {
        return new ResponseEntity<LikeDto>(this.service.likeExistsForQuiz(userId, quizId),
                HttpStatus.OK);
    }

    // READ Method
    // Get a Like
    @GetMapping("/quizzes/{quizId}/likes/{id}")
    public ResponseEntity<LikeDto> likeExistsForQuiz(@PathVariable("quizId") String quizId,
            @PathVariable("id") String id) {
        return new ResponseEntity<LikeDto>(this.service.getLike(quizId, id), HttpStatus.OK);
    }

    // DELETE Method
    // Deletes a Like
    @DeleteMapping("/quizzes/{quizId}/likes/{id}")
    public String deleteLike(@PathVariable("quizId") String quizId, @PathVariable("id") String id) {
        try {
            this.service.deleteLike(quizId, id);
        } catch (Exception e) {
            // Do Nothing
        }
        return "\"The Like with id \\\"" + id + "\\\" has been deleted.\"";
    }
}
