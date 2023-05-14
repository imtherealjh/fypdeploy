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
                "SELECT fc, AVG(CAST(fc.ratings AS DOUBLE)) FROM Clinic c " +
                        "JOIN feedbackClinic fc " +
                        "WHERE fc.ratings >= 4 " +
                        "GROUP BY fc " +
                        "HAVING AVG(fc.ratings) >= 4 " +
                        "ORDER BY RAND() " +
                        "LIMIT 5",
                Object[].class);

        List<Map<String, Object>> allResults = clinicQuery
                .getResultStream()
                .map(obj -> {
                    Map<String, Object> result = new HashMap<>();
                    PatientFeedbackClinic fd = (PatientFeedbackClinic) obj[0];
                    Double avgRating = (Double) obj[1];

                    result.put("clinic", fd.getClinicFeedback().getClinicName()
                            + " (avg rating : " + avgRating + ")");
                    result.put("content", fd.getFeedback());
                    result.put("ratings", fd.getPatientClinicFeedback().getName() + " - " + fd.getRatings());

                    return result;
                }).collect(Collectors.toList());

        return allResults;
    }
}
