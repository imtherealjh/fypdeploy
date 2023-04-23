package com.uow.FYP_23_S1_11.controller;

import java.util.Date;
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

import com.uow.FYP_23_S1_11.domain.PatientFeedbackDoctor;
import com.uow.FYP_23_S1_11.domain.PatientMedicalRecords;
import com.uow.FYP_23_S1_11.domain.request.PatientMedicalRecordsRequest;
import com.uow.FYP_23_S1_11.service.DoctorService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(value = "/api/doctor", produces = { MediaType.APPLICATION_JSON_VALUE })
@Validated
@SecurityRequirement(name = "bearerAuth")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @GetMapping("/getPatientsByDate")
    public ResponseEntity<?> getPatientsByApptDate(@NotNull @RequestParam String apptDate) {
        return ResponseEntity.ok(doctorService.getPatientsByApptDate(apptDate));
    }

    @PostMapping("/insertMedicalRecords")
    public ResponseEntity<Boolean> insertMedicalRecords(
            @Valid @RequestBody PatientMedicalRecordsRequest patientMedicalRecordsRequest) {
        return ResponseEntity.ok(doctorService.insertMedicalRecords(patientMedicalRecordsRequest));
    }

    @GetMapping("/getByMedicalRecordsId")
    public ResponseEntity<List<PatientMedicalRecords>> getByMedicalRecordsId(@RequestParam Integer medicalRecordId) {
        return ResponseEntity.ok(doctorService.getByMedicalRecordsId(medicalRecordId));
    }

    @PostMapping("/updateMedicalRecords")
    public ResponseEntity<Boolean> updateMedicalRecords(@RequestParam Integer medicalRecordsId,
            @Valid @RequestBody PatientMedicalRecordsRequest patientMedicalRecordsRequest) {
        return ResponseEntity.ok(doctorService.updateMedicalRecords(medicalRecordsId, patientMedicalRecordsRequest));
    }

    @GetMapping("/getByDoctorFeedbackId")
    public ResponseEntity<List<PatientFeedbackDoctor>> getByDoctorFeedbackId(@RequestParam Integer doctorFeedbackId) {
        return ResponseEntity.ok(doctorService.getByDoctorFeedbackId(doctorFeedbackId));
    }
}
