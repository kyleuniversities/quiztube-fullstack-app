package com.ku.quizzical.app.controller.subject;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for Subject related operations
 */
@CrossOrigin
@RestController
public final class SubjectController {
    // Instance Fields
    private SubjectDatabaseService service;

    // Constructor Method
    public SubjectController(SubjectDatabaseService service) {
        super();
        this.service = service;
    }

    // READ Method
    // Gets all Subjects
    @GetMapping("/subjects")
    public List<SubjectDto> getAllSubjects() {
        return this.service.getAllSubjects();
    }

    // READ Method
    // Gets a Subject by its id
    @GetMapping("/subjects/{id}")
    public ResponseEntity<SubjectDto> getSubjectById(@PathVariable String id) {
        return new ResponseEntity<SubjectDto>(this.service.getSubject(id), HttpStatus.OK);
    }

    // READ Method
    // Gets a Subject by its subjectname
    @GetMapping("/subjects/text/{text}")
    public ResponseEntity<SubjectDto> getSubjectByTitle(@PathVariable String text) {
        return new ResponseEntity<SubjectDto>(this.service.getSubjectByText(text), HttpStatus.OK);
    }
}
