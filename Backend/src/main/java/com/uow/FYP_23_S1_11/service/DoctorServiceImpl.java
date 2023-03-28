package com.uow.FYP_23_S1_11.service;

import org.springframework.stereotype.Service;

import com.uow.FYP_23_S1_11.domain.request.PatientMedicalRecordsRequest;
import com.uow.FYP_23_S1_11.domain.PatientMedicalRecords;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DoctorServiceImpl implements DoctorService{
    

    @Override
    public Boolean insertMedicalRecords(PatientMedicalRecordsRequest request) {
        try{
        ObjectMapper mapper = new ObjectMapper();
        PatientMedicalRecords patientMedicalRecords = (PatientMedicalRecords) mapper.convertValue(request, PatientMedicalRecords.class);
        patientMedicalRecords.setCurrentIllnesses(request.getCurrentIllnesses());
        patientMedicalRecords.setPastIllnesses(request.getPastIllnesses());
        patientMedicalRecords.setHereditaryIllnesses(request.getHereditaryIllnesses());
        patientMedicalRecords.setAllergies(request.getAllergies());
        return true;
        
    } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }    
    
}
