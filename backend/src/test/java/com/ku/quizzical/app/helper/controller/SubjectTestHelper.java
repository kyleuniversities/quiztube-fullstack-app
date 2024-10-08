package com.ku.quizzical.app.helper.controller;

import java.util.List;
import com.ku.quizzical.app.controller.subject.SubjectDto;
import com.ku.quizzical.app.controller.subject.SubjectDtoList;
import com.ku.quizzical.app.util.TestRestTemplateContainer;
import com.ku.quizzical.common.helper.list.ListHelper;

/**
 * Helper class for Subject Test Operations
 */
public class SubjectTestHelper {
    /**
     * Gets a list of all Subjects
     */
    public static SubjectDtoList getAllSubjects(TestRestTemplateContainer container) {
        return container.getList("/subjects", SubjectDtoList.class);
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
     * Returns a new Random subject
     */
    public static SubjectDto getRandomSubject(TestRestTemplateContainer container) {
        List<SubjectDto> subjects = SubjectTestHelper.getAllSubjects(container);
        return ListHelper.getRandomValue(subjects);
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private SubjectTestHelper() {
        super();
    }
}
