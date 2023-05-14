package com.uow.FYP_23_S1_11.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import com.uow.FYP_23_S1_11.repository.DoctorRepository;
import com.uow.FYP_23_S1_11.Constants;
import com.uow.FYP_23_S1_11.domain.Doctor;
import com.uow.FYP_23_S1_11.domain.PatientFeedbackDoctor;
import com.uow.FYP_23_S1_11.domain.UserAccount;
import com.uow.FYP_23_S1_11.domain.request.RegisterDoctorRequest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class DoctorServiceImpl implements DoctorService {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DoctorRepository doctorRepo;

    @Override
    public Object getProfile() {
        UserAccount user = Constants.getAuthenticatedUser();
        return user.getDoctor();
    }

    @Override
    public Boolean updateProfile(RegisterDoctorRequest registerDoctorRequest) {
        UserAccount user = Constants.getAuthenticatedUser();
        Doctor doctor = user.getDoctor();

        TypedQuery<String> query = entityManager.createNamedQuery("findEmailInTables", String.class);
        query.setParameter("email", registerDoctorRequest.getEmail());

        List<String> results = query.getResultList();
        if (!results.isEmpty() && !doctor.getEmail().equals(registerDoctorRequest.getEmail())) {
            throw new IllegalArgumentException("Username/Email has already been registered...");
        }

        doctor.setName(registerDoctorRequest.getName());
        doctor.setEmail(registerDoctorRequest.getEmail());
        doctor.setProfile(registerDoctorRequest.getProfile());

        doctorRepo.save(doctor);
        return true;
    }

    @Override
    public Map<?, ?> getDoctorFeedback(org.springframework.data.domain.Pageable pageable) {
        UserAccount user = Constants.getAuthenticatedUser();
        Doctor doctor = user.getDoctor();
        if (doctor == null) {
            throw new IllegalArgumentException("Feedback not found...");
        }

        TypedQuery<PatientFeedbackDoctor> query = entityManager.createQuery(
                "SELECT pfd FROM PatientFeedbackDoctor pfd WHERE pfd.doctorFeedback = :doctor",
                PatientFeedbackDoctor.class);
        query.setParameter("doctor", doctor);

        List<Map<String, Object>> data = query
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultStream()
                .map(obj -> {
                    Map<String, Object> object = new HashMap<>();
                    object.put("patient", obj.getPatientDoctorFeedback().getName());
                    object.put("date", obj.getLocalDateTime().toLocalDate());
                    object.put("rating", obj.getRatings());
                    object.put("feedback", obj.getFeedback());
                    return object;
                })
                .collect(Collectors.toList());

        TypedQuery<Long> countQuery = entityManager.createQuery(
                "SELECT COUNT(pfd) FROM PatientFeedbackDoctor pfd WHERE pfd.doctorFeedback = :doctor",
                Long.class);
        query.setParameter("doctor", doctor);

        Page<?> page = PageableExecutionUtils.getPage(data, pageable,
                () -> countQuery.getSingleResult());
        return Constants.convertToResponse(page);
    }
}
