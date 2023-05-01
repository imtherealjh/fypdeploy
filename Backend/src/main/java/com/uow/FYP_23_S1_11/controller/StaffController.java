package com.uow.FYP_23_S1_11.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uow.FYP_23_S1_11.service.StaffService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(value = "/api/staff", produces = { MediaType.APPLICATION_JSON_VALUE })
@Validated
@PreAuthorize("hasAuthority('DOCTOR') or hasAuthority('NURSE') or hasAuthority('FRONT_DESK')")
@SecurityRequirement(name = "bearerAuth")
public class StaffController {
    @Autowired
    private StaffService staffService;

    @GetMapping("/getAllPatients")
    public ResponseEntity<List<?>> getAllPatients() {
        return ResponseEntity.ok(staffService.getAllPatients());
    }
}
