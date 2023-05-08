package com.uow.FYP_23_S1_11.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.uow.FYP_23_S1_11.domain.SystemFeedback;
import com.uow.FYP_23_S1_11.domain.request.SystemFeedbackRequest;

public interface SystemAdminService {

    public List<?> getAllClinics();

    public Object getClinicById(Integer clinicId);

    public ResponseEntity<byte[]> getClinicLicense(Integer clinicId);

    public Boolean createNewSpecialty(String specialty);

    public Boolean updateSpecialty(Integer id, String specialty);

    public Boolean deleteSpecialty(Integer id);

    public Boolean approveClinic(Integer clinicId);

    public Boolean rejectClinic(Integer clinicId);

    public Boolean suspendClinic(Integer clinicId);

    public Boolean enableClinic(Integer clinicId);

    public List<SystemFeedback> findFeedbackByRole(String role);

    public List<SystemFeedback> findFeedbackByDate(LocalDate startDate, LocalDate endDate);

    public List<SystemFeedback> findFeedbackByDateAndRole(LocalDate startDate, LocalDate endDate, String role);

    public Boolean updateSystemFeedback(Integer systemFeedbackId,
            SystemFeedbackRequest request);

    public List<?> getAllFeedbackPendingStatus();

    public List<?> getAllFeedbackCompleteStatus();
}
