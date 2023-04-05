package com.uow.FYP_23_S1_11.service;

import java.util.List;

import com.uow.FYP_23_S1_11.domain.PatientMedicalRecords;
import com.uow.FYP_23_S1_11.domain.request.PatientMedicalRecordsRequest;

public interface DoctorService {
    public Boolean insertMedicalRecords(PatientMedicalRecordsRequest request);

    // public List<PatientMedicalRecords> getByPatientId(Integer patientId);
    public Boolean updateMedicalRecords(Integer medicalRecordsId,
            PatientMedicalRecordsRequest updateMedicalRecordsRequest);
}
