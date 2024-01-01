package com.ku.quizzical.app.controller.subject;

import java.util.List;

public interface SubjectDatabaseService {
    SubjectDto saveSubject(SubjectDto subject);

    List<SubjectDto> getAllSubjects();

    SubjectDto getSubject(String id);

    SubjectDto getSubjectByText(String text);

    SubjectDto updateSubject(String id, SubjectUpdateRequest subject);

    void deleteSubject(String id);
}
