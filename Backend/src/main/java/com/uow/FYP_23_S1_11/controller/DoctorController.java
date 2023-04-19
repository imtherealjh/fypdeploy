package com.uow.FYP_23_S1_11.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.uow.FYP_23_S1_11.domain.PatientMedicalRecords;
import com.uow.FYP_23_S1_11.domain.request.PatientMedicalRecordsRequest;
import com.uow.FYP_23_S1_11.exception.MedicalRecordsNotFoundException;
import com.uow.FYP_23_S1_11.service.DoctorService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(value = "/api/doctor", produces = { MediaType.APPLICATION_JSON_VALUE })
@Validated
@SecurityRequirement(name = "bearerAuth")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/secure")
    public ResponseEntity<String> getSecureRoute() {
        return ResponseEntity.ok("Secure endpoint");
    }

    @PostMapping("/insertMedicalRecords")
    public ResponseEntity<Boolean> insertMedicalRecords(
            @Valid @RequestBody PatientMedicalRecordsRequest patientMedicalRecordsRequest) {
        return ResponseEntity.ok(doctorService.insertMedicalRecords(patientMedicalRecordsRequest));
    }

    @GetMapping("/getByMedicalRecordsId")
    public ResponseEntity<List<PatientMedicalRecords>> getByMedicalRecordsId(@RequestParam Integer medicalRecordId)
            throws MedicalRecordsNotFoundException {
        return ResponseEntity.ok(doctorService.getByMedicalRecordsId(medicalRecordId));
    }

    @PostMapping("/updateMedicalRecords")
    public ResponseEntity<Boolean> updateMedicalRecords(@Valid @RequestBody @RequestParam Integer medicalRecordsId,
            PatientMedicalRecordsRequest patientMedicalRecordsRequest) throws MedicalRecordsNotFoundException {
        return ResponseEntity.ok(doctorService.updateMedicalRecords(medicalRecordsId, patientMedicalRecordsRequest));
    }
}
