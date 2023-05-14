package com.uow.FYP_23_S1_11.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uow.FYP_23_S1_11.domain.Appointment;
import com.uow.FYP_23_S1_11.repository.AppointmentRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class QueueService {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private AppointmentRepository apptRepo;

    public Object getAppointmentDetails(Integer apptId) {
        String hsql = "SELECT "
                + "new map(a.apptClinic.clinicName as clinic, a.apptDoctor.doctorId as doctorId, a.apptDoctor.name as doctor, a.apptTime as time) "
                + "FROM Appointment a "
                + "JOIN a.apptDoctor d "
                + "WHERE a.apptPatient IS NOT NULL AND "
                + "a.apptClinic IS NOT NULL AND "
                + "a.status = 'BOOKED' AND "
                + "a.appointmentId = :appointmentId AND "
                + "a.apptDate = :date ";

        TypedQuery<Object> query = entityManager.createQuery(hsql, Object.class);
        query.setParameter("appointmentId", apptId);
        query.setParameter("date", LocalDate.now());

        if (query.getResultList().size() < 1) {
            throw new IllegalArgumentException("Appointment is not valid / has been completed");
        }

        return query.getSingleResult();
    };

    public Object getCurrentQueueStatus(Integer apptId) {
        Optional<Appointment> apptOptional = apptRepo.findById(apptId);
        if (apptOptional.isEmpty()) {
            throw new IllegalArgumentException("Appointment not found");
        }

        Appointment appointment = apptOptional.get();

        String hsql = "SELECT new map(a.appointmentId as currentServingAppt) "
                + "FROM Appointment a "
                + "JOIN a.apptDoctor d "
                + "WHERE a.apptPatient IS NOT NULL AND "
                + "a.apptClinic IS NOT NULL AND "
                + "d = :doctor AND "
                + "a.status = 'CHECKED_IN' AND "
                + "a.apptDate = :date ";

        TypedQuery<Object> query = entityManager.createQuery(hsql, Object.class);
        query.setParameter("doctor", appointment.getApptDoctor());
        query.setParameter("date", LocalDate.now());

        return query.getSingleResult();
    }

}
