package com.uow.FYP_23_S1_11.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.uow.FYP_23_S1_11.domain.request.RegisterFrontDeskRequest;
import com.uow.FYP_23_S1_11.service.ClerkService;
import com.uow.FYP_23_S1_11.constraints.OnUpdate;
import com.uow.FYP_23_S1_11.domain.request.EducationalMaterialRequest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/api/clerk", produces = { MediaType.APPLICATION_JSON_VALUE })
@Validated
@PreAuthorize("hasAuthority('FRONT_DESK')")
@SecurityRequirement(name = "bearerAuth")
public class ClerkController {
    @Autowired
    private ClerkService clerkService;

    @GetMapping("/getProfile")
    public ResponseEntity<?> getProfile() {
        return ResponseEntity.ok(clerkService.getProfile());
    }

    @Validated(OnUpdate.class)
    @PutMapping("/updateProfile")
    public ResponseEntity<?> updateProfile(@RequestBody @Valid RegisterFrontDeskRequest registerFrontDeskRequest) {
        return ResponseEntity.ok(clerkService.updateProfile(registerFrontDeskRequest));
    }

    @PostMapping("/checkInPatient")
    public ResponseEntity<?> checkInPatient(@RequestParam @NotNull Integer id) {
        return ResponseEntity.ok(clerkService.checkInUser(id));
    }

    @PostMapping("/createEduMaterial")
    public ResponseEntity<Boolean> createEduMaterial(
            @RequestBody @Valid EducationalMaterialRequest eduMaterialRequest) {
        return ResponseEntity.ok(clerkService.createEduMaterial(eduMaterialRequest));
    }

    @PutMapping("/updateEduMaterial")
    public ResponseEntity<Boolean> updateEduMaterial(@NotNull @RequestParam Integer id,
            @RequestBody @Valid EducationalMaterialRequest eduMaterialRequest) {
        return ResponseEntity.ok(clerkService.updateEduMaterial(id, eduMaterialRequest));
    }

}
