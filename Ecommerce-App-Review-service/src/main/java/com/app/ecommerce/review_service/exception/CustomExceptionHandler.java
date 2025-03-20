package com.app.ecommerce.review_service.exception;

import com.app.ecommerce.review_service.dto.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ReviewException.class)
    public ResponseEntity<ErrorResponse> handleReviewException(ReviewException e) {
        ErrorResponse errorResponse=new ErrorResponse(e.getMessage(), "400");
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse errorResponse=new ErrorResponse(e.getMessage(), "500");
        return ResponseEntity.internalServerError().body(errorResponse);
    }
}
