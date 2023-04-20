package com.uow.FYP_23_S1_11.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uow.FYP_23_S1_11.domain.Clinic;

public interface ClinicRepository extends JpaRepository<Clinic, Integer> {
    @Query("SELECT DISTINCT p FROM Clinic p JOIN FETCH p.doctor c1 WHERE c1.doctorId IN (SELECT c2.doctorId FROM Doctor c2 JOIN c2.doctorSpecialty gc WHERE gc.type = :specialty)")
    List<?> findBySpecialty(@Param("specialty") String specialty);
}
