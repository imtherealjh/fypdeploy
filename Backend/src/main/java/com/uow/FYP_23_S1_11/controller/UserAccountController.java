package com.uow.FYP_23_S1_11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uow.FYP_23_S1_11.domain.request.ClinicRegisterRequest;
import com.uow.FYP_23_S1_11.domain.request.PatientRegisterRequest;
import com.uow.FYP_23_S1_11.service.UserAccountService;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping(value="/api/account", produces={MediaType.APPLICATION_JSON_VALUE})
public class UserAccountController {
    @Autowired private UserAccountService userAccountService;

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
