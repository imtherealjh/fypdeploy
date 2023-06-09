package com.uow.FYP_23_S1_11.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uow.FYP_23_S1_11.domain.Appointment;
import com.uow.FYP_23_S1_11.domain.Doctor;
import com.uow.FYP_23_S1_11.domain.Patient;
import com.uow.FYP_23_S1_11.enums.EAppointmentStatus;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
        @Query("SELECT A FROM Appointment A WHERE A.apptDoctor.doctorId = :doctorId AND A.status = :status AND A.apptDate = :apptDate")
        public List<Appointment> findAvailableApptByDoctorAndDay(@Param("doctorId") Integer doctorId,
                        @Param("status") EAppointmentStatus status, @Param("apptDate") LocalDate date);

        @Query("SELECT new map(A.appointmentId as appointmentId, A.apptDate as apptDate, A.apptTime as apptTime, A.apptDoctor.doctorId as doctorId, "
                        + "A.apptDoctor.name as doctorName, A.apptClinic.clinicName as clinicName, A.status as status, A.diagnostic as diagnostic, fPC as fPC, fPD as fPD) "
                        + "FROM Appointment A "
                        + "JOIN A.apptPatient aP "
                        + "LEFT OUTER JOIN aP.feedbackPatientClinic fPC ON fPC.clinicFeedbackId = A.appointmentId "
                        + "LEFT OUTER JOIN aP.feedbackPatientDoctor fPD ON fPD.doctorFeedbackId = A.appointmentId "
                        + "WHERE A.apptPatient=:patient")
        public List<?> findByApptPatient(@Param("patient") Patient apptPatient);

        @Query("SELECT new map(A.appointmentId as appointmentId, A.apptDate as apptDate, A.apptTime as apptTime, A.apptDoctor.doctorId as doctorId, "
                        + "A.apptDoctor.name as doctorName, A.apptClinic.clinicName as clinicName, A.status as status, fPC as fPC, fPD as fPD) "
                        + "FROM Appointment A "
                        + "JOIN A.apptPatient aP "
                        + "LEFT JOIN aP.feedbackPatientClinic fPC ON fPC.clinicFeedbackId = A.appointmentId "
                        + "LEFT JOIN aP.feedbackPatientDoctor fPD ON fPD.doctorFeedbackId = A.appointmentId "
                        + "WHERE A.appointmentId=:appointmentId ")
        public Map<?, ?> findByApptId(@Param("appointmentId") Integer appointmentId);

        @Query("SELECT A FROM Appointment A WHERE A.status = 'AVAILABLE' AND A.apptDate < CURRENT_DATE")
        public List<Appointment> findByOldAvailableAppt();

        public List<Appointment> findByApptDateAndApptDoctor(LocalDate apptDate, Doctor apptDoctor);
}
