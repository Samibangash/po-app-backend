package com.poapp.exception;

import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.poapp.common.ApiResponse;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle HttpMessageNotReadableException (already defined)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ErrorResponse<String> errorResponse = new ErrorResponse<>(
            "Bad Request",
            HttpStatus.BAD_REQUEST.value(),
            "HttpMessageNotReadableException",
            ex.getMessage(),
            null,
            false
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    // Handle SignatureException for JWT errors
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponse<String>> handleSignatureException(SignatureException ex) {
        ErrorResponse<String> errorResponse = new ErrorResponse<>(
            "Unauthorized",
            HttpStatus.UNAUTHORIZED.value(),
            "SignatureException",
            ex.getMessage(),
            null,  // Data field is null in this case
            false
        );

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(errorResponse);
    }

    // Generic handler for all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<String>> handleGenericException(Exception ex) {
        ErrorResponse<String> errorResponse = new ErrorResponse<>(
            "Internal Server Error",
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            ex.getClass().getSimpleName(),
            ex.getMessage(),
            null,
            false
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }


    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleNoResourceFoundException(NoResourceFoundException ex) {
        ApiResponse<String> response = new ApiResponse<>(
            "Resource Not Found",
            HttpStatus.NOT_FOUND.value(),
            "NoResourceFoundException",
            ex.getMessage(),
            null,
            false
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
