package com.uow.FYP_23_S1_11.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uow.FYP_23_S1_11.domain.Appointment;
import com.uow.FYP_23_S1_11.domain.Patient;
import com.uow.FYP_23_S1_11.enums.EAppointmentStatus;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query("SELECT A FROM Appointment A WHERE A.apptDoctor.doctorId = :doctorId AND A.status = :status AND A.apptDate = :apptDate")
    List<Appointment> findAvailableApptByDoctorAndDay(@Param("doctorId") Integer doctorId,
            @Param("status") EAppointmentStatus status, @Param("apptDate") LocalDate date);

    List<Appointment> findByApptPatient(Patient apptPatient);

    @Query("SELECT new com.uow.FYP_23_S1_11.domain.response.AppointmentResponse(A.appointmentId, A.apptDate, A.apptTime, A.apptDoctor.doctorId, A.apptDoctor.name, A.apptClinic.name) "
            + "FROM Appointment A WHERE A.apptPatient = :patient AND "
            + "(A.apptDate < CURRENT_DATE OR (A.apptDate = CURRENT_DATE AND A.apptTime < CURRENT_TIME))")
    List<?> getPastAppointments(@Param("patient") Patient patient);

    @Query("SELECT new com.uow.FYP_23_S1_11.domain.response.AppointmentResponse(A.appointmentId, A.apptDate, A.apptTime, A.apptDoctor.doctorId, A.apptDoctor.name, A.apptClinic.name) "
            + "FROM Appointment A WHERE A.apptPatient = :patient AND "
            + "(A.apptDate > CURRENT_DATE OR (A.apptDate = CURRENT_DATE AND A.apptTime > CURRENT_TIME))")
    List<?> getUpcomingAppointments(@Param("patient") Patient patient);
}
