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
import com.uow.FYP_23_S1_11.domain.request.RegisterDoctorRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterFrontDeskRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterNurseRequest;
import com.uow.FYP_23_S1_11.service.ClinicOwnerService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping(value = "/api/clinic-owner", produces = { MediaType.APPLICATION_JSON_VALUE })
@SecurityRequirement(name = "bearerAuth")
public class ClinicOwnerController {
    @Autowired private ClinicOwnerService clincOwnerService;

    @PostMapping("/insertDoctorSchedule")
    public ResponseEntity<Boolean> insertDoctorSchedule(@Valid @RequestBody DoctorScheduleRequest doctorScheduleRequest) {
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

    @PostMapping("/registerFrontDesk")
    public ResponseEntity<Boolean> registerFrontDesk(@Valid @RequestBody RegisterFrontDeskRequest registerFrontDeskReq) {
        return ResponseEntity.ok(clincOwnerService.registerFrontDesk(registerFrontDeskReq));
    }
}
