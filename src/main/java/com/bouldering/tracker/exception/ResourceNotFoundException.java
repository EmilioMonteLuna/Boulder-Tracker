package com.bouldering.tracker.exception;
import java.time.LocalDateTime;


public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

// Error response structure
record ErrorResponse(int status, String message, LocalDateTime timestamp) {

}