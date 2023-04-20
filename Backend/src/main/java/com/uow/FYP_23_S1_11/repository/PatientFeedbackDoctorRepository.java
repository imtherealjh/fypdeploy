package com.uow.FYP_23_S1_11.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uow.FYP_23_S1_11.domain.PatientFeedbackDoctor;

public interface PatientFeedbackDoctorRepository extends JpaRepository<PatientFeedbackDoctor, Integer> {
    List<PatientFeedbackDoctor> findByDoctorFeedbackId(Integer doctorFeedbackId);
}
