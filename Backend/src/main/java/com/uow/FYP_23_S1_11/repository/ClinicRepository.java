package com.uow.FYP_23_S1_11.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uow.FYP_23_S1_11.domain.Clinic;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Integer> {
    @Query("SELECT DISTINCT C FROM Clinic C WHERE C.clinicId IN (SELECT d.doctorClinic FROM Doctor d INNER JOIN d.doctorSpecialty ds where ds.type=?1)")
    List<Clinic> findBySpecialty(String specialty);
}
