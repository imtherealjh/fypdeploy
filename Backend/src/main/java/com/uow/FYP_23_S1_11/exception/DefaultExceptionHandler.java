package com.uow.FYP_23_S1_11.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatusCode status, WebRequest request) {
    List<String> errorList = ex
        .getBindingResult()
        .getFieldErrors()
        .stream()
        .map(fieldError -> fieldError.getDefaultMessage())
        .collect(Collectors.toList());
    ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST, errorList);
    return handleExceptionInternal(ex, errorDetails, headers, errorDetails.getStatus(), request);
  }

  @ExceptionHandler({ AuthenticationException.class, AccessDeniedException.class })
  @ResponseBody
  public ResponseEntity<ApiError> handleAuthenticationException(Exception ex) {
    System.out.println(ex.getLocalizedMessage());
    ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage(), ex);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
  }
}
