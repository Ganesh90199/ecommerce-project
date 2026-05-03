package com.ganesh.ecommerce.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleNotFound(ResourceNotFoundException e) {
	    return ResponseEntity.status(404).body(
	            Map.of("error", e.getMessage())
	    );
	}

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.status(500).body(
                Map.of("error", "Something went wrong", "message", e.getMessage())
        );
    }
}