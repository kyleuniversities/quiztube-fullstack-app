package com.ku.quizzical.app.controller.subject;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import com.ku.quizzical.app.helper.controller.SubjectTestHelper;
import com.ku.quizzical.app.helper.controller.TestHelper;
import com.ku.quizzical.app.util.TestRestTemplateContainer;
import com.ku.quizzical.common.helper.list.ListHelper;

/**
 * Test Class for Subject Controller
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class SubjectControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    // READ Method Test
    // Tests the Get Subjects Operation
    @Test
    void subjectsGetTest() throws Exception {
        // Set up template container
        TestRestTemplateContainer container =
                TestRestTemplateContainer.newInstance(this.restTemplate, this::toFullUrl);

        // Test GET
        List<SubjectDto> subjects = SubjectTestHelper.getAllSubjects(container);
        assertThat(subjects.size()).isGreaterThan(-1);
    }

    // READ Method Test
    // Tests the Get Subject by Id Operation
    @Test
    void subjectGetByIdTest() throws Exception {
        // Set up template container
        TestRestTemplateContainer container =
                TestRestTemplateContainer.newInstance(this.restTemplate, this::toFullUrl);

        // Test GET
        List<SubjectDto> subjects = SubjectTestHelper.getAllSubjects(container);
        ListHelper.forEach(subjects, (SubjectDto subject) -> {
            SubjectDto matchingSubject = SubjectTestHelper.getById(subject.id(), container);
            assertThat(matchingSubject.id()).isEqualTo(subject.id());
        });
    }

    // READ Method Test
    // Tests the Get Subject by text Operation
    @Test
    void subjectGetByTextTest() throws Exception {
        // Set up template container
        TestRestTemplateContainer container =
                TestRestTemplateContainer.newInstance(this.restTemplate, this::toFullUrl);

        // Test GET
        List<SubjectDto> subjects = SubjectTestHelper.getAllSubjects(container);
        ListHelper.forEach(subjects, (SubjectDto subject) -> {
            SubjectDto matchingSubject = SubjectTestHelper.getByText(subject.text(), container);
            assertThat(matchingSubject.id()).isEqualTo(subject.id());
        });
    }

    private String toFullUrl(String url) {
        return TestHelper.toFullUrl(this.port, url);
    }
}
