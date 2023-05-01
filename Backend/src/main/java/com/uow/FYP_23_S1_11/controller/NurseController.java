package com.uow.FYP_23_S1_11.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uow.FYP_23_S1_11.service.StaffService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/api/nurse", produces = { MediaType.APPLICATION_JSON_VALUE })
@Validated
@PreAuthorize("hasAuthority('NURSE')")
@SecurityRequirement(name = "bearerAuth")
public class NurseController {
    @Autowired
    private StaffService staffService;

    @GetMapping("/getVisitingPatients")
    public ResponseEntity<?> getAllVisitingPatientByDate(@NotNull @RequestParam LocalDate apptDate) {
        return ResponseEntity.ok(staffService.getPatientsByDate(apptDate));
    }

}
