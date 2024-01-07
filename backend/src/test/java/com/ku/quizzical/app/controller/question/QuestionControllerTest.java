package com.ku.quizzical.app.controller.question;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import com.ku.quizzical.app.controller.quiz.QuizDto;
import com.ku.quizzical.app.controller.user.UserDto;
import com.ku.quizzical.app.helper.controller.QuestionTestHelper;
import com.ku.quizzical.app.helper.controller.TestHelper;
import com.ku.quizzical.app.util.TestRestTemplateContainer;
import com.ku.quizzical.common.helper.PrintHelper;
import com.ku.quizzical.common.util.function.QuadriConsumer;
import com.ku.quizzical.common.util.function.TriConsumer;

/**
 * Test Class for Question Controller
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class QuestionControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    // CREATE Method Test
    // Tests the Question Post Operation
    @Test
    void questionPostTest() throws Exception {
        PrintHelper.printLine("\n\n\n<<QUESTION POST TEST>>");
        this.testWithNewQuiz((QuizDto quiz, UserDto user, TestRestTemplateContainer container) -> {
            // Set up question add request
            QuestionAddRequest request =
                    QuestionTestHelper.newRandomQuestionAddRequest(quiz.id(), user.id(), container);

            // Test POST Method
            List<QuestionDto> questions1 = QuestionTestHelper.getAllQuestions(quiz.id(), container);
            QuestionDto question = QuestionTestHelper.saveQuestion(quiz.id(), request, container);
            List<QuestionDto> questions2 = QuestionTestHelper.getAllQuestions(quiz.id(), container);
            assertThat(questions1.size() + 1).isEqualTo(questions2.size());

            // Cleanup
            QuestionTestHelper.deleteQuestionById(question.id(), container);
        });
    }

    // READ Method Test
    // Tests the Get Questions from Quiz Operation
    @Test
    void questionsGetTest() throws Exception {
        PrintHelper.printLine("\n\n\n<<QUESTION GET ALL TEST>>");
        this.testWithNewQuiz((QuizDto quiz, UserDto user, TestRestTemplateContainer container) -> {
            List<QuestionDto> questions = QuestionTestHelper.getAllQuestions(quiz.id(), container);
            assertThat(questions.size()).isGreaterThan(-1);
        });
    }

    // READ Method Test
    // Tests the Get Question by Id Operation
    @Test
    void questionGetByIdTest() throws Exception {
        PrintHelper.printLine("\n\n\n<<QUESTION GET BY ID TEST>>");
        this.testWithNewQuestion((QuestionDto question, QuizDto quiz, UserDto user,
                TestRestTemplateContainer container) -> {
            QuestionDto matchingQuestion =
                    QuestionTestHelper.getById(quiz.id(), question.id(), container);
            assertThat(question.id()).isEqualTo(matchingQuestion.id());
        });
    }

    // UPDATE Method Test
    // Tests the Update Question by Id Operation
    @Test
    void updateQuestionById() throws Exception {
        PrintHelper.printLine("\n\n\n<<QUESTION UPDATE TEST>>");
        this.testWithNewQuestion((QuestionDto question, QuizDto quiz, UserDto user,
                TestRestTemplateContainer container) -> {
            // Set up update request
            QuestionUpdateRequest request =
                    QuestionTestHelper.newRandomQuestionUpdateRequest(container);

            // Test PATCH request
            QuestionTestHelper.updateQuestionById(quiz.id(), question.id(), request, container);
            QuestionDto updatedQuestion =
                    QuestionTestHelper.getById(quiz.id(), question.id(), container);
            assertThat(request.question()).isEqualTo(updatedQuestion.question());
        });
    }

    // DELETE Method Test
    // Tests the Delete Question by Id Operation
    // The testWithNewQuestion() method already does this
    @Test
    void deleteQuestionById() throws Exception {
        PrintHelper.printLine("\n\n\n<<QUESTION DELETE TEST>>");
        this.testWithNewQuestion((QuestionDto question, QuizDto quiz, UserDto user,
                TestRestTemplateContainer container) -> {
        });
    }

    // Method to test operation with a new question
    private void testWithNewQuestion(
            QuadriConsumer<QuestionDto, QuizDto, UserDto, TestRestTemplateContainer> action) {
        this.testWithNewQuiz((QuizDto quiz, UserDto user, TestRestTemplateContainer container) -> {
            // Set up question add request
            QuestionAddRequest request =
                    QuestionTestHelper.newRandomQuestionAddRequest(quiz.id(), user.id(), container);

            // Test POST Method
            QuestionDto question = QuestionTestHelper.saveQuestion(quiz.id(), request, container);
            action.accept(question, quiz, user, container);

            // Cleanup
            QuestionTestHelper.deleteQuestionById(question.id(), container);
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
