package com.uow.FYP_23_S1_11.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping(value="/api/doctor", produces={MediaType.APPLICATION_JSON_VALUE})
@SecurityRequirement(name="bearerAuth")
public class DoctorController {
    @GetMapping("/secure")
    public ResponseEntity<String> getSecureRoute() {
        return ResponseEntity.ok("Secure endpoint");
    }
    
}
