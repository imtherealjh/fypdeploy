package com.uow.FYP_23_S1_11.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uow.FYP_23_S1_11.domain.Clinic;
import com.uow.FYP_23_S1_11.enums.EWeekdays;

public interface ClinicRepository extends JpaRepository<Clinic, Integer> {
        @Query("SELECT NEW com.uow.FYP_23_S1_11.domain.response.CustomClinicObject(p, COUNT(c1) + COUNT(c2) + COUNT(c3)) "
                        + "FROM Clinic p "
                        + "LEFT JOIN p.doctor c1 "
                        + "LEFT JOIN p.nurse c2 "
                        + "LEFT JOIN p.frontDesk c3 "
                        + "WHERE p.clinicId = :id "
                        + "GROUP BY p.clinicId")
        public Object findByCustomObject(@Param("id") Integer id);

        @Query("SELECT DISTINCT p FROM Clinic p " +
                        "JOIN FETCH p.doctor c1 " +
                        "JOIN c1.doctorSchedule gc1 " +
                        "WHERE p.status = 'APPROVED' " +
                        "AND gc1.day = :day " +
                        "AND NOT EXISTS " +
                        "(SELECT 1 FROM Appointment a " +
                        "JOIN a.apptDoctor gc2 " +
                        "WHERE a.apptDate = :apptDate AND a.apptDoctor = c1 AND gc2.doctorClinic = p)")
        public List<Clinic> findByClinicsWithSchedule(@Param("apptDate") LocalDate apptDate,
                        @Param("day") EWeekdays day);

}
