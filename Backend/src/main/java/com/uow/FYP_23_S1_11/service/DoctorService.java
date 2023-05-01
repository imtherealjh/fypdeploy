package com.uow.FYP_23_S1_11.service;

import java.time.LocalDate;
import java.util.List;

import com.uow.FYP_23_S1_11.domain.PatientFeedbackDoctor;
import com.uow.FYP_23_S1_11.domain.PatientMedicalRecords;
import com.uow.FYP_23_S1_11.domain.request.PatientMedicalRecordsRequest;

public interface DoctorService {
        public Object getPatientsByApptDate(LocalDate apptDate);

        public Boolean insertMedicalRecords(PatientMedicalRecordsRequest request);

        public List<PatientMedicalRecords> getByMedicalRecordsId(Integer medicalRecordsId);

        public Boolean updateMedicalRecords(PatientMedicalRecordsRequest updateMedicalRecordsRequest);

        public List<PatientFeedbackDoctor> getByDoctorFeedbackId(Integer doctorFeedbackId);
}
