package com.uow.FYP_23_S1_11.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uow.FYP_23_S1_11.Constants;
import com.uow.FYP_23_S1_11.domain.UserAccount;
import com.uow.FYP_23_S1_11.domain.request.QueueRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterFrontDeskRequest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uow.FYP_23_S1_11.domain.Clinic;
import com.uow.FYP_23_S1_11.domain.EducationalMaterial;
import com.uow.FYP_23_S1_11.domain.FrontDesk;
import com.uow.FYP_23_S1_11.domain.Queue;
import com.uow.FYP_23_S1_11.domain.request.EducationalMaterialRequest;
import com.uow.FYP_23_S1_11.repository.EduMaterialRepository;
import com.uow.FYP_23_S1_11.repository.FrontDeskRepository;
import com.uow.FYP_23_S1_11.repository.QueueRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClerkServiceImpl implements ClerkService {
    @Autowired
    private FrontDeskRepository clerkRepo;
    @Autowired
    private QueueRepository queueRepo;
    @Autowired
    private EduMaterialRepository eduMaterialRepo;

    @Override
    public Object getProfile() {
        UserAccount user = Constants.getAuthenticatedUser();
        return user.getFrontDesk();
    }

    @Override
    public Boolean updateProfile(RegisterFrontDeskRequest registerFrontDeskRequest) {
        UserAccount user = Constants.getAuthenticatedUser();

        FrontDesk clerk = user.getFrontDesk();
        clerk.setName(registerFrontDeskRequest.getName());
        clerk.setEmail(registerFrontDeskRequest.getEmail());

        clerkRepo.save(clerk);
        return true;
    }

    @Override
    public Boolean createEduMaterial(EducationalMaterialRequest request) {
        UserAccount currentUser = Constants.getAuthenticatedUser();
        Clinic clinic = currentUser.getFrontDesk().getFrontDeskClinic();

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            EducationalMaterial educationalMaterial = (EducationalMaterial) mapper.convertValue(request,
                    EducationalMaterial.class);

            educationalMaterial.setClinic(clinic);
            eduMaterialRepo.save(educationalMaterial);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Boolean deleteEduMaterial(Integer materialId) {
        try {
            UserAccount currentUser = Constants.getAuthenticatedUser();
            Optional<EducationalMaterial> materialOptional = eduMaterialRepo.findById(materialId);
            if (materialOptional.isEmpty()) {
                throw new IllegalArgumentException("Educational Material does not exist...");
            }
            EducationalMaterial eduMat = materialOptional.get();
            eduMaterialRepo.delete(eduMat);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Boolean updateEduMaterial(Integer materialId, EducationalMaterialRequest request) {
        try {
            UserAccount currentUser = Constants.getAuthenticatedUser();
            Optional<EducationalMaterial> materialOptional = eduMaterialRepo.findById(materialId);
            if (materialOptional.isEmpty()) {
                throw new IllegalArgumentException("Educational Material does not exist...");
            }
            EducationalMaterial eduMat = materialOptional.get();
            eduMat.setTitle(request.getTitle());
            eduMat.setContent(request.getContent());
            eduMaterialRepo.save(eduMat);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Boolean updateQueueNumber(Integer queueId,
            QueueRequest updateQueueRequest) {
        Optional<Queue> originalQueue = queueRepo
                .findById(queueId);

        if (originalQueue.isEmpty() == false) {
            Queue queue = originalQueue.get();
            queue.setQueueNumber(updateQueueRequest.getQueueNumber());
            queue.setStatus(updateQueueRequest.getStatus());
            queueRepo.save(queue);
            return true;
        } else {
            throw new IllegalArgumentException("Queue not found...");
        }

    }

    @Override
    public Boolean deleteQueueNumber(Integer queueId) {
        Optional<Queue> queueOptional = queueRepo
                .findById(queueId);
        if (queueOptional.isEmpty()) {
            throw new IllegalArgumentException("Queue number does not exist...");
        }
        Queue queue = queueOptional.get();
        queueRepo.delete(queue);
        return true;
    }
}
