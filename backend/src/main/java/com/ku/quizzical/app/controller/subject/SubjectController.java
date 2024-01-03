package com.ku.quizzical.app.controller.subject;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    // CREATE Method
    // Saves a Subject
    @PostMapping("/subjects")
    public ResponseEntity<SubjectDto> saveSubject(@RequestBody SubjectDto subject) {
        return new ResponseEntity<SubjectDto>(this.service.saveSubject(subject), HttpStatus.OK);
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

    // UPDATE Method
    // Updates a Subject
    @PatchMapping("/subjects/{id}")
    public ResponseEntity<SubjectDto> updateSubject(@PathVariable String id,
            @RequestBody SubjectUpdateRequest subject) {
        return new ResponseEntity<SubjectDto>(this.service.updateSubject(id, subject),
                HttpStatus.OK);
    }

    // DELETE Method
    // Deletes a Subject
    @DeleteMapping("/subjects/{id}")
    public String deleteSubject(@PathVariable String id) {
        try {
            this.service.deleteSubject(id);
        } catch (Exception e) {
            // Do Nothing
        }
        return "\"The Subject with id \\\"" + id + "\\\" has been deleted.\"";
    }
}
