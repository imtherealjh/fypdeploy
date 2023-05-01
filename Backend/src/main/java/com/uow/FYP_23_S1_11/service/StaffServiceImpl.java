package com.uow.FYP_23_S1_11.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.uow.FYP_23_S1_11.Constants;
import com.uow.FYP_23_S1_11.domain.Clinic;
import com.uow.FYP_23_S1_11.domain.Patient;
import com.uow.FYP_23_S1_11.domain.UserAccount;
import com.uow.FYP_23_S1_11.domain.response.PatientAppointmentDetails;
import com.uow.FYP_23_S1_11.domain.response.RetrievePatient;
import com.uow.FYP_23_S1_11.enums.EAppointmentStatus;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class StaffServiceImpl implements StaffService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<?> getAllPatients() {
        UserAccount user = Constants.getAuthenticatedUser();

        Clinic clinic = Optional.ofNullable(user.getClinic())
                .orElseGet(() -> Optional.ofNullable(user.getDoctor())
                        .map(elem -> elem.getDoctorClinic())
                        .orElseGet(() -> Optional.ofNullable(user.getNurse())
                                .map(elem -> elem.getNurseClinic())
                                .orElseGet(() -> Optional.ofNullable(user.getFrontDesk())
                                        .map(elem -> elem.getFrontDeskClinic()).orElse(null))));

        TypedQuery<Patient> query = entityManager.createQuery(
                "SELECT a.apptPatient "
                        + "FROM Appointment a "
                        + "LEFT JOIN a.apptDoctor d "
                        + "WHERE a.apptPatient IS NOT NULL AND "
                        + "d.doctorClinic = :clinic AND "
                        + "a.status = :status "
                        + "GROUP BY a.apptPatient, a.apptTime",
                Patient.class);
        query.setParameter("clinic", clinic);
        query.setParameter("status", EAppointmentStatus.BOOKED);
        return query.getResultList();
    }

    @Override
    public Object getPatientsByDate(LocalDate date) {
        UserAccount user = Constants.getAuthenticatedUser();

        Clinic clinic = Optional.ofNullable(user.getClinic())
                .orElseGet(() -> Optional.ofNullable(user.getDoctor())
                        .map(elem -> elem.getDoctorClinic())
                        .orElseGet(() -> Optional.ofNullable(user.getNurse())
                                .map(elem -> elem.getNurseClinic())
                                .orElseGet(() -> Optional.ofNullable(user.getFrontDesk())
                                        .map(elem -> elem.getFrontDeskClinic()).orElse(null))));

        Session session = entityManager.unwrap(Session.class);

        Clinic eagerClinic = session.get(Clinic.class, clinic.getClinicId());
        Hibernate.initialize(eagerClinic.getClinicAccount());

        session.close();

        UserAccount userAccount = eagerClinic.getClinicAccount();
        if (clinic == null || !userAccount.getIsEnabled()) {
            throw new IllegalArgumentException("Clinic not able to be found");
        }

        TypedQuery<PatientAppointmentDetails> query = entityManager.createQuery(
                "SELECT "
                        + "new com.uow.FYP_23_S1_11.domain.response.PatientAppointmentDetails(a.apptPatient, a.apptTime) "
                        + "FROM Appointment a "
                        + "LEFT JOIN a.apptDoctor d "
                        + "WHERE a.apptPatient IS NOT NULL AND "
                        + "d.doctorClinic = :clinic AND "
                        + "a.status = :status AND "
                        + "a.apptDate = :date "
                        + "GROUP BY a.apptPatient, a.apptTime",
                PatientAppointmentDetails.class);
        query.setParameter("clinic", clinic);
        query.setParameter("date", date);
        query.setParameter("status", EAppointmentStatus.BOOKED);

        RetrievePatient object = new RetrievePatient(query.getResultList(),
                query.getResultList().size());
        return object;
    }

}
