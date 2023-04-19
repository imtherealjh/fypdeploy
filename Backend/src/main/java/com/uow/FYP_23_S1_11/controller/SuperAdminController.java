package com.uow.FYP_23_S1_11.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/sysAdmin", produces = { MediaType.APPLICATION_JSON_VALUE })
@Validated
@PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
public class SuperAdminController {
    @GetMapping("/secure")
    public ResponseEntity<String> getSecureRoute() {
        return ResponseEntity.ok("Secure endpoint");
    }
}
