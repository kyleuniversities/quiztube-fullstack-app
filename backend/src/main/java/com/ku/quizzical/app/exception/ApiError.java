package com.ku.quizzical.app.exception;

import java.time.LocalDateTime;

public final class ApiError {
    // Instance Fields
    private String path;
    private String message;
    private int status;
    private LocalDateTime time;

    // New Instance Method
    public static ApiError newInstance(String path, String message, int status, LocalDateTime time) {
        return new ApiError(path, message, status, time);
    }

    // Constructor Method
    private ApiError(String path, String message, int status, LocalDateTime time) {
        super();
        this.path = path;
        this.message = message;
        this.status = status;
        this.time = time;
    }

    // Accessor Methods
    public String getPath() {
        return this.path;
    }

    public String getMessage() {
        return this.message;
    }

    public int getStatus() {
        return this.status;
    }

    public LocalDateTime getTime() {
        return this.time;
    }
}