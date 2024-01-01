package com.ku.quizzical.app.controller.like;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.ku.quizzical.app.controller.user.User;
import com.ku.quizzical.app.controller.user.UserRepository;
import com.ku.quizzical.app.helper.AuthorizationValidationHelper;
import com.ku.quizzical.app.helper.BackendValidationHelper;
import com.ku.quizzical.app.util.dto.IntegerDto;

@CrossOrigin
@RestController
public final class LikeController {
    // Instance Fields
    private LikeDatabaseService service;
    private UserRepository userRepository;
    private LikeRepository repository;

    // Constructor Method
    public LikeController(LikeDatabaseService service, UserRepository userRepository,
            LikeRepository repository) {
        super();
        this.service = service;
        this.userRepository = userRepository;
        this.repository = repository;
    }

    // CREATE Method
    // Saves a Like
    @PostMapping("/quizzes/{quizId}/likes")
    public ResponseEntity<LikeDto> saveLike(@PathVariable String quizId,
            @RequestHeader("Authorization") String authorizationHeader, @RequestBody LikeDto like) {
        User matchingUser = BackendValidationHelper.validateExistingResourceWithFallthrough(
                "Like User", like.userId(), this.userRepository::findById);
        AuthorizationValidationHelper.validateAuthorization(authorizationHeader,
                matchingUser.getId());
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
            @PathVariable("userId") String userId,
            @RequestHeader("Authorization") String authorizationHeader) {
        User matchingUser = BackendValidationHelper.validateExistingResourceWithFallthrough(
                "Like User", userId, this.userRepository::findById);
        AuthorizationValidationHelper.validateAuthorization(authorizationHeader,
                matchingUser.getId());
        return new ResponseEntity<LikeDto>(this.service.likeExistsForQuiz(userId, quizId),
                HttpStatus.OK);
    }

    // DELETE Method
    // Deletes a Like
    @DeleteMapping("/quizzes/{quizId}/likes/{id}")
    public String deleteLike(@PathVariable("quizId") String quizId, @PathVariable("id") String id,
            @RequestHeader("Authorization") String authorizationHeader) {
        Like matchingLike = BackendValidationHelper.validateExistingResourceWithFallthrough("Like",
                id, this.repository::findById);
        AuthorizationValidationHelper.validateAuthorization(authorizationHeader,
                matchingLike.getUserId());
        this.service.deleteLike(quizId, id);
        return "\"The Like with id \\\"" + id + "\\\" has been deleted.\"";
    }
}
