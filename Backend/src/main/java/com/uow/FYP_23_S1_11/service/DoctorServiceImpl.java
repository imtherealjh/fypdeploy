package com.uow.FYP_23_S1_11.service;

import java.util.HashMap;
import java.util.List;
//import java.util.List;
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
import com.uow.FYP_23_S1_11.repository.PatientMedicalRecordsRepository;
import com.uow.FYP_23_S1_11.domain.PatientMedicalRecords;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private PatientMedicalRecordsRepository patientMedicalRecordsRepo;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

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
    public List<PatientMedicalRecords> getByMedicalRecordsId(Integer medicalRecordId) {
        return patientMedicalRecordsRepo.findByMedicalRecordId(medicalRecordId);
    }

    @Override
    public Boolean updateMedicalRecords(Integer medicalRecordsId,
            PatientMedicalRecordsRequest updateMedicalRecordsRequest) {
        try {
            Optional<PatientMedicalRecords> originalMedicalRecord = patientMedicalRecordsRepo
                    .findById(medicalRecordsId);
            PatientMedicalRecords origPatientMedicalRecords = originalMedicalRecord.get();
            origPatientMedicalRecords.setCurrentIllnesses(updateMedicalRecordsRequest.getCurrentIllnesses());
            origPatientMedicalRecords.setPastIllnesses(updateMedicalRecordsRequest.getPastIllnesses());
            origPatientMedicalRecords.setHereditaryIllnesses(updateMedicalRecordsRequest.getHereditaryIllnesses());
            origPatientMedicalRecords.setAllergies(updateMedicalRecordsRequest.getAllergies());
            patientMedicalRecordsRepo.save(origPatientMedicalRecords);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }
}