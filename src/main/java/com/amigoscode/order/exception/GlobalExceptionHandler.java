package com.amigoscode.order.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

// TODO: 7 - Create a global exception handler:
//  - Annotate with @ControllerAdvice
//  - Add an @ExceptionHandler(OrderNotFoundException.class) method
//    that returns ResponseEntity<ApiError>
//  - Build an ApiError with the request path, exception message, 404 status, and current timestamp
//  Hint: inject HttpServletRequest to get the request path

// TODO: 8 - Add another @ExceptionHandler for MethodArgumentNotValidException:
//  - Extract field errors from the exception
//  - Build a message string with all field errors: "field: message; field: message"
//  - Return a 400 Bad Request response with ApiError
//  Hint: ex.getBindingResult().getFieldErrors()
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ApiError> handleOrderNotFoundException(
            HttpServletRequest request,
            OrderNotFoundException e
    ) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(
            HttpServletRequest request,
            MethodArgumentNotValidException e
    ) {
        String errors = e.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(";"));

        ApiError apiError = new ApiError(
                request.getRequestURI(),
                errors,
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);

    }

}
