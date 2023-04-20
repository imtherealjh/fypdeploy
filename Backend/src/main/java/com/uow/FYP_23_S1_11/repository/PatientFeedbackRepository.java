package com.uow.FYP_23_S1_11.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.uow.FYP_23_S1_11.domain.PatientFeedback;

public interface PatientFeedbackRepository extends JpaRepository<PatientFeedback, Integer> {

}
