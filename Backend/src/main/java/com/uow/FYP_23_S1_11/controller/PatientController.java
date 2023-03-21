package com.uow.FYP_23_S1_11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uow.FYP_23_S1_11.domain.request.PatientFeedbackRequest;
import com.uow.FYP_23_S1_11.service.PatientService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;


@RestController
@RequestMapping("/api/patient")
@SecurityRequirement(name = "bearerAuth")
public class PatientController {
    @Autowired private PatientService patientService;

    @PostMapping("/insertFeedback")
    public ResponseEntity<Boolean> insertFeedback(@RequestBody PatientFeedbackRequest patientFeedbackRequest) {
        return ResponseEntity.ok(patientService.insertFeedback(patientFeedbackRequest));
    }

}
