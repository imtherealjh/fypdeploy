package com.uow.FYP_23_S1_11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uow.FYP_23_S1_11.domain.request.FeedbackRequest;
import com.uow.FYP_23_S1_11.service.AllService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/api/all", produces = { MediaType.APPLICATION_JSON_VALUE })
@Validated
public class AllController {
    @Autowired
    private AllService allService;

    @GetMapping("/getSystemFeedback")
    public ResponseEntity<?> getAllSystemFeedback(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(allService.getAllSystemFeedback(PageRequest.of(page, size)));
    }

    @PostMapping("/insertSystemFeedback")
    public ResponseEntity<Boolean> insertSystemFeedback(
            @RequestBody @Valid FeedbackRequest feedback) {
        return ResponseEntity.ok(allService.insertSystemFeedback(feedback));
    }

    @PutMapping("/updateSystemFeedback")
    public ResponseEntity<Boolean> updateSystemFeedback(@RequestParam @NotNull Integer id,
            @RequestBody @Valid FeedbackRequest feedback) {
        return ResponseEntity.ok(allService.updateSystemFeedback(id, feedback));
    }

    @DeleteMapping("/deleteSystemFeedback")
    public ResponseEntity<Boolean> deleteSystemFeedback(@RequestParam @NotNull Integer id) {
        return ResponseEntity.ok(allService.deleteSystemFeedback(id));
    }

}
