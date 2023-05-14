package com.uow.FYP_23_S1_11.exception;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DelegatedAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        Throwable throwable = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        ObjectMapper mapper = new ObjectMapper();

        ApiError apiError;
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        if (throwable != null && throwable.getCause().getClass() == ExpiredJwtException.class) {
            response.setStatus(406);
            apiError = new ApiError(HttpStatus.NOT_ACCEPTABLE, throwable);
        } else if (authException instanceof DisabledException) {
            response.setStatus(409);
            apiError = new ApiError(HttpStatus.CONFLICT, authException);
        } else {
            response.setStatus(401);
            apiError = new ApiError(HttpStatus.UNAUTHORIZED, authException);
        }

        log.error("AuthEntryPointJwt error: {}", apiError.getDebugMessage());
        response.getWriter().write(mapper.writeValueAsString(apiError));
    }

}
