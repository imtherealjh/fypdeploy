package com.uow.FYP_23_S1_11.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uow.FYP_23_S1_11.repository.ClinicRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SystemAdminServiceImpl implements SystemAdminService {
    @Autowired
    private ClinicRepository clinicRepo;

    @Override
    public List<?> getAllClinics() {
        return clinicRepo.findAll();
    }

    @Override
    public Boolean approveClinics() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'approveClinics'");
    }

}
