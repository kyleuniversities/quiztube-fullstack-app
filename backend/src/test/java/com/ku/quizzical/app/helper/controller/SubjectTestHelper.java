package com.ku.quizzical.app.helper.controller;

import java.util.List;
import com.ku.quizzical.app.controller.subject.SubjectDto;
import com.ku.quizzical.app.util.TestRestTemplateContainer;

/**
 * Helper class for Subject Test Operations
 */
public class SubjectTestHelper {
    /**
     * Gets a list of all Subjects
     */
    public static List<SubjectDto> getAllSubjects(TestRestTemplateContainer container) {
        return container.getList("/subjects");
    }

    /**
     * Gets a Subject by id
     */
    public static SubjectDto getById(String id, TestRestTemplateContainer container) {
        return container.getObject("/subjects/" + id, SubjectDto.class);
    }

    /**
     * Gets a Subject by text
     */
    public static SubjectDto getByText(String text, TestRestTemplateContainer container) {
        return container.getObject("/subjects/text/" + text, SubjectDto.class);
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private SubjectTestHelper() {
        super();
    }
}
