package com.uow.FYP_23_S1_11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uow.FYP_23_S1_11.domain.request.DoctorScheduleRequest;
import com.uow.FYP_23_S1_11.domain.request.GenerateAppointmentRequest;
import com.uow.FYP_23_S1_11.domain.request.GenerateClinicAppointmentRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterDoctorRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterFrontDeskRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterNurseRequest;
import com.uow.FYP_23_S1_11.service.ClerkService;
import com.uow.FYP_23_S1_11.service.ClinicOwnerService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping(value = "/api/clinicOwner", produces = { MediaType.APPLICATION_JSON_VALUE })
// @PreAuthorize("hasAuthority('CLINIC_OWNER')")
@SecurityRequirement(name = "bearerAuth")
public class ClinicOwnerController {
    @Autowired
    private ClinicOwnerService clincOwnerService;
    @Autowired
    private ClerkService clerkService;

    @PostMapping("/insertDoctorSchedule")
    public ResponseEntity<Boolean> insertDoctorSchedule(
            @Valid @RequestBody DoctorScheduleRequest doctorScheduleRequest) {
        System.out.println(doctorScheduleRequest.getDoctorId());
        return ResponseEntity.ok(clincOwnerService.insertDoctorSchedule(doctorScheduleRequest));
    }

    @PostMapping("/registerDoctor")
    public ResponseEntity<Boolean> registerDoctor(@Valid @RequestBody RegisterDoctorRequest registerDoctorReq) {
        return ResponseEntity.ok(clincOwnerService.registerDoctor(registerDoctorReq));
    }

    @PostMapping("/registerNurse")
    public ResponseEntity<Boolean> registerNurse(@Valid @RequestBody RegisterNurseRequest registerNurseReq) {
        return ResponseEntity.ok(clincOwnerService.registerNurse(registerNurseReq));
    }

    @PostMapping("/registerClerk")
    public ResponseEntity<Boolean> registerClerk(@Valid @RequestBody RegisterFrontDeskRequest registerFrontDeskReq) {
        return ResponseEntity.ok(clincOwnerService.registerFrontDesk(registerFrontDeskReq));
    }

    @PostMapping("/generateClinicAppointmentSlots")
    public ResponseEntity<Boolean> generateClinicAppointmentSlots(
            @RequestBody GenerateClinicAppointmentRequest generateClinicAppointmentReq) {
        return ResponseEntity.ok(clerkService.generateClinicAppointmentSlots(generateClinicAppointmentReq));
    }

    @PostMapping("/generateAppointmentSlots")
    public ResponseEntity<Boolean> generateDoctorAppointmentSlots(
            @RequestBody GenerateAppointmentRequest generateAppointmentReq) {
        return ResponseEntity.ok(clerkService.generateDoctorAppointmentSlots(generateAppointmentReq));
    }
}
