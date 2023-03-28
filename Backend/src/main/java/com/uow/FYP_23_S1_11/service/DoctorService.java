package com.uow.FYP_23_S1_11.service;

import com.uow.FYP_23_S1_11.domain.request.PatientMedicalRecordsRequest;

public interface DoctorService {
    public Boolean insertMedicalRecords(PatientMedicalRecordsRequest request);
}
