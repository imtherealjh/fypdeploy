package com.uow.FYP_23_S1_11.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uow.FYP_23_S1_11.domain.PatientMedicalRecords;

@Repository
public interface PatientMedicalRecordsRepository extends JpaRepository<PatientMedicalRecords, Integer> {
    // @Query("SELECT * FROM PATIENT_MEDICAL_RECORD pmd left join PATIENT p on
    // pmd.medicalRecordsId = p.patientId WHERE p.patientId = :patientId")
    // @Query("SELECT pmd FROM PATIENT_MEDICAL_RECORDS pmd WHERE pmd.medicalRecordId
    // = :patientmd")
    List<PatientMedicalRecords> findByMedicalRecordId(Integer medicalRecordId);
}
