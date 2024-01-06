package com.ku.quizzical.app.helper.controller;

import java.util.List;
import org.json.JSONObject;
import com.ku.quizzical.app.controller.question.QuestionAddRequest;
import com.ku.quizzical.app.controller.question.QuestionDto;
import com.ku.quizzical.app.controller.question.QuestionDtoList;
import com.ku.quizzical.app.controller.question.QuestionUpdateRequest;
import com.ku.quizzical.app.helper.JsonHelper;
import com.ku.quizzical.app.util.TestRestTemplateContainer;
import com.ku.quizzical.common.helper.RandomHelper;
import com.ku.quizzical.common.helper.number.IndexHelper;

/**
 * Helper class for Question Test Operations
 */
public class QuestionTestHelper {
    /**
     * Deletes question by id
     */
    public static void deleteQuestionById(String id, TestRestTemplateContainer container) {
        container.delete("/questions/" + id);
    }

    /**
     * Gets a list of all Questions
     */
    public static List<QuestionDto> getAllQuestions(String quizId,
            TestRestTemplateContainer container) {
        return container.getList(String.format("/quizzes/%s/questions", quizId),
                QuestionDtoList.class);
    }

    /**
     * Gets a Question by id
     */
    public static QuestionDto getById(String quizId, String id,
            TestRestTemplateContainer container) {
        return container.getObject(String.format("/quizzes/%s/questions/%s", quizId, id),
                QuestionDto.class);
    }

    /**
     * Creates a random question add request
     */
    public static QuestionAddRequest newRandomQuestionAddRequest(String quizId, String userId,
            TestRestTemplateContainer container) {
        String tag = IndexHelper.toIndexText(RandomHelper.nextInt(1000000), 6);
        String question = "Question " + tag;
        String answer = "Question Answer " + tag;
        int numberOfMilliseconds = 10000;
        return new QuestionAddRequest(question, answer, numberOfMilliseconds, quizId, userId);
    }

    /**
     * Creates a random question update request
     */
    public static QuestionUpdateRequest newRandomQuestionUpdateRequest(
            TestRestTemplateContainer container) {
        String tag = IndexHelper.toIndexText(RandomHelper.nextInt(1000000), 6);
        String question = "Question " + tag;
        String answer = "Question Answer " + tag;
        int numberOfMilliseconds = 10000;
        return new QuestionUpdateRequest(question, answer, numberOfMilliseconds);
    }

    /**
     * Saves question
     */
    public static QuestionDto saveQuestion(String quizId, QuestionAddRequest request,
            TestRestTemplateContainer container) {
        JSONObject object = JsonHelper.newJsonObject();
        JsonHelper.put(object, "question", request.question());
        JsonHelper.put(object, "answer", request.answer());
        JsonHelper.put(object, "numberOfMilliseconds", request.numberOfMilliseconds() + "");
        JsonHelper.put(object, "userId", request.userId());
        JsonHelper.put(object, "quizId", request.quizId());
        return container.post(String.format("/quizzes/%s/questions", quizId), object,
                QuestionDto.class);
    }

    /**
     * Updates a question
     */
    public static QuestionDto updateQuestionById(String quizId, String id,
            QuestionUpdateRequest request, TestRestTemplateContainer container) {
        JSONObject object = JsonHelper.newJsonObject();
        JsonHelper.put(object, "question", request.question());
        JsonHelper.put(object, "answer", request.answer());
        JsonHelper.put(object, "numberOfMilliseconds", request.numberOfMilliseconds() + "");
        return container.patch(String.format("/quizzes/%s/questions/%s", quizId, id), object,
                QuestionDto.class);
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private QuestionTestHelper() {
        super();
    }
}
