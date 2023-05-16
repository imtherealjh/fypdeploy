package com.uow.FYP_23_S1_11.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.uow.FYP_23_S1_11.domain.request.RegisterClinicRequest;
import com.uow.FYP_23_S1_11.constraints.OnCreate;
import com.uow.FYP_23_S1_11.domain.request.LoginRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterPatientRequest;
import com.uow.FYP_23_S1_11.domain.request.ResetPasswordConfirmReq;
import com.uow.FYP_23_S1_11.domain.request.ResetPasswordReq;
import com.uow.FYP_23_S1_11.service.UserAccountService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

//import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(value = "/api/auth", produces = { MediaType.APPLICATION_JSON_VALUE })
@Validated
public class AuthController {
    @Autowired
    private UserAccountService userAccountService;

    @GetMapping("/refresh")
    public void refresh(HttpServletRequest request,
            HttpServletResponse response, @CookieValue(value = "refreshToken", defaultValue = "") String token)
            throws StreamWriteException, DatabindException, IOException {
        userAccountService.refresh(request, response, token);
    }

    @PostMapping("/login")
    public void authenticate(@RequestBody @Valid LoginRequest loginRequest, HttpServletRequest request,
            HttpServletResponse response, @CookieValue(value = "refreshToken", defaultValue = "") String token)
            throws StreamWriteException, DatabindException, IOException {
        userAccountService.authenticate(loginRequest, request, response, token);
    }

    @Validated(OnCreate.class)
    @PostMapping(value = "/registerClinic", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Boolean> registerClinic(@Valid @ModelAttribute RegisterClinicRequest clinicReq) {
        Boolean result = userAccountService.registerClinicAccount(clinicReq);
        return ResponseEntity.ok(result);
    }

    @Validated(OnCreate.class)
    @PostMapping("/registerPatient")
    public ResponseEntity<Boolean> registerPatient(@Valid @RequestBody RegisterPatientRequest patientReq,
            HttpServletRequest request) {
        Boolean result = userAccountService.registerPatientAccount(patientReq, request);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/verify")
    public ResponseEntity<Boolean> verifyUser(@RequestParam("code") @NotEmpty String code) {
        return ResponseEntity.ok(userAccountService.verify(code));
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request,
            HttpServletResponse response, @CookieValue(value = "refreshToken", defaultValue = "") String token) {
        userAccountService.logout(request, response, token);
    }

    @PostMapping("/reset")
    public void resetPassword(@RequestBody @Valid ResetPasswordReq resetPasswordReq)
            throws UnsupportedEncodingException, MessagingException {
        userAccountService.resetPassword(resetPasswordReq);
    }

    @PostMapping("/resetConfirm")
    public void resetPasswordConfirm(@RequestBody @Valid ResetPasswordConfirmReq resetPasswordConfirmReq)
            throws UnsupportedEncodingException, MessagingException {
        userAccountService.resetPasswordConfirm(resetPasswordConfirmReq);
    }
}
