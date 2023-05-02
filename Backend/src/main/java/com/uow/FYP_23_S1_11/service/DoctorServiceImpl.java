package com.uow.FYP_23_S1_11.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uow.FYP_23_S1_11.repository.DoctorRepository;
import com.uow.FYP_23_S1_11.repository.PatientFeedbackDoctorRepository;
import com.uow.FYP_23_S1_11.Constants;
import com.uow.FYP_23_S1_11.domain.Doctor;
import com.uow.FYP_23_S1_11.domain.PatientFeedbackDoctor;
import com.uow.FYP_23_S1_11.domain.UserAccount;
import com.uow.FYP_23_S1_11.domain.request.RegisterDoctorRequest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class DoctorServiceImpl implements DoctorService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private DoctorRepository doctorRepo;
    @Autowired
    private PatientFeedbackDoctorRepository patientFeedbackDoctorRepo;

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

    @Override
    public Object getProfile() {
        UserAccount user = Constants.getAuthenticatedUser();
        return user.getDoctor();
    }

    @Override
    public Boolean updateProfile(RegisterDoctorRequest registerDoctorRequest) {
        UserAccount user = Constants.getAuthenticatedUser();
        Doctor doctor = user.getDoctor();

        doctor.setName(registerDoctorRequest.getName());
        doctor.setEmail(registerDoctorRequest.getEmail());
        doctor.setProfile(registerDoctorRequest.getProfile());

        doctorRepo.save(doctor);
        return true;
    }

}