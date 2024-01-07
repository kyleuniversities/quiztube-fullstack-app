package com.ku.quizzical.app.controller.like;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import com.ku.quizzical.app.controller.quiz.QuizDto;
import com.ku.quizzical.app.controller.user.UserDto;
import com.ku.quizzical.app.helper.controller.LikeTestHelper;
import com.ku.quizzical.app.helper.controller.TestHelper;
import com.ku.quizzical.app.util.TestRestTemplateContainer;
import com.ku.quizzical.common.helper.PrintHelper;
import com.ku.quizzical.common.util.function.QuadriConsumer;
import com.ku.quizzical.common.util.function.TriConsumer;

/**
 * Test Class for Like Controller
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class LikeControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    // CREATE Method Test
    // Tests the Like Post Operation
    @Test
    void likePostTest() throws Exception {
        PrintHelper.printLine("\n\n\n<<LIKE POST TEST>>");
        this.testWithNewQuiz((QuizDto quiz, UserDto user, TestRestTemplateContainer container) -> {
            // Set up Like Add Request
            LikeAddRequest request =
                    LikeTestHelper.newLikeAddRequest(user.id(), quiz.id(), container);

            // Test POST Method
            int likes1 = LikeTestHelper.getNumberOfLikesForQuiz(quiz.id(), container);
            LikeDto like = LikeTestHelper.saveLike(request, container);
            int likes2 = LikeTestHelper.getNumberOfLikesForQuiz(quiz.id(), container);
            assertThat(likes1 + 1).isEqualTo(likes2);

            // Cleanup
            LikeTestHelper.deleteLike(quiz.id(), like.id(), container);
        });
    }

    // READ Method Test
    // Tests the Number of Likes from Quiz Operation
    @Test
    void likesGetTest() throws Exception {
        PrintHelper.printLine("\n\n\n<<LIKE GET ALL TEST>>");
        this.testWithNewQuiz((QuizDto quiz, UserDto user, TestRestTemplateContainer container) -> {
            int likes = LikeTestHelper.getNumberOfLikesForQuiz(quiz.id(), container);
            assertThat(likes).isGreaterThan(-1);
        });
    }

    // READ Method Test
    // Tests the Get Like by Id Operation
    @Test
    void likeGetByIdTest() throws Exception {
        PrintHelper.printLine("\n\n\n<<LIKE GET BY ID TEST>>");
        this.testWithNewLike(
                (LikeDto like, QuizDto quiz, UserDto user, TestRestTemplateContainer container) -> {
                    LikeDto matchingLike =
                            LikeTestHelper.likeExistsForQuiz(user.id(), quiz.id(), container);
                    assertThat(like.id()).isEqualTo(matchingLike.id());
                });
    }

    // DELETE Method Test
    // Tests the Delete Like by Id Operation
    // The testWithNewLike() method already does this
    @Test
    void deleteLikeById() throws Exception {
        PrintHelper.printLine("\n\n\n<<LIKE DELETE TEST>>");
        this.testWithNewLike(
                (LikeDto like, QuizDto quiz, UserDto user, TestRestTemplateContainer container) -> {
                });
    }

    // Method to test operation with a new like
    private void testWithNewLike(
            QuadriConsumer<LikeDto, QuizDto, UserDto, TestRestTemplateContainer> action) {
        this.testWithNewQuiz((QuizDto quiz, UserDto user, TestRestTemplateContainer container) -> {
            // Set up like add request
            LikeAddRequest request =
                    LikeTestHelper.newLikeAddRequest(user.id(), quiz.id(), container);

            // Test POST Method
            LikeDto like = LikeTestHelper.saveLike(request, container);
            action.accept(like, quiz, user, container);

            // Cleanup
            LikeTestHelper.deleteLike(quiz.id(), like.id(), container);
        });
    }

    // Method to test operation with a new quiz
    private void testWithNewQuiz(TriConsumer<QuizDto, UserDto, TestRestTemplateContainer> action) {
        TestHelper.testWithNewQuiz(this.restTemplate, this::toFullUrl, action);
    }

    // Method to return full requets URL
    private String toFullUrl(String url) {
        return TestHelper.toFullUrl(this.port, url);
    }
}
