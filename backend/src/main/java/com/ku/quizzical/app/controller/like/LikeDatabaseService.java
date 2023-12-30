package com.ku.quizzical.app.controller.like;

import java.util.List;
import com.ku.quizzical.app.util.dto.IntegerDto;

public interface LikeDatabaseService {
    LikeDto saveLike(String userId, String quizId, LikeDto like);

    IntegerDto getNumberOfLikesForQuiz(String quizId);

    LikeDto likeExistsForQuiz(String userId, String quizId);

    List<LikeDto> getAllLikesByUserId(String userId);

    LikeDto getLike(String quizId, String id);

    void deleteLike(String quizId, String id);
}
