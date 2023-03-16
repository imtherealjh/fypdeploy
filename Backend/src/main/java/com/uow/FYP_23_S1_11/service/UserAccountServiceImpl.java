package com.uow.FYP_23_S1_11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uow.FYP_23_S1_11.domain.Clinic;
import com.uow.FYP_23_S1_11.domain.Patient;
import com.uow.FYP_23_S1_11.domain.UserAccount;
import com.uow.FYP_23_S1_11.domain.request.ClinicRegisterRequest;
import com.uow.FYP_23_S1_11.domain.request.PatientRegisterRequest;
import com.uow.FYP_23_S1_11.enums.EUserRole;
import com.uow.FYP_23_S1_11.repository.ClinicRepository;
import com.uow.FYP_23_S1_11.repository.PatientRepository;
import com.uow.FYP_23_S1_11.repository.UserAccountRepository;

@Service
@Transactional
public class UserAccountServiceImpl implements UserAccountService {
    @Autowired private UserAccountRepository userAccRepo;
    @Autowired private ClinicRepository clincRepo;
    @Autowired private PatientRepository patientRepo;

    @Override
    public Boolean registerClinicAccount(ClinicRegisterRequest clinicReq) {
        ObjectMapper mapper = new ObjectMapper();
    	mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            UserAccount newAccount = (UserAccount) mapper.convertValue(clinicReq, UserAccount.class);
            newAccount.setRole(EUserRole.PATIENT);

            UserAccount account = userAccRepo.save(newAccount);
            Clinic newClinic = (Clinic) mapper.convertValue(clinicReq, Clinic.class);
            newClinic.setClinicAccount(account);
            clincRepo.save(newClinic);

            return true;
        } catch(Exception e) {
            return false;
        }
    }

    @Override
    public Boolean registerPatientAccount(PatientRegisterRequest patientReq) {
        ObjectMapper mapper = new ObjectMapper();
    	mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            UserAccount newAccount = (UserAccount) mapper.convertValue(patientReq, UserAccount.class);
            newAccount.setRole(EUserRole.PATIENT);

            UserAccount account = userAccRepo.save(newAccount);
            Patient newPatient = (Patient) mapper.convertValue(patientReq, Patient.class);
            newPatient.setPatientAccount(account);
            patientRepo.save(newPatient);

            return true;
        } catch(Exception e) {
            return false;
        }
    }

}
