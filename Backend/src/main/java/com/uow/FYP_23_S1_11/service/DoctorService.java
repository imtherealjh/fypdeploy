package com.uow.FYP_23_S1_11.service;

import java.util.List;

import com.uow.FYP_23_S1_11.domain.PatientFeedbackDoctor;
import com.uow.FYP_23_S1_11.domain.PatientMedicalRecords;
import com.uow.FYP_23_S1_11.domain.request.PatientMedicalRecordsRequest;

public interface DoctorService {
        public Object getPatientsByApptDate(String apptDate);

        public Boolean insertMedicalRecords(PatientMedicalRecordsRequest request);

        public List<PatientMedicalRecords> getByMedicalRecordsId(Integer medicalRecordsId);

        public Boolean updateMedicalRecords(Integer medicalRecordsId,
                        PatientMedicalRecordsRequest updateMedicalRecordsRequest);

        public List<PatientFeedbackDoctor> getByDoctorFeedbackId(Integer doctorFeedbackId);
}
