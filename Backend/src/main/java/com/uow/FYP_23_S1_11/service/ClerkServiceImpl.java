package com.uow.FYP_23_S1_11.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uow.FYP_23_S1_11.Constants;
import com.uow.FYP_23_S1_11.domain.Appointment;
import com.uow.FYP_23_S1_11.domain.Clinic;
import com.uow.FYP_23_S1_11.domain.Doctor;
import com.uow.FYP_23_S1_11.domain.DoctorSchedule;
import com.uow.FYP_23_S1_11.domain.UserAccount;
import com.uow.FYP_23_S1_11.domain.request.GenerateAppointmentRequest;
import com.uow.FYP_23_S1_11.domain.request.QueueRequest;
import com.uow.FYP_23_S1_11.enums.EAppointmentStatus;
import com.uow.FYP_23_S1_11.enums.EWeekdays;
import com.uow.FYP_23_S1_11.repository.AppointmentRepository;
import com.uow.FYP_23_S1_11.repository.DoctorRepository;
import com.uow.FYP_23_S1_11.repository.DoctorScheduleRepository;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uow.FYP_23_S1_11.domain.EducationalMaterial;
import com.uow.FYP_23_S1_11.domain.Queue;
import com.uow.FYP_23_S1_11.domain.request.EducationalMaterialRequest;
import com.uow.FYP_23_S1_11.repository.EduMaterialRepository;
import com.uow.FYP_23_S1_11.repository.QueueRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClerkServiceImpl implements ClerkService {
    @Autowired
    private DoctorScheduleRepository doctorScheduleRepo;
    @Autowired
    private QueueRepository queueRepo;
    @Autowired
    private EduMaterialRepository eduMaterialRepo;

    //
    @Override
    public Boolean createEduMaterial(EducationalMaterialRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            EducationalMaterial educationalMaterial = (EducationalMaterial) mapper.convertValue(request,
                    EducationalMaterial.class);
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
