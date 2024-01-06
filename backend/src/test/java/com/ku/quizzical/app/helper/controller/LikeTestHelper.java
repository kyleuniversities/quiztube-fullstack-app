package com.ku.quizzical.app.helper.controller;

import org.json.JSONObject;
import com.ku.quizzical.app.controller.like.LikeAddRequest;
import com.ku.quizzical.app.controller.like.LikeDto;
import com.ku.quizzical.app.helper.JsonHelper;
import com.ku.quizzical.app.util.TestRestTemplateContainer;
import com.ku.quizzical.app.util.dto.IntegerDto;

/**
 * Helper class for Like Test Operations
 */
public class LikeTestHelper {
    /**
     * Checks if a like exists
     */
    public static LikeDto deleteLike(String quizId, String id,
            TestRestTemplateContainer container) {
        return container.getObject(String.format("/quizzes/%s/likes/%s", quizId, id),
                LikeDto.class);
    }

    /**
     * Gets the number of likes for a Quiz
     */
    public static int getNumberOfLikesForQuiz(String quizId, TestRestTemplateContainer container) {
        return container
                .getObject(String.format("/quizzes/%s/likes/count", quizId), IntegerDto.class)
                .value();
    }

    /**
     * Checks if a like exists
     */
    public static LikeDto likeExistsForQuiz(String userId, String quizId,
            TestRestTemplateContainer container) {
        return container.getObject(
                String.format("/quizzes/%s/likes/i-liked-this/%s", quizId, userId), LikeDto.class);
    }

    /**
     * Returns a new like add request
     */
    public static LikeAddRequest newLikeAddRequest(String userId, String quizId,
            TestRestTemplateContainer container) {
        return new LikeAddRequest(quizId, userId);
    }

    /**
     * Saves like
     */
    public static LikeDto saveLike(LikeAddRequest request, TestRestTemplateContainer container) {
        JSONObject object = JsonHelper.newJsonObject();
        JsonHelper.put(object, "userId", request.userId());
        JsonHelper.put(object, "quizId", request.quizId());
        return container.post(String.format("/quizzes/%s/likes", request.quizId()), object,
                LikeDto.class);
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private LikeTestHelper() {
        super();
    }
}
