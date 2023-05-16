package com.uow.FYP_23_S1_11.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.uow.FYP_23_S1_11.domain.UserAccount;
import com.uow.FYP_23_S1_11.domain.request.RegisterClinicRequest;
import com.uow.FYP_23_S1_11.domain.request.LoginRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterPatientRequest;
import com.uow.FYP_23_S1_11.domain.request.ResetPasswordConfirmReq;
import com.uow.FYP_23_S1_11.domain.request.ResetPasswordReq;
import com.uow.FYP_23_S1_11.enums.ERole;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserAccountService {
        public void authenticate(LoginRequest loginReq, HttpServletRequest request,
                        HttpServletResponse response, String token)
                        throws StreamWriteException, DatabindException, IOException;

        public void refresh(HttpServletRequest request,
                        HttpServletResponse response, String token)
                        throws StreamWriteException, DatabindException, IOException;

        public UserAccount registerAccount(UserAccount account, String email, ERole userRole);

        public Boolean registerClinicAccount(RegisterClinicRequest clinicReq);

        public Boolean registerPatientAccount(RegisterPatientRequest patientReq, HttpServletRequest request);

        public void logout(HttpServletRequest request,
                        HttpServletResponse response, String token);

        public Boolean verify(String verificationCode);

        public void resetPassword(ResetPasswordReq resetPasswordReq)
                        throws UnsupportedEncodingException, MessagingException;

        public void resetPasswordConfirm(ResetPasswordConfirmReq resetPasswordConfirmReq);
}
