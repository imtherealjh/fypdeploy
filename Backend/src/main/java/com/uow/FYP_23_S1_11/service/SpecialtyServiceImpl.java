package com.uow.FYP_23_S1_11.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.uow.FYP_23_S1_11.domain.Specialty;
import com.uow.FYP_23_S1_11.repository.SpecialtyRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SpecialtyServiceImpl implements SpecialtyService {
    @Autowired
    private SpecialtyRepository specialtyRepo;

    @Override
    public ResponseEntity<List<Specialty>> getAllSpecialty() {
        return ResponseEntity.ok().body(specialtyRepo.findAll());
    }
}
