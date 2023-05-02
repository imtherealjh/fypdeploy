package com.uow.FYP_23_S1_11.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uow.FYP_23_S1_11.repository.PatientFeedbackDoctorRepository;
import com.uow.FYP_23_S1_11.domain.PatientFeedbackDoctor;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class DoctorServiceImpl implements DoctorService {
    @PersistenceContext
    private EntityManager entityManager;
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

}