package com.uow.FYP_23_S1_11.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.uow.FYP_23_S1_11.domain.request.QueueRequest;
import com.uow.FYP_23_S1_11.service.ClerkService;

import com.uow.FYP_23_S1_11.domain.request.EducationalMaterialRequest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/clerk", produces = { MediaType.APPLICATION_JSON_VALUE })
@Validated
@SecurityRequirement(name = "bearerAuth")
public class ClerkController {
    @Autowired
    private ClerkService clerkService;

    @PostMapping("/createEduMaterial")
    public ResponseEntity<Boolean> createEduMaterial(@RequestBody EducationalMaterialRequest eduMaterialRequest) {
        return ResponseEntity.ok(clerkService.createEduMaterial(eduMaterialRequest));
    }

    // Required??
    @PostMapping("/updateEduMaterial")
    public ResponseEntity<Boolean> updateEduMaterial(@RequestParam Integer materialId,
            EducationalMaterialRequest eduMaterialRequest) {
        return ResponseEntity.ok(clerkService.updateEduMaterial(materialId, eduMaterialRequest));
    }

    @DeleteMapping("/deleteEduMaterial")
    public ResponseEntity<Boolean> deleteEduMaterial(@RequestParam Integer materialId) {
        return ResponseEntity.ok(clerkService.deleteEduMaterial(materialId));
    }

    @PostMapping("/updateQueueNumber")
    public ResponseEntity<Boolean> updateQueueNumber(@RequestParam Integer queueId,
            @Valid @RequestBody QueueRequest updateQueueRequest) {
        return ResponseEntity.ok(clerkService.updateQueueNumber(queueId, updateQueueRequest));
    }

    @DeleteMapping("/deleteQueueNumber")
    public ResponseEntity<Boolean> deleteQueueNumber(@RequestParam Integer queueId) {
        return ResponseEntity.ok(clerkService.deleteQueueNumber(queueId));
    }
}
