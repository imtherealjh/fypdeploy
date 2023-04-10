package com.uow.FYP_23_S1_11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uow.FYP_23_S1_11.domain.PatientFeedbackNurse;

@Repository
public interface PatientFeedbackNurseRepository extends JpaRepository<PatientFeedbackNurse, Integer> {

}
