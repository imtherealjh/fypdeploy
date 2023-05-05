package com.uow.FYP_23_S1_11.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.uow.FYP_23_S1_11.Constants;
import com.uow.FYP_23_S1_11.domain.Appointment;
import com.uow.FYP_23_S1_11.domain.Clinic;
import com.uow.FYP_23_S1_11.domain.UserAccount;
import com.uow.FYP_23_S1_11.enums.ERole;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UtilService {
    @PersistenceContext
    private EntityManager entityManager;

    public List<?> checkVerifyAppointment(Integer patientId) {
        UserAccount user = Constants.getAuthenticatedUser();

        Clinic clinic = Optional.ofNullable(user.getClinic())
                .orElseGet(() -> Optional.ofNullable(user.getDoctor())
                        .map(elem -> elem.getDoctorClinic())
                        .orElseGet(() -> Optional.ofNullable(user.getNurse())
                                .map(elem -> elem.getNurseClinic())
                                .orElseGet(() -> Optional
                                        .ofNullable(user.getFrontDesk())
                                        .map(elem -> elem.getFrontDeskClinic())
                                        .orElse(null))));

        if (clinic == null) {
            throw new IllegalArgumentException("Clinic not able to be found");
        }

        String hsql = "SELECT A FROM Appointment A "
                + "WHERE A.apptPatient.patientId = :patientId ";

        hsql += user.getRole() == ERole.DOCTOR ? "AND A.apptDoctor = :doctor " : "";

        hsql += "AND A.apptDate = CURRENT_DATE "
                + "AND A.apptClinic = :clinic "
                + "AND (A.status = 'CHECKED_IN' OR A.status = 'COMPLETED')";

        TypedQuery<Appointment> query = entityManager.createQuery(
                hsql,
                Appointment.class);
        query.setParameter("patientId", patientId);
        if (user.getRole() == ERole.DOCTOR) {
            query.setParameter("doctor", user.getDoctor());
        }
        query.setParameter("clinic", clinic);

        return query.getResultList();
    }
}
