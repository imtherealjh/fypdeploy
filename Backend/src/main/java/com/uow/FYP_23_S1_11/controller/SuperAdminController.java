package com.uow.FYP_23_S1_11.controller;

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

import com.uow.FYP_23_S1_11.service.SystemAdminService;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/api/sysAdmin", produces = { MediaType.APPLICATION_JSON_VALUE })
@Validated
@PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
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

}
