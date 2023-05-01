package com.uow.FYP_23_S1_11.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface SystemAdminService {

    public List<?> getAllClinics();

    public Object getClinicById(Integer clinicId);

    public ResponseEntity<byte[]> getClinicLicense(Integer clinicId);

    public Boolean approveClinic(Integer clinicId);

    public Boolean rejectClinic(Integer clinicId);

    public Boolean suspendClinic(Integer clinicId);

    public Boolean enableClinic(Integer clinicId);

}
