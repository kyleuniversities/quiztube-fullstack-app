package com.ku.quizzical.app.controller.quiz;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.function.BiConsumer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import com.ku.quizzical.app.controller.user.UserDto;
import com.ku.quizzical.app.helper.controller.QuizTestHelper;
import com.ku.quizzical.app.helper.controller.TestHelper;
import com.ku.quizzical.app.util.TestRestTemplateContainer;
import com.ku.quizzical.common.util.function.TriConsumer;

/**
 * Test Class for Quiz Controller
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class QuizControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    // CREATE Method Test
    // Tests the Quiz Post Operation
    @Test
    void quizPostTest() throws Exception {
        this.testWithNewUser((UserDto user, TestRestTemplateContainer container) -> {
            // Set up quiz add request
            QuizAddRequest request = QuizTestHelper.newRandomQuizAddRequest(user.id(), container);

            // Test POST Method
            List<QuizDto> quizzes1 = QuizTestHelper.getAllQuizzes(container);
            QuizDto quiz = QuizTestHelper.saveQuiz(request, container);
            List<QuizDto> quizzes2 = QuizTestHelper.getAllQuizzes(container);
            assertThat(quizzes1.size() + 1).isEqualTo(quizzes2.size());

            // Cleanup
            QuizTestHelper.deleteQuizById(quiz.id(), container);
        });
    }

    // READ Method Test
    // Tests the Get Quizzes Operation
    @Test
    void quizzesGetTest() throws Exception {
        // Set up template container
        TestRestTemplateContainer container =
                TestRestTemplateContainer.newInstance(this.restTemplate, this::toFullUrl);

        // Test GET
        List<QuizDto> quizzes = QuizTestHelper.getAllQuizzes(container);
        assertThat(quizzes.size()).isGreaterThan(-1);
    }

    // READ Method Test
    // Tests the Get Quiz by Id Operation
    @Test
    void quizGetByIdTest() throws Exception {
        this.testWithNewQuiz((QuizDto quiz, UserDto user, TestRestTemplateContainer container) -> {
            QuizDto matchingQuiz = QuizTestHelper.getById(quiz.id(), container);
            assertThat(quiz.id()).isEqualTo(matchingQuiz.id());
        });
    }

    // UPDATE Method Test
    // Tests the Update Quiz by Id Operation
    @Test
    void updateQuizById() throws Exception {
        this.testWithNewQuiz((QuizDto quiz, UserDto user, TestRestTemplateContainer container) -> {
            // Set up update request
            QuizUpdateRequest request = QuizTestHelper.newRandomQuizUpdateRequest(container);

            // Test PATCH request
            QuizTestHelper.updateQuizById(quiz.id(), request, container);
            QuizDto updatedQuiz = QuizTestHelper.getById(quiz.id(), container);
            assertThat(request.title()).isEqualTo(updatedQuiz.title());
        });
    }

    // DELETE Method Test
    // Tests the Delete Quiz by Id Operation
    // The testWithNewQuiz() method already does this
    @Test
    void deleteQuizById() throws Exception {
        this.testWithNewQuiz((QuizDto quiz, UserDto user, TestRestTemplateContainer container) -> {
        });
    }

    // Method to test operation with a registered user
    private void testWithNewQuiz(TriConsumer<QuizDto, UserDto, TestRestTemplateContainer> action) {
        this.testWithNewUser((UserDto user, TestRestTemplateContainer container) -> {
            // Create new quiz
            QuizAddRequest request = QuizTestHelper.newRandomQuizAddRequest(user.id(), container);
            QuizDto quiz = QuizTestHelper.saveQuiz(request, container);

            // Perform action
            action.accept(quiz, user, container);

            // Cleanup
            QuizTestHelper.deleteQuizById(quiz.id(), container);
        });
    }

    // Method to test operation with a registered user
    private void testWithNewUser(BiConsumer<UserDto, TestRestTemplateContainer> action) {
        TestHelper.testWithNewUser(this.restTemplate, this::toFullUrl, action);
    }

    // Method to return full requets URL
    private String toFullUrl(String url) {
        return TestHelper.toFullUrl(this.port, url);
    }
}
