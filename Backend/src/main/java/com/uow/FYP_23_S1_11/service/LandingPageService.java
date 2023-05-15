package com.uow.FYP_23_S1_11.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.uow.FYP_23_S1_11.domain.Clinic;
import com.uow.FYP_23_S1_11.domain.PatientFeedbackClinic;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class LandingPageService {
        @PersistenceContext
        private EntityManager entityManager;

        public List<?> retrieveClinicFeedback() {
                TypedQuery<Object[]> clinicQuery = entityManager.createQuery(
                                "SELECT c, AVG(CAST(fc.ratings AS DOUBLE)) FROM Clinic c " +
                                                "JOIN feedbackClinic fc " +
                                                "GROUP BY c " +
                                                "HAVING AVG(fc.ratings) >= 4 " +
                                                "ORDER BY RAND() " +
                                                "LIMIT 5",
                                Object[].class);

                TypedQuery<Object[]> feedbackQuery = entityManager.createQuery(
                                "SELECT fc FROM PatientFeedbackClinic fc " +
                                                "WHERE fc.ratings >= 4 AND fc.clinicFeedback = :clinic " +
                                                "ORDER BY RAND() " +
                                                "LIMIT 1",
                                Object[].class);

                List<Map<String, Object>> allResults = clinicQuery
                                .getResultStream()
                                .map(obj -> {
                                        Map<String, Object> result = new HashMap<>();
                                        Clinic clinic = (Clinic) obj[0];
                                        Double avgRating = (Double) obj[1];

                                        feedbackQuery.setParameter("clinic", clinic);
                                        Object[] feedbackRes = feedbackQuery.getSingleResult();
                                        PatientFeedbackClinic pfc = (PatientFeedbackClinic) feedbackRes[0];

                                        result.put("clinic", clinic.getClinicName()
                                                        + " (avg rating : " + avgRating + ")");
                                        result.put("content", pfc.getFeedback());
                                        result.put("ratings", pfc.getPatientClinicFeedback().getName() + " - "
                                                        + pfc.getRatings());

                                        return result;
                                }).collect(Collectors.toList());

                return allResults;
        }
}
