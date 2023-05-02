package com.uow.FYP_23_S1_11.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uow.FYP_23_S1_11.domain.request.PatientMedicalRecordsRequest;
import com.uow.FYP_23_S1_11.service.StaffService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/api/staff", produces = { MediaType.APPLICATION_JSON_VALUE })
@Validated
@PreAuthorize("hasAnyAuthority('DOCTOR', 'NURSE', 'FRONT_DESK')")
@SecurityRequirement(name = "bearerAuth")
public class StaffController {
    @Autowired
    private StaffService staffService;

    @GetMapping("/getPatientsByDate")
    public ResponseEntity<Object> getPatientsByDate(@RequestParam @NotNull LocalDate apptDate) {
        return ResponseEntity.ok(staffService.getPatientsByDate(apptDate));
    }

    @GetMapping("/getAllPatients")
    public ResponseEntity<List<?>> getAllPatients() {
        return ResponseEntity.ok(staffService.getAllPatients());
    }

    @GetMapping("/getAppointmentDetails")
    public ResponseEntity<List<?>> getAppointmentDetails(@RequestParam @NotNull Integer patientId) {
        return ResponseEntity.ok(staffService.getAppointmentDetails(patientId));
    }

    @GetMapping("/checkVerifyAppointment")
    public ResponseEntity<List<?>> checkVerifyAppointment(@RequestParam @NotNull Integer patientId) {
        return ResponseEntity.ok(staffService.checkVerifyAppointment(patientId));
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR', 'NURSE')")
    @PutMapping("/updateMedicalRecords")
    public ResponseEntity<Boolean> updateMedicalRecords(
            @Valid @RequestBody PatientMedicalRecordsRequest patientMedicalRecordsRequest) {
        return ResponseEntity.ok(staffService.updateMedicalRecords(patientMedicalRecordsRequest));
    }
}
