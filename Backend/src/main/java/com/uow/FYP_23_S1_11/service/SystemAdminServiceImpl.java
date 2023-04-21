package com.uow.FYP_23_S1_11.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SystemAdminServiceImpl implements SystemAdminService {

    @Override
    public List<?> getAllClinics() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllClinics'");
    }

    @Override
    public Boolean approveClinics() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'approveClinics'");
    }

}
