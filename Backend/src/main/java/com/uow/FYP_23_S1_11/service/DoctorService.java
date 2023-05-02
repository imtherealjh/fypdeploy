package com.uow.FYP_23_S1_11.service;

import java.util.List;

import com.uow.FYP_23_S1_11.domain.PatientFeedbackDoctor;
import com.uow.FYP_23_S1_11.domain.request.RegisterDoctorRequest;

public interface DoctorService {
        public List<PatientFeedbackDoctor> getByDoctorFeedbackId(Integer doctorFeedbackId);

        public Object getProfile();

        public Boolean updateProfile(RegisterDoctorRequest registerDoctorRequest);
}
