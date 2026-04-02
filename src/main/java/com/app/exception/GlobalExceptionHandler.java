package com.app.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BookNotFoundException.class)
  public ResponseEntity<ApiError> handleNotFound(BookNotFoundException ex, HttpServletRequest request) {
    return error(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI(), null);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
    Map<String, String> fields = new LinkedHashMap<>();
    for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
      fields.putIfAbsent(fe.getField(), fe.getDefaultMessage());
    }
    return error(HttpStatus.BAD_REQUEST, "Validation failed", request.getRequestURI(), fields);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiError> handleBadJson(HttpMessageNotReadableException ex, HttpServletRequest request) {
    return error(HttpStatus.BAD_REQUEST, "Malformed JSON request", request.getRequestURI(), null);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ApiError> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
    String param = ex.getName();
    return error(HttpStatus.BAD_REQUEST, "Invalid value for parameter '" + param + "'", request.getRequestURI(), null);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ApiError> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest request) {
    return error(HttpStatus.CONFLICT, "Data integrity violation", request.getRequestURI(), null);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest request) {
    return error(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", request.getRequestURI(), null);
  }

  private static ResponseEntity<ApiError> error(
      HttpStatus status,
      String message,
      String path,
      Map<String, String> validationErrors
  ) {
    ApiError body = new ApiError(
        Instant.now().toString(),
        status.value(),
        status.getReasonPhrase(),
        message,
        path,
        validationErrors
    );
    return ResponseEntity.status(status).body(body);
  }

  public record ApiError(
      String timestamp,
      int status,
      String error,
      String message,
      String path,
      Map<String, String> validationErrors
  ) {}
}
