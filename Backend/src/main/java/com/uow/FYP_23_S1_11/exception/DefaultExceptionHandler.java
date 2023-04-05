package com.uow.FYP_23_S1_11.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ AuthenticationException.class, AccessDeniedException.class })
    @ResponseBody
    public ResponseEntity<ApiError> handleAuthenticationException(Exception ex) {
      ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage(), ex);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }
}
