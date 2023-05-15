package com.uow.FYP_23_S1_11.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.uow.FYP_23_S1_11.Constants;
import com.uow.FYP_23_S1_11.domain.UserAccount;
import com.uow.FYP_23_S1_11.domain.request.RegisterFrontDeskRequest;
import com.uow.FYP_23_S1_11.enums.EAppointmentStatus;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uow.FYP_23_S1_11.domain.Appointment;
import com.uow.FYP_23_S1_11.domain.Clinic;
import com.uow.FYP_23_S1_11.domain.Doctor;
import com.uow.FYP_23_S1_11.domain.EducationalMaterial;
import com.uow.FYP_23_S1_11.domain.FrontDesk;
import com.uow.FYP_23_S1_11.domain.request.EducationalMaterialRequest;
import com.uow.FYP_23_S1_11.repository.AppointmentRepository;
import com.uow.FYP_23_S1_11.repository.EduMaterialRepository;
import com.uow.FYP_23_S1_11.repository.FrontDeskRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClerkServiceImpl implements ClerkService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private FrontDeskRepository clerkRepo;
    @Autowired
    private EduMaterialRepository eduMaterialRepo;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private AppointmentRepository apptRepo;

    @Override
    public Object getProfile() {
        UserAccount user = Constants.getAuthenticatedUser();
        return user.getFrontDesk();
    }

    @Override
    public List<Doctor> getDoctorList() {
        UserAccount user = Constants.getAuthenticatedUser();
        Clinic clinic = user.getFrontDesk().getFrontDeskClinic();
        TypedQuery<Doctor> query = entityManager.createQuery(
                "SELECT p FROM Doctor p " +
                        "JOIN p.doctorClinic dc " +
                        "JOIN p.doctorAccount da " +
                        "WHERE NOT p.doctorAppt IS EMPTY " +
                        "AND da.isEnabled = 1 " +
                        "AND dc.status = 'APPROVED' " +
                        "AND dc = :clinic",
                Doctor.class);
        query.setParameter("clinic", clinic);
        return query.getResultList();
    }

    @Override
    public Boolean updateProfile(RegisterFrontDeskRequest registerFrontDeskRequest) {
        UserAccount user = Constants.getAuthenticatedUser();
        FrontDesk clerk = user.getFrontDesk();

        TypedQuery<String> query = entityManager.createNamedQuery("findEmailInTables", String.class);
        query.setParameter("email", registerFrontDeskRequest.getEmail());

        List<String> results = query.getResultList();
        if (!results.isEmpty() && !clerk.getEmail().equals(registerFrontDeskRequest.getEmail())) {
            throw new IllegalArgumentException("Username/Email has already been registered...");
        }

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
    public Boolean updateEduMaterial(Integer materialId, EducationalMaterialRequest request) {
        try {
            UserAccount currentUser = Constants.getAuthenticatedUser();
            Clinic clinic = currentUser.getFrontDesk().getFrontDeskClinic();
            Optional<EducationalMaterial> materialOptional = eduMaterialRepo.findById(materialId);
            if (materialOptional.isEmpty()
                    || clinic.getClinicId() != materialOptional.get().getClinic().getClinicId()) {
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
    public Boolean checkInUser(Integer apptId) {
        Optional<Appointment> appOptional = apptRepo.findById(apptId);
        if (appOptional.isEmpty()) {
            throw new IllegalArgumentException("No appointment found...");
        }

        Appointment appt = appOptional.get();
        List<Appointment> apptList = apptRepo.findAvailableApptByDoctorAndDay(appt.getApptDoctor().getDoctorId(),
                EAppointmentStatus.CHECKED_IN, LocalDate.now());
        if (apptList.size() > 0) {
            throw new IllegalArgumentException("Unable to check in user as doctor is still attending another patient");
        }
        appt.setStatus(EAppointmentStatus.CHECKED_IN);
        apptRepo.save(appt);
        messagingTemplate.convertAndSend("/queue/" + appt.getApptDoctor().getDoctorId(), "Update On Check-in");
        return true;
    }

}
