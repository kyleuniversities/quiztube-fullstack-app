package com.ku.quizzical.app.controller.integration;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import com.ku.quizzical.app.controller.like.LikeAddRequest;
import com.ku.quizzical.app.controller.like.LikeDto;
import com.ku.quizzical.app.controller.question.Question;
import com.ku.quizzical.app.controller.quiz.Quiz;
import com.ku.quizzical.app.controller.user.User;
import com.ku.quizzical.app.controller.user.UserDto;
import com.ku.quizzical.app.helper.NullValueHelper;
import com.ku.quizzical.app.helper.controller.LikeTestHelper;
import com.ku.quizzical.app.helper.controller.TestHelper;
import com.ku.quizzical.app.util.TestRestTemplateContainer;
import com.ku.quizzical.common.helper.ListHelper;

/**
 * Test Class for integration Controller
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class IntegrationControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Creates a List of Realized Users and deletes them
     */
    @Test
    void integrationRealizedUsersTest() throws Exception {
        this.testWithNewRealizedUsers((List<User> users) -> {
            assertThat(users.size()).isGreaterThan(0);
            ListHelper.forEach(users, (User user) -> {
                List<Quiz> quizzes = user.getQuizzes();
                assertThat(quizzes.size()).isGreaterThan(0);
                ListHelper.forEach(quizzes,
                        (Quiz quiz) -> assertThat(quiz.getQuestions().size()).isGreaterThan(0));
            });
        });
    }

    /**
     * Test Liking and Unliking Various Quizzes
     */
    @Test
    void integrationLikesTest() throws Exception {
        this.testWithNewRealizedUsers((List<User> users) -> {
            this.testWithNewUser((UserDto agentUser, TestRestTemplateContainer container) -> {
                User targetUser = ListHelper.getRandomValue(users);
                Quiz targetQuiz = ListHelper.getRandomValue(targetUser.getQuizzes());
                LikeAddRequest request = LikeTestHelper.newLikeAddRequest(agentUser.id(),
                        targetQuiz.getId(), container);
                LikeDto matchingLike1 = LikeTestHelper.likeExistsForQuiz(agentUser.id(),
                        targetQuiz.getId(), container);
                assertThat(matchingLike1.id()).isEqualTo(NullValueHelper.NULL_TEXT);
                LikeDto like = LikeTestHelper.saveLike(request, container);
                LikeDto matchingLike2 = LikeTestHelper.likeExistsForQuiz(agentUser.id(),
                        targetQuiz.getId(), container);
                assertThat(matchingLike2.id()).isEqualTo(like.id());
                LikeTestHelper.deleteLike(targetQuiz.getId(), like.id(), container);
                LikeDto matchingLike3 = LikeTestHelper.likeExistsForQuiz(agentUser.id(),
                        targetQuiz.getId(), container);
                assertThat(matchingLike3.id()).isEqualTo(NullValueHelper.NULL_TEXT);
            });
        });
    }

    // Method to test operation with a registered user
    private void testWithNewUser(BiConsumer<UserDto, TestRestTemplateContainer> action) {
        TestHelper.testWithNewUser(this.restTemplate, this::toFullUrl, action);
    }

    // Method to test operation with new realized users
    private void testWithNewRealizedUsers(Consumer<List<User>> action) {
        TestHelper.testWithNewRealizedUsers(this.restTemplate, this::toFullUrl, action);
    }

    // Method to return full requets URL
    private String toFullUrl(String url) {
        return TestHelper.toFullUrl(this.port, url);
    }
}
