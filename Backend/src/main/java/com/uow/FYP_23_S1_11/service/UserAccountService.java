package com.uow.FYP_23_S1_11.service;

import com.uow.FYP_23_S1_11.domain.request.ClinicRegisterRequest;
import com.uow.FYP_23_S1_11.domain.request.PatientRegisterRequest;

public interface UserAccountService {
    public Boolean registerClinicAccount(ClinicRegisterRequest clinicReq);
    public Boolean registerPatientAccount(PatientRegisterRequest patientReq);
}
