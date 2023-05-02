package com.uow.FYP_23_S1_11.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uow.FYP_23_S1_11.domain.PatientFeedbackDoctor;
import com.uow.FYP_23_S1_11.service.DoctorService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(value = "/api/doctor", produces = { MediaType.APPLICATION_JSON_VALUE })
@Validated
@PreAuthorize("hasAuthority('DOCTOR')")
@SecurityRequirement(name = "bearerAuth")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @GetMapping("/getByDoctorFeedbackId")
    public ResponseEntity<List<PatientFeedbackDoctor>> getByDoctorFeedbackId(@RequestParam Integer doctorFeedbackId) {
        return ResponseEntity.ok(doctorService.getByDoctorFeedbackId(doctorFeedbackId));
    }
}
