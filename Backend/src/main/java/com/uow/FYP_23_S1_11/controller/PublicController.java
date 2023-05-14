package com.uow.FYP_23_S1_11.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uow.FYP_23_S1_11.domain.Specialty;
import com.uow.FYP_23_S1_11.service.LandingPageService;
import com.uow.FYP_23_S1_11.service.SpecialtyService;

@RestController
@RequestMapping(value = "/api/public", produces = { MediaType.APPLICATION_JSON_VALUE })
public class PublicController {
        @Autowired
        private SpecialtyService specialtyService;
        @Autowired
        private LandingPageService landingPageService;

        @GetMapping("/getAllSpecialty")
        public ResponseEntity<List<Specialty>> getAllSpecialty() {
                return specialtyService.getAllSpecialty();
        }

        @GetMapping("/getLandingPageData")
        public ResponseEntity<?> getLandingPageData() {
                return ResponseEntity.ok(landingPageService.retrieveClinicFeedback());
        }
}
