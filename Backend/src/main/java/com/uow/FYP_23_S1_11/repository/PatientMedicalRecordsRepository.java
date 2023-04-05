package com.uow.FYP_23_S1_11.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uow.FYP_23_S1_11.domain.PatientMedicalRecords;

@Repository
public interface PatientMedicalRecordsRepository extends JpaRepository<PatientMedicalRecords, Integer> {
    // @Query("SELECT pmd FROM PATIENT_MEDICAL_RECORD pmd WHERE pmd.clinicId =
    // :patientId")
    // List<PatientMedicalRecords> findByPatientId(@Param("patientId") Integer
    // patientId);
}
