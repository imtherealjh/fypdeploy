package com.uow.FYP_23_S1_11.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uow.FYP_23_S1_11.domain.Clinic;
import com.uow.FYP_23_S1_11.domain.Doctor;
import com.uow.FYP_23_S1_11.domain.Specialty;
import com.uow.FYP_23_S1_11.domain.request.AccessTokenRequest;
import com.uow.FYP_23_S1_11.domain.request.ClinicRegisterRequest;
import com.uow.FYP_23_S1_11.domain.request.LoginRequest;
import com.uow.FYP_23_S1_11.domain.request.PatientRegisterRequest;
import com.uow.FYP_23_S1_11.domain.response.AuthResponse;
import com.uow.FYP_23_S1_11.service.PatientService;
import com.uow.FYP_23_S1_11.service.UserAccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value="/api/auth", produces={MediaType.APPLICATION_JSON_VALUE})
public class AuthController {
    @Autowired private UserAccountService userAccountService;
    @Autowired private PatientService patientService;

    @GetMapping("/getAllSpecialty")
    public ResponseEntity<List<Specialty>> getAllSpecialty() {
        return ResponseEntity.ok(patientService.getAllSpecialty());
    }

    @GetMapping("/getClinicsBySpecialty")
    public ResponseEntity<List<Clinic>> getClinicBySpecialty(@RequestParam String specialty) {
        return ResponseEntity.ok(patientService.getAllClinicBySpecialty(specialty));
    }

    @GetMapping("/getDoctorsByClinicSpecialty")
    public ResponseEntity<List<Doctor>> getDoctorsByClinicSpecialty( @RequestParam String clinicId, @RequestParam String specialty) {
        return ResponseEntity.ok(patientService.getAllDoctorsByClinicSpecialty(clinicId, specialty));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userAccountService.authenticate(loginRequest));
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
