package com.uow.FYP_23_S1_11.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.uow.FYP_23_S1_11.domain.PatientMedicalRecords;
import com.uow.FYP_23_S1_11.domain.request.PatientMedicalRecordsRequest;
import com.uow.FYP_23_S1_11.service.DoctorService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(value = "/api/doctor", produces = { MediaType.APPLICATION_JSON_VALUE })
@SecurityRequirement(name = "bearerAuth")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;
    
    @GetMapping("/secure")
    public ResponseEntity<String> getSecureRoute() {
        return ResponseEntity.ok("Secure endpoint");
    }

    @PostMapping("/insertMedicalRecords")
    public ResponseEntity<Boolean> insertMedicalRecords(@Valid @RequestBody PatientMedicalRecordsRequest patientMedicalRecordsRequest) {
        return ResponseEntity.ok(doctorService.insertMedicalRecords(patientMedicalRecordsRequest));
    }

//    @GetMapping("/getByPatientId")
//    public ResponseEntity<List<PatientMedicalRecords>> getByPatientId(@RequestParam Integer patientId) {
//        return ResponseEntity.ok(doctorService.getByPatientId(patientId));
//    }

    @PostMapping("/updateMedicalRecords")
    public ResponseEntity<Boolean> updateMedicalRecords(@RequestParam Integer medicalRecordsId,
            @RequestBody PatientMedicalRecordsRequest patientMedicalRecordsRequest) {
        return ResponseEntity.ok(doctorService.updateMedicalRecords(medicalRecordsId, patientMedicalRecordsRequest));
}
}
