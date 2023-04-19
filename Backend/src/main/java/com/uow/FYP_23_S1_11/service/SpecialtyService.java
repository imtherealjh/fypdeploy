package com.uow.FYP_23_S1_11.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.uow.FYP_23_S1_11.domain.Specialty;

public interface SpecialtyService {
    public ResponseEntity<List<Specialty>> getAllSpecialty();
}
