package com.uow.FYP_23_S1_11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uow.FYP_23_S1_11.domain.request.GenerateAppointmentRequest;
import com.uow.FYP_23_S1_11.domain.request.GenerateClinicAppointmentRequest;
import com.uow.FYP_23_S1_11.service.ClerkService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(value="/api/clerk", produces = { MediaType.APPLICATION_JSON_VALUE })
@SecurityRequirement(name = "bearerAuth")
public class ClerkController {
    @Autowired private ClerkService clerkService;

    @PostMapping("/generateClinicAppointmentSlots")
    public ResponseEntity<Boolean> generateClinicAppointmentSlots(@RequestBody GenerateClinicAppointmentRequest generateClinicAppointmentReq) {
        return ResponseEntity.ok(clerkService.generateClinicAppointmentSlots(generateClinicAppointmentReq));
    }

    @PostMapping("/generateAppointmentSlots")
    public ResponseEntity<Boolean> generateDoctorAppointmentSlots(@RequestBody GenerateAppointmentRequest generateAppointmentReq) {
        return ResponseEntity.ok(clerkService.generateDoctorAppointmentSlots(generateAppointmentReq));
    }
}
