package com.uow.FYP_23_S1_11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uow.FYP_23_S1_11.service.QueueService;

import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/api/queue", produces = { MediaType.APPLICATION_JSON_VALUE })
@Validated
public class QueueController {
    @Autowired
    private QueueService queueService;

    @GetMapping("/getAppointmentDetails")
    public ResponseEntity<?> getAppointmentDetails(@RequestParam @NotNull Integer apptId) {
        return ResponseEntity.ok(queueService.getAppointmentDetails(apptId));
    }

    @GetMapping("/getCurrentQueueStatus")
    public ResponseEntity<?> getCurrentQueueStatus(@RequestParam @NotNull Integer apptId) {
        return ResponseEntity.ok(queueService.getCurrentQueueStatus(apptId));
    }
}
