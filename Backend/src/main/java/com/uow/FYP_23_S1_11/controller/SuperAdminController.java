package com.uow.FYP_23_S1_11.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uow.FYP_23_S1_11.domain.request.SystemFeedbackRequest;
import com.uow.FYP_23_S1_11.service.SystemAdminService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/api/sysAdmin", produces = { MediaType.APPLICATION_JSON_VALUE })
@Validated
@PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
@SecurityRequirement(name = "bearerAuth")
public class SuperAdminController {
    @Autowired
    private SystemAdminService sysAdminService;

    @GetMapping("/getClinicById")
    public ResponseEntity<Object> getClinicById(@NotNull @RequestParam Integer clinicId) {
        return ResponseEntity.ok(sysAdminService.getClinicById(clinicId));
    }

    @GetMapping("/getAllClinics")
    public ResponseEntity<List<?>> getAllClinics() {
        return ResponseEntity.ok(sysAdminService.getAllClinics());
    }

    @GetMapping("/getClinicLicense")
    public ResponseEntity<?> getClinicLicense(@NotNull @RequestParam Integer clinicId) {
        return sysAdminService.getClinicLicense(clinicId);
    }

    @PutMapping("/approveClinic")
    public ResponseEntity<?> approveClinic(@NotNull @RequestParam Integer clinicId) {
        return ResponseEntity.ok(sysAdminService.approveClinic(clinicId));
    }

    @PutMapping("/rejectClinic")
    public ResponseEntity<?> rejectClinic(@NotNull @RequestParam Integer clinicId) {
        return ResponseEntity.ok(sysAdminService.rejectClinic(clinicId));
    }

    @PutMapping("/suspendClinic")
    public ResponseEntity<?> suspendClinic(@NotNull @RequestParam Integer clinicId) {
        return ResponseEntity.ok(sysAdminService.suspendClinic(clinicId));
    }

    @PutMapping("/enableClinic")
    public ResponseEntity<?> enableClinic(@NotNull @RequestParam Integer clinicId) {
        return ResponseEntity.ok(sysAdminService.enableClinic(clinicId));
    }

    @GetMapping("/findFeedbackByRole")
    public ResponseEntity<?> findFeedbackByRole(@RequestParam String role) {
        return ResponseEntity.ok(sysAdminService.findFeedbackByRole(role));
    }

    @GetMapping("/findFeedbackByDate")
    public ResponseEntity<?> findFeedbackByDate(@RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(sysAdminService.findFeedbackByDate(startDate, endDate));
    }

    @GetMapping("/findFeedbackByDateAndRole")
    public ResponseEntity<?> findFeedbackByDateAndRole(@RequestParam String role, @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(sysAdminService.findFeedbackByDateAndRole(startDate, endDate, role));
    }

    @GetMapping("/getAllFeedbackPendingStatus")
    public ResponseEntity<List<?>> getAllFeedbackPendingStatus() {
        return ResponseEntity.ok(sysAdminService.getAllFeedbackPendingStatus());
    }

    @GetMapping("/getAllFeedbackCompleteStatus")
    public ResponseEntity<List<?>> getAllFeedbackCompleteStatus() {
        return ResponseEntity.ok(sysAdminService.getAllFeedbackCompleteStatus());
    }

    @PostMapping("/updateSystemFeedback")
    public ResponseEntity<Boolean> updateSystemFeedback(@RequestParam Integer systemFeedbackId,
            @Valid @RequestBody SystemFeedbackRequest request) {
        return ResponseEntity.ok(sysAdminService.updateSystemFeedback(systemFeedbackId, request));
    }
}
