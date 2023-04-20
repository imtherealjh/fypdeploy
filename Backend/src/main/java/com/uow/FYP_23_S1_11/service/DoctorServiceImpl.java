package com.uow.FYP_23_S1_11.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.validation.FieldError;

import com.uow.FYP_23_S1_11.domain.request.PatientMedicalRecordsRequest;
import com.uow.FYP_23_S1_11.exception.MedicalRecordsNotFoundException;
import com.uow.FYP_23_S1_11.repository.PatientFeedbackDoctorRepository;
import com.uow.FYP_23_S1_11.repository.PatientMedicalRecordsRepository;
import com.uow.FYP_23_S1_11.domain.PatientFeedbackDoctor;
import com.uow.FYP_23_S1_11.domain.PatientMedicalRecords;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private PatientMedicalRecordsRepository patientMedicalRecordsRepo;

    @Autowired
    private PatientFeedbackDoctorRepository patientFeedbackDoctorRepo;

    @Override
    public Boolean insertMedicalRecords(PatientMedicalRecordsRequest request) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            PatientMedicalRecords patientMedicalRecords = (PatientMedicalRecords) mapper.convertValue(request,
                    PatientMedicalRecords.class);
            patientMedicalRecordsRepo.save(patientMedicalRecords);
            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Boolean updateMedicalRecords(Integer medicalRecordsId,
            PatientMedicalRecordsRequest updateMedicalRecordsRequest) {
        Optional<PatientMedicalRecords> originalMedicalRecord = patientMedicalRecordsRepo
                .findById(medicalRecordsId);

        if (originalMedicalRecord.isEmpty() == false) {
            PatientMedicalRecords origPatientMedicalRecords = originalMedicalRecord.get();
            origPatientMedicalRecords.setCurrentIllnesses(updateMedicalRecordsRequest.getCurrentIllnesses());
            origPatientMedicalRecords.setPastIllnesses(updateMedicalRecordsRequest.getPastIllnesses());
            origPatientMedicalRecords.setHereditaryIllnesses(updateMedicalRecordsRequest.getHereditaryIllnesses());
            origPatientMedicalRecords.setAllergies(updateMedicalRecordsRequest.getAllergies());
            patientMedicalRecordsRepo.save(origPatientMedicalRecords);
            return true;
        } else {
            throw new IllegalArgumentException("Medical records not found...");
        }

    }

    @Override
    public List<PatientMedicalRecords> getByMedicalRecordsId(Integer medicalRecordId) {
        List<PatientMedicalRecords> patientMedicalRecords = patientMedicalRecordsRepo
                .findByMedicalRecordId(medicalRecordId);
        if (patientMedicalRecords.isEmpty() == false) {
            return patientMedicalRecords;
        } else {
            throw new IllegalArgumentException("Medical records not found...");
        }
    }

    @Override
    public List<PatientFeedbackDoctor> getByDoctorFeedbackId(Integer doctorFeedbackId) {
        List<PatientFeedbackDoctor> patientFeedbackDoctor = patientFeedbackDoctorRepo
                .findByDoctorFeedbackId(doctorFeedbackId);
        if (patientFeedbackDoctor.isEmpty() == false) {
            return patientFeedbackDoctor;
        } else {
            throw new IllegalArgumentException("Feedback not found...");
        }
    }

}