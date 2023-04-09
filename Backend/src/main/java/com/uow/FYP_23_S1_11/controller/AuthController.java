package com.uow.FYP_23_S1_11.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uow.FYP_23_S1_11.domain.request.AccessTokenRequest;
import com.uow.FYP_23_S1_11.domain.request.ClinicRegisterRequest;
import com.uow.FYP_23_S1_11.domain.request.LoginRequest;
import com.uow.FYP_23_S1_11.domain.request.PatientRegisterRequest;
import com.uow.FYP_23_S1_11.domain.response.AuthResponse;
import com.uow.FYP_23_S1_11.service.UserAccountService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/auth", produces = { MediaType.APPLICATION_JSON_VALUE })
public class AuthController {
    @Autowired
    private UserAccountService userAccountService;

    @PostMapping("/login")
    public void authenticate(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request,
            HttpServletResponse response, @CookieValue(value = "token", defaultValue = "") String token)
            throws StreamWriteException, DatabindException, IOException {
        var tokens = userAccountService.authenticate(loginRequest);

        System.out.println(token);

        Cookie cookie = new Cookie("token", tokens.getRefreshToken());
        cookie.setHttpOnly(true);

        response.addCookie(cookie);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    @PostMapping("/requestAccessToken")
    public ResponseEntity<AuthResponse> requestAccessToken(@Valid @RequestBody AccessTokenRequest accessTokenRequest) {
        return ResponseEntity.ok(userAccountService.regenerateAccessToken(accessTokenRequest));
    }

    @PostMapping("/registerClinic")
    public ResponseEntity<Boolean> registerClinic(@Valid @RequestBody ClinicRegisterRequest clinicReq) {
        try {
            Boolean result = userAccountService.registerClinicAccount(clinicReq);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(404).body(false);
        }
    }

    @PostMapping("/registerPatient")
    public ResponseEntity<Boolean> registerPatient(@Valid @RequestBody PatientRegisterRequest patientReq) {
        Boolean result = userAccountService.registerPatientAccount(patientReq);
        return ResponseEntity.ok(result);
    }
}
