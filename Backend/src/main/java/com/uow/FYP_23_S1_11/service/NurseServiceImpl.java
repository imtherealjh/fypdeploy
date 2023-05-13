package com.uow.FYP_23_S1_11.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uow.FYP_23_S1_11.Constants;
import com.uow.FYP_23_S1_11.domain.Nurse;
import com.uow.FYP_23_S1_11.domain.UserAccount;
import com.uow.FYP_23_S1_11.domain.request.RegisterNurseRequest;
import com.uow.FYP_23_S1_11.repository.NurseRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class NurseServiceImpl implements NurseService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private NurseRepository nurseRepo;

    @Override
    public Object getProfile() {
        UserAccount user = Constants.getAuthenticatedUser();
        return user.getNurse();
    }

    @Override
    public Boolean updateProfile(RegisterNurseRequest registerNurseRequest) {
        UserAccount user = Constants.getAuthenticatedUser();
        Nurse nurse = user.getNurse();

        TypedQuery<String> query = entityManager.createNamedQuery("findEmailInTables", String.class);
        query.setParameter("email", registerNurseRequest.getEmail());

        List<String> results = query.getResultList();
        if (!results.isEmpty() && !nurse.getEmail().equals(registerNurseRequest.getEmail())) {
            throw new IllegalArgumentException("Username/Email has already been registered...");
        }

        nurse.setName(registerNurseRequest.getName());
        nurse.setEmail(registerNurseRequest.getEmail());

        nurseRepo.save(nurse);
        return true;
    }

}
