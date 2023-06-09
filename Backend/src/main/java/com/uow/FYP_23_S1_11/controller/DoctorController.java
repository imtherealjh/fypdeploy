package com.uow.FYP_23_S1_11.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

import com.uow.FYP_23_S1_11.constraints.OnUpdate;
import com.uow.FYP_23_S1_11.domain.request.RegisterDoctorRequest;
import com.uow.FYP_23_S1_11.service.DoctorService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/doctor", produces = { MediaType.APPLICATION_JSON_VALUE })
@Validated
@PreAuthorize("hasAuthority('DOCTOR')")
@SecurityRequirement(name = "bearerAuth")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @GetMapping("/getProfile")
    public ResponseEntity<Object> getProfile() {
        return ResponseEntity.ok(doctorService.getProfile());
    }

    @GetMapping("/getFeedback")
    public ResponseEntity<Map<?, ?>> getDoctorFeedback(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(doctorService.getDoctorFeedback(PageRequest.of(page, size)));
    }

    @Validated(OnUpdate.class)
    @PutMapping("/updateProfile")
    public ResponseEntity<?> updateProfile(@RequestBody @Valid RegisterDoctorRequest registerDoctorRequest) {
        return ResponseEntity.ok(doctorService.updateProfile(registerDoctorRequest));
    }

}
