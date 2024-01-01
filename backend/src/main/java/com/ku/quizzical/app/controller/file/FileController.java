package com.ku.quizzical.app.controller.file;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ku.quizzical.app.s3.S3StorageService;
import com.ku.quizzical.common.helper.string.StringReplacementHelper;

@CrossOrigin
@RestController
@RequestMapping("/file")
public final class FileController {
    // Instance Fields
    private final S3StorageService s3StorageService;

    // Constructor Method
    public FileController(S3StorageService s3StorageService) {
        this.s3StorageService = s3StorageService;
    }

    // READ Method
    // Downloads an image file
    @GetMapping(value = "/image/{key}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getFileImage(@PathVariable String key) {
        if (key == null || key.equals("null")) {
            return new byte[] {};
        }
        String decodedKey = StringReplacementHelper.replace(key, "__", "/");
        return this.s3StorageService.getObject(decodedKey);
    }
}
