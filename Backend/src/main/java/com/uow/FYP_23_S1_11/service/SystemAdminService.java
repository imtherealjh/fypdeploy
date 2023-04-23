package com.uow.FYP_23_S1_11.service;

import java.util.List;

public interface SystemAdminService {
    public Object getClinicById(Integer clinicId);

    public List<?> getAllClinics();

    public Boolean approveClinic(Integer clinicId);

    public Boolean rejectClinic(Integer clinicId);

    public Boolean suspendClinic(Integer clinicId);

    public Boolean enableClinic(Integer clinicId);

}
