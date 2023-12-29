package com.ku.quizzical.app.controller.subject;

import java.util.List;
import java.util.Optional;

public interface SubjectService {
    void saveSubject(SubjectDto subject);

    List<SubjectDto> getAllSubjects();

    Optional<SubjectDto> getSubject(String id);

    Optional<SubjectDto> getSubjectByText(String text);

    void updateSubject(String id, SubjectDto subject);

    void deleteSubject(String id);
}
