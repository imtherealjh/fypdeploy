package com.uow.FYP_23_S1_11.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.uow.FYP_23_S1_11.domain.Appointment;
import com.uow.FYP_23_S1_11.domain.request.ClinicAndDoctorFeedbackRequest;
import com.uow.FYP_23_S1_11.domain.request.DoctorAvailableRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterPatientRequest;
import com.uow.FYP_23_S1_11.domain.request.SearchLocReq;

public interface PatientService {
        public Object getPatientProfile();

        public List<?> getAllAppointments();

        public List<?> getAllClinicBySpecialty(String specialty);

        public Map<?, ?> getAllClinicSpecLoc(SearchLocReq searchLocReq, Pageable pageable);

        public List<Appointment> getDoctorAvailableAppointment(DoctorAvailableRequest req);

        public Boolean updateProfile(RegisterPatientRequest updateProfileReq);

        public Boolean insertClinicAndDoctorFeedback(ClinicAndDoctorFeedbackRequest request);

}
