package com.uow.FYP_23_S1_11.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uow.FYP_23_S1_11.domain.PatientMedicalRecords;

public interface PatientMedicalRecordsRepository extends JpaRepository<PatientMedicalRecords, Integer> {
    List<PatientMedicalRecords> findByMedicalRecordId(Integer medicalRecordId);
}
