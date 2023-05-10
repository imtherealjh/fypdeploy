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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uow.FYP_23_S1_11.domain.request.SpecialtyRequest;
import com.uow.FYP_23_S1_11.service.SystemAdminService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
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

    @PostMapping("/createNewSpecialty")
    public ResponseEntity<?> createNewSpecialty(@RequestBody @Valid SpecialtyRequest specialty) {
        return ResponseEntity.ok(sysAdminService.createNewSpecialty(specialty));
    }

    @PostMapping("/updateSpecialty")
    public ResponseEntity<?> updateSpecialty(@RequestParam @NotNull Integer id,
            @RequestBody @NotEmpty String specialty) {
        return ResponseEntity.ok(sysAdminService.updateSpecialty(id, specialty));
    }

    @PostMapping("/deleteSpecialty")
    public ResponseEntity<?> deleteSpecialty(@RequestParam @NotNull Integer id) {
        return ResponseEntity.ok(sysAdminService.deleteSpecialty(id));
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

    @PutMapping("/resolveTicket")
    public ResponseEntity<?> resolveTicket(@NotNull @RequestParam Integer ticketId) {
        return ResponseEntity.ok(sysAdminService.resolveTechnicalTicket(ticketId));
    }
}
