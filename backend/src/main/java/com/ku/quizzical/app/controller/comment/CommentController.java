package com.ku.quizzical.app.controller.comment;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for Comment related operations
 */
@CrossOrigin
@RestController
public final class CommentController {
    // Constructor Method
    public CommentController() {
        super();
        // Comment features have been reserved for a later version
    }
}
