package com.ku.quizzical.app.helper.controller;

import java.util.List;
import org.json.JSONObject;
import com.ku.quizzical.app.controller.quiz.QuizAddRequest;
import com.ku.quizzical.app.controller.quiz.QuizDto;
import com.ku.quizzical.app.controller.quiz.QuizDtoList;
import com.ku.quizzical.app.controller.quiz.QuizUpdateRequest;
import com.ku.quizzical.app.helper.JsonHelper;
import com.ku.quizzical.app.util.TestRestTemplateContainer;
import com.ku.quizzical.common.helper.RandomHelper;
import com.ku.quizzical.common.helper.number.IndexHelper;

/**
 * Helper class for Quiz Test Operations
 */
public class QuizTestHelper {
    /**
     * Deletes quiz by id
     */
    public static void deleteQuizById(String id, TestRestTemplateContainer container) {
        container.delete("/quizzes/" + id);
    }

    /**
     * Gets a list of all Quizzes
     */
    public static List<QuizDto> getAllQuizzes(TestRestTemplateContainer container) {
        return container.getList("/quizzes", QuizDtoList.class);
    }

    /**
     * Gets a Quiz by id
     */
    public static QuizDto getById(String id, TestRestTemplateContainer container) {
        return container.getObject("/quizzes/" + id, QuizDto.class);
    }

    /**
     * Creates a random quiz add request
     */
    public static QuizAddRequest newRandomQuizAddRequest(String userId,
            TestRestTemplateContainer container) {
        String tag = IndexHelper.toIndexText(RandomHelper.nextInt(1000000), 6);
        String title = "Quiz " + tag;
        String description = "Quiz Description " + tag;
        String picture = "static/quiz/quiz-picture-t.png";
        String thumbnail = "static/quiz/quiz-picture-t_T.png";
        String subjectId = SubjectTestHelper.getRandomSubject(container).id();
        return new QuizAddRequest(title, description, picture, thumbnail, userId, subjectId);
    }

    /**
     * Creates a random quiz update request
     */
    public static QuizUpdateRequest newRandomQuizUpdateRequest(
            TestRestTemplateContainer container) {
        String tag = IndexHelper.toIndexText(RandomHelper.nextInt(1000000), 6);
        String title = "Quiz " + tag;
        String description = "Quiz Description " + tag;
        String picture = "static/quiz/quiz-picture-t.png";
        String thumbnail = "static/quiz/quiz-picture-t_T.png";
        String subjectId = SubjectTestHelper.getRandomSubject(container).id();
        return new QuizUpdateRequest(title, description, picture, thumbnail, subjectId);
    }

    /**
     * Saves quiz
     */
    public static QuizDto saveQuiz(QuizAddRequest request, TestRestTemplateContainer container) {
        JSONObject object = JsonHelper.newJsonObject();
        JsonHelper.put(object, "title", request.title());
        JsonHelper.put(object, "description", request.description());
        JsonHelper.put(object, "picture", request.picture());
        JsonHelper.put(object, "thumbnail", request.thumbnail());
        JsonHelper.put(object, "userId", request.userId());
        JsonHelper.put(object, "subjectId", request.thumbnail());
        return container.post("/quizzes", object, QuizDto.class);
    }

    /**
     * Updates a quiz
     */
    public static QuizDto updateQuizById(String id, QuizUpdateRequest request,
            TestRestTemplateContainer container) {
        JSONObject object = JsonHelper.newJsonObject();
        JsonHelper.put(object, "title", request.title());
        JsonHelper.put(object, "description", request.description());
        JsonHelper.put(object, "picture", request.picture());
        JsonHelper.put(object, "thumbnail", request.thumbnail());
        JsonHelper.put(object, "subjectId", request.thumbnail());
        return container.post("/quizzes/" + id, object, QuizDto.class);
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private QuizTestHelper() {
        super();
    }
}
