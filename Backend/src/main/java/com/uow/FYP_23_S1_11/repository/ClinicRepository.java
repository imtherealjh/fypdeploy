package com.uow.FYP_23_S1_11.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uow.FYP_23_S1_11.domain.Clinic;

public interface ClinicRepository extends JpaRepository<Clinic, Integer> {
    @Query("SELECT NEW com.uow.FYP_23_S1_11.domain.response.CustomClinicObject(p, COUNT(c1) + COUNT(c2) + COUNT(c3)) FROM Clinic p " +
     "LEFT JOIN p.doctor c1 " +
     "LEFT JOIN p.nurse c2 " + 
     "LEFT JOIN p.frontDesk c3 " +
     "WHERE p.clinicId = :id " +
     "GROUP BY p.clinicId")
    public Object findByCustomObject(@Param("id") Integer id);
    @Query("SELECT DISTINCT p FROM Clinic p " +
            "JOIN FETCH p.doctor c1 " +
            "WHERE c1.doctorId IN " +
                "(SELECT c2.doctorId FROM Doctor c2 " + 
                "JOIN c2.doctorSpecialty gc " + 
                "WHERE gc.type = :specialty)")
    public List<?> findBySpecialty(@Param("specialty") String specialty);

}
