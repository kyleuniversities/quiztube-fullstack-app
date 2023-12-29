package com.ku.quizzical.app.controller.subject;

import java.util.List;

public interface SubjectDatabaseService {
    void saveSubject(SubjectDto subject);

    List<SubjectDto> getAllSubjects();

    SubjectDto getSubject(String id);

    SubjectDto getSubjectByText(String text);

    void updateSubject(String id, SubjectDto subject);

    void deleteSubject(String id);
}
