package com.uow.FYP_23_S1_11.service;

import java.io.IOException;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.uow.FYP_23_S1_11.domain.UserAccount;
import com.uow.FYP_23_S1_11.domain.request.ClinicRegisterRequest;
import com.uow.FYP_23_S1_11.domain.request.LoginRequest;
import com.uow.FYP_23_S1_11.domain.request.PatientRegisterRequest;
import com.uow.FYP_23_S1_11.enums.ERole;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserAccountService {
    public void authenticate(LoginRequest loginReq, HttpServletRequest request,
            HttpServletResponse response, String token) throws StreamWriteException, DatabindException, IOException;

    public void refresh(HttpServletRequest request,
            HttpServletResponse response, String token) throws StreamWriteException, DatabindException, IOException;

    public UserAccount registerAccount(UserAccount account, ERole userRole);

    public Boolean registerClinicAccount(ClinicRegisterRequest clinicReq);

    public Boolean registerPatientAccount(PatientRegisterRequest patientReq);
}
