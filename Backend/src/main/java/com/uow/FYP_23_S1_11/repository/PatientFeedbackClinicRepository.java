package com.uow.FYP_23_S1_11.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.uow.FYP_23_S1_11.domain.PatientFeedbackClinic;

public interface PatientFeedbackClinicRepository extends JpaRepository<PatientFeedbackClinic, Integer> {
    List<PatientFeedbackClinic> findByClinicFeedbackId(Integer clinicFeedbackId);
}
