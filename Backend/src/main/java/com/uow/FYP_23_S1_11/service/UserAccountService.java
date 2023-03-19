package com.uow.FYP_23_S1_11.service;

import com.uow.FYP_23_S1_11.domain.request.AccessTokenRequest;
import com.uow.FYP_23_S1_11.domain.request.ClinicRegisterRequest;
import com.uow.FYP_23_S1_11.domain.request.LoginRequest;
import com.uow.FYP_23_S1_11.domain.request.PatientRegisterRequest;
import com.uow.FYP_23_S1_11.domain.response.AuthResponse;

public interface UserAccountService {
    public AuthResponse authenticate(LoginRequest loginReq);
    public AuthResponse regenerateAccessToken(AccessTokenRequest accessTokenReq);
    public Boolean registerClinicAccount(ClinicRegisterRequest clinicReq);
    public Boolean registerPatientAccount(PatientRegisterRequest patientReq);
    
}
