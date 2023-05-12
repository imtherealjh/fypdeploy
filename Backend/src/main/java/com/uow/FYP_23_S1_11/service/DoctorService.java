package com.uow.FYP_23_S1_11.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.uow.FYP_23_S1_11.domain.request.RegisterDoctorRequest;

public interface DoctorService {
        public Object getProfile();

        public Boolean updateProfile(RegisterDoctorRequest registerDoctorRequest);

        public Map<?, ?> getDoctorFeedback(Pageable pageable);
}
