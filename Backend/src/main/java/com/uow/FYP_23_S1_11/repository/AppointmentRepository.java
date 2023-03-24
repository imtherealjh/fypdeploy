package com.uow.FYP_23_S1_11.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uow.FYP_23_S1_11.domain.Appointment;
import com.uow.FYP_23_S1_11.domain.Patient;
import com.uow.FYP_23_S1_11.enums.EAppointmentStatus;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query("SELECT A FROM Appointment A WHERE A.apptDoctor.doctorId = :doctorId AND A.status = :status AND A.apptDate = :apptDate")
    List<Appointment> findAvailableApptByDoctorAndDay(@Param("doctorId") Integer doctorId, @Param("status") EAppointmentStatus status, @Param("apptDate") LocalDate date);
    List<Appointment> findByApptPatient(Patient apptPatient);
}
