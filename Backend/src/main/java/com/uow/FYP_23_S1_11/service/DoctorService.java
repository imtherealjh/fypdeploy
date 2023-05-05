package com.uow.FYP_23_S1_11.service;

import java.util.List;

import com.uow.FYP_23_S1_11.domain.PatientFeedbackDoctor;
import com.uow.FYP_23_S1_11.domain.PatientMedicalRecords;
import com.uow.FYP_23_S1_11.domain.request.PatientMedicalRecordsRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterDoctorRequest;

public interface DoctorService {

        public Object getProfile();

        public Boolean updateProfile(RegisterDoctorRequest registerDoctorRequest);

        public List<PatientMedicalRecords> getByMedicalRecordsId(Integer medicalRecordId);

        public List<PatientFeedbackDoctor> getByDoctorFeedbackId(Integer doctorFeedbackId);

        public Boolean insertMedicalRecords(PatientMedicalRecordsRequest request);

        public Boolean updateMedicalRecords(PatientMedicalRecordsRequest request);
}
