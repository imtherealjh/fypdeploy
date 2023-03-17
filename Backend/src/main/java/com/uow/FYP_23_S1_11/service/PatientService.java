package com.uow.FYP_23_S1_11.service;

import com.uow.FYP_23_S1_11.domain.request.PatientFeedbackRequest;

public interface PatientService {
    public Boolean insertFeedback(PatientFeedbackRequest feedback);
}
