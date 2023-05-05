package com.uow.FYP_23_S1_11.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.uow.FYP_23_S1_11.domain.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
    @Query(value = "SELECT p.patient_id,p.address,p.contact_no,p.dob,p.email,p.gender,p.name,p.patient_account FROM patient p INNER JOIN appointments a ON p.patient_id = a.patient_id WHERE p.patient_id = ?1 AND a.appt_date = ?2", nativeQuery = true)
    public List<Patient> findByPatientIdAndDate(Integer patientId, LocalDate date);
}
