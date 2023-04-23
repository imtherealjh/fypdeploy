package com.uow.FYP_23_S1_11.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatusCode status, WebRequest request) {
    List<String> errorList = ex
        .getBindingResult()
        .getFieldErrors()
        .stream()
        .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
        .collect(Collectors.toList());
    ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST, errorList);
    return handleExceptionInternal(ex, errorDetails, headers, errorDetails.getStatus(), request);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  protected Object handleNoRecordFoundException(IllegalArgumentException ex) {
    List<String> errorList = List.of(ex.getMessage());
    ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST, errorList);
    return errorDetails;
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  protected Object onConstraintValidationException(ConstraintViolationException ex) {
    List<String> errorList = ex.getConstraintViolations().stream()
        .map(fieldError -> fieldError.getPropertyPath().toString() + " " + fieldError.getMessage())
        .collect(Collectors.toList());
    ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST, errorList);
    return errorDetails;
  }
}
