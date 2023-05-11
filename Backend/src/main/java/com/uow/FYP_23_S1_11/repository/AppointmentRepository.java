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
                        + "A.apptDoctor.name as doctorName, A.apptClinic.clinicName as clinicName, A.status as status, fPC as fPC, fPD as fPD) "
                        + "FROM Appointment A "
                        + "JOIN A.apptPatient aP "
                        + "LEFT JOIN aP.feedbackPatientClinic fPC "
                        + "LEFT JOIN aP.feedbackPatientDoctor fPD "
                        + "WHERE A.apptPatient=:patient "
                        + "GROUP BY A.appointmentId")
        public List<?> findByApptPatient(@Param("patient") Patient apptPatient);

        @Query("SELECT new map(A.appointmentId as appointmentId, A.apptDate as apptDate, A.apptTime as apptTime, A.apptDoctor.doctorId as doctorId, "
                        + "A.apptDoctor.name as doctorName, A.apptClinic.clinicName as clinicName, A.status as status, fPC as fPC, fPD as fPD) "
                        + "FROM Appointment A "
                        + "JOIN A.apptPatient aP "
                        + "LEFT JOIN aP.feedbackPatientClinic fPC "
                        + "LEFT JOIN aP.feedbackPatientDoctor fPD "
                        + "WHERE A.appointmentId=:appointmentId "
                        + "GROUP BY A.appointmentId")
        public Map<?, ?> findByApptId(@Param("appointmentId") Integer appointmentId);

        public List<Appointment> findByApptDateAndApptDoctor(LocalDate apptDate, Doctor apptDoctor);
}
