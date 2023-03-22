package com.uow.FYP_23_S1_11.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.uow.FYP_23_S1_11.Constants;
import com.uow.FYP_23_S1_11.domain.Clinic;
import com.uow.FYP_23_S1_11.domain.Doctor;
import com.uow.FYP_23_S1_11.domain.DoctorSchedule;
import com.uow.FYP_23_S1_11.domain.FrontDesk;
import com.uow.FYP_23_S1_11.domain.Nurse;
import com.uow.FYP_23_S1_11.domain.Specialty;
import com.uow.FYP_23_S1_11.domain.UserAccount;
import com.uow.FYP_23_S1_11.domain.request.DoctorScheduleRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterDoctorRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterFrontDeskRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterNurseRequest;
import com.uow.FYP_23_S1_11.enums.ERole;
import com.uow.FYP_23_S1_11.repository.DoctorRepository;
import com.uow.FYP_23_S1_11.repository.DoctorScheduleRepository;
import com.uow.FYP_23_S1_11.repository.FrontDeskRepository;
import com.uow.FYP_23_S1_11.repository.NurseRepository;
import com.uow.FYP_23_S1_11.repository.SpecialtyRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClinicOwnerServiceImpl implements ClinicOwnerService {
    @Autowired
    private DoctorScheduleRepository doctorScheduleRepo;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private NurseRepository nurseRepo;
    @Autowired
    private FrontDeskRepository frontDeskRepo;
    @Autowired
    private SpecialtyRepository specialtyRepo;
    @Autowired
    private UserAccountService userAccountService;

    @Override
    public Boolean registerDoctor(RegisterDoctorRequest registerDoctorRequest) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            // get owner's clinic object...
            UserAccount userAccount = Constants.getAuthenticatedUser();
            Clinic clinic = userAccount.getClinic();

            UserAccount newAccount = (UserAccount) mapper.convertValue(registerDoctorRequest, UserAccount.class);
            UserAccount registeredAccount = userAccountService.registerAccount(newAccount, ERole.DOCTOR);

            Doctor newDoctor = (Doctor) mapper.convertValue(registerDoctorRequest, Doctor.class);
            List<Specialty> specialty = registerDoctorRequest.getSpecialty().stream()
                    .map(x -> specialtyRepo.findByType(x)).collect(Collectors.toList());

            newDoctor.setDoctorAccount(registeredAccount);
            newDoctor.setDoctorClinic(clinic);
            newDoctor.setDoctorSpecialty(specialty);

            doctorRepository.save(newDoctor);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Boolean registerNurse(RegisterNurseRequest registerNurseReq) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            // get owner's clinic object...
            UserAccount userAccount = Constants.getAuthenticatedUser();
            Clinic clinic = userAccount.getClinic();

            UserAccount newAccount = (UserAccount) mapper.convertValue(registerNurseReq, UserAccount.class);
            UserAccount registeredAccount = userAccountService.registerAccount(newAccount, ERole.NURSE);

            Nurse nurse = (Nurse) mapper.convertValue(registerNurseReq, Nurse.class);
            nurse.setNurseAccount(registeredAccount);
            nurse.setNurseClinic(clinic);

            nurseRepo.save(nurse);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Boolean registerFrontDesk(RegisterFrontDeskRequest registerFrontDeskReq) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            // get owner's clinic object...
            UserAccount userAccount = Constants.getAuthenticatedUser();
            Clinic clinic = userAccount.getClinic();

            UserAccount newAccount = (UserAccount) mapper.convertValue(registerFrontDeskReq, UserAccount.class);
            UserAccount registeredAccount = userAccountService.registerAccount(newAccount, ERole.FRONT_DESK);

            FrontDesk frontDesk = (FrontDesk) mapper.convertValue(registerFrontDeskReq, FrontDesk.class);
            frontDesk.setFrontDeskAccount(registeredAccount);
            frontDesk.setFrontDeskClinic(clinic);

            frontDeskRepo.save(frontDesk);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Boolean insertDoctorSchedule(DoctorScheduleRequest doctorScheduleReq) {
        //TODO: implement checker for whether doctor is the same clinic as owner
        //TODO: implement to check whether schedule conflicts with original schedule and if it is before opening hours or after closing hours
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.registerModule(new JavaTimeModule());
        try {
            Optional<Doctor> doctor = doctorRepository.findById(doctorScheduleReq.getDoctorId());
            if (doctor.isEmpty()) {
                throw new IllegalArgumentException("Doctor not found!!!");
            }
            DoctorSchedule newSchedule = (DoctorSchedule) mapper.convertValue(doctorScheduleReq, DoctorSchedule.class);
            newSchedule.setDoctor(doctor.get());
            doctorScheduleRepo.save(newSchedule);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e);
            return false;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

}
