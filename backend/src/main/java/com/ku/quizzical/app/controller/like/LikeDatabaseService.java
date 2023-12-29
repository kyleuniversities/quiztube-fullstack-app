package com.ku.quizzical.app.controller.like;

import java.util.List;

public interface LikeDatabaseService {
    LikeDto saveLike(String userId, String quizId, LikeDto like);

    int getNumberOfLikesForQuiz(String quizId);

    boolean likeExistsForQuiz(String userId, String quizId);

    List<LikeDto> getAllLikesByQuizId(String quizId);

    List<LikeDto> getAllLikesByUserId(String userId);

    LikeDto getLikeById(String userId, String quizId, String id);

    void deleteLike(String userId, String quizId, String id);
}
