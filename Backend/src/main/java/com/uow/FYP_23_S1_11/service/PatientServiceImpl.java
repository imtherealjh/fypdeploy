package com.uow.FYP_23_S1_11.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uow.FYP_23_S1_11.domain.Patient;
import com.uow.FYP_23_S1_11.domain.PatientFeedback;
import com.uow.FYP_23_S1_11.domain.request.PatientFeedbackRequest;
import com.uow.FYP_23_S1_11.repository.PatientFeedbackRepository;
import com.uow.FYP_23_S1_11.repository.PatientRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {
    @Autowired private PatientFeedbackRepository patientFeedbackRepo;
    @Autowired private PatientRepository patientRepo;

    @Override
    public Boolean insertFeedback(PatientFeedbackRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            Optional<Patient> patient = patientRepo.findById(request.getPatientId());
            if(patient.isEmpty()) {
                throw new IllegalArgumentException("Invalid user");
            }

            PatientFeedback patientFeedback = (PatientFeedback) mapper.convertValue(request, PatientFeedback.class);
            patientFeedback.setPatient(patient.get());
            patientFeedbackRepo.save(patientFeedback);

            return true;
        } catch(Exception e) {
            System.out.println(e);
            return false;
        }
    }
    
}