package com.app.ecommerce.payment_service.exception;

import com.app.ecommerce.payment_service.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "500");
        return ResponseEntity.internalServerError().body(errorResponse);
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ErrorResponse> handlePaymentException(PaymentException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400");
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
