package com.uow.FYP_23_S1_11.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.uow.FYP_23_S1_11.domain.request.PatientMedicalRecordsRequest;

public interface StaffService {
    public List<?> getAllPatients();

    public Object getPatientsByDate(LocalDate date);

    public Map<?, ?> getAppointmentDetails(Integer patientId);

    public Boolean updateMedicalRecords(PatientMedicalRecordsRequest updateMedicalRecordsRequest);

}
