package com.ku.quizzical.app.controller.file;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ku.quizzical.app.controller.auth.AuthenticationRequest;
import com.ku.quizzical.app.controller.auth.AuthenticationResponse;
import com.ku.quizzical.app.controller.auth.AuthenticationService;
import com.ku.quizzical.app.s3.S3StorageService;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public final class FileController {
    // Instance Fields
    private final S3StorageService s3StorageService;

    // Constructor Method
    public FileController(S3StorageService s3StorageService) {
        this.s3StorageService = s3StorageService;
    }

    // READ Method
    // Downloads an image file
    @GetMapping(value = "file/image/{key}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getFileImage(@PathVariable String key) {
        return this.s3StorageService.getObject(key);
    }
}
