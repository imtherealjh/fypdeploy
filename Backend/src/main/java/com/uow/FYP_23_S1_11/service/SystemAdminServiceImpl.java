package com.uow.FYP_23_S1_11.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.uow.FYP_23_S1_11.domain.Clinic;
import com.uow.FYP_23_S1_11.domain.SystemFeedback;
import com.uow.FYP_23_S1_11.domain.UserAccount;
import com.uow.FYP_23_S1_11.domain.request.SystemFeedbackRequest;
import com.uow.FYP_23_S1_11.enums.EClinicStatus;
import com.uow.FYP_23_S1_11.repository.ClinicRepository;
import com.uow.FYP_23_S1_11.repository.SystemFeedbackRepository;
import com.uow.FYP_23_S1_11.repository.UserAccountRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class SystemAdminServiceImpl implements SystemAdminService {
    @Autowired
    private UserAccountRepository userAccountRepo;

    @Autowired
    private ClinicRepository clinicRepo;

    @Autowired
    private SystemFeedbackRepository systemFeedbackRepo;

    @Override
    public Object getClinicById(Integer id) {
        return clinicRepo.findByCustomObject(id);
    }

    @Override
    public List<?> getAllClinics() {
        return clinicRepo.findAll();
    }

    @Override
    public ResponseEntity<byte[]> getClinicLicense(Integer clinicId) {
        try {
            Clinic clinic = findClinicById(clinicId);
            byte[] imageData = clinic.getLicenseProof();
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageData);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occured in getClinicLicense In SystemAdminServiceImpl: {}", e);
            return null;
        }
    }

    private Clinic findClinicById(int id) {
        try {
            Optional<Clinic> clinicOptional = clinicRepo.findById(id);
            if (clinicOptional.isEmpty()) {
                throw new IllegalArgumentException("Clinic is not found...");
            }
            return clinicOptional.get();
        } catch (Exception e) {
            log.error("Error occured in findClinicById In SystemAdminServiceImpl: {}", e);
            return null;
        }
    }

    @Override
    public Boolean approveClinic(Integer clinicId) {
        try {
            Clinic clinic = findClinicById(clinicId);
            if (clinic == null || clinic.getStatus() != EClinicStatus.PENDING) {
                throw new IllegalArgumentException("Unable to approve clinic...");
            }
            clinic.setStatus(EClinicStatus.APPROVED);
            clinicRepo.save(clinic);

            UserAccount userAccount = clinic.getClinicAccount();
            userAccount.setIsEnabled(true);
            userAccountRepo.save(userAccount);

            return true;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occured in approveClinic In SystemAdminServiceImpl: {}", e);
            return false;
        }
    }

    @Override
    public Boolean rejectClinic(Integer clinicId) {
        try {
            Clinic clinic = findClinicById(clinicId);
            if (clinic == null || clinic.getStatus() != EClinicStatus.PENDING) {
                throw new IllegalArgumentException("Unable to reject clinic...");
            }
            clinic.setStatus(EClinicStatus.REJECTED);
            clinicRepo.save(clinic);

            return true;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occured in rejectClinic In SystemAdminServiceImpl: {}", e);
            return false;
        }
    }

    @Override
    public Boolean suspendClinic(Integer clinicId) {
        try {
            Clinic clinic = findClinicById(clinicId);
            if (clinic == null || clinic.getStatus() != EClinicStatus.APPROVED) {
                throw new IllegalArgumentException("Unable to suspend clinic...");
            }

            // TODO: Add functionality to remove appointments and update patient about the
            // status
            clinic.setStatus(EClinicStatus.SUSPENDED);
            clinicRepo.save(clinic);

            UserAccount userAccount = clinic.getClinicAccount();
            userAccount.setIsEnabled(false);
            userAccountRepo.save(userAccount);

            return true;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occured in suspendClinic In SystemAdminServiceImpl: {}", e);
            return false;
        }
    }

    @Override
    public Boolean enableClinic(Integer clinicId) {
        try {
            Clinic clinic = findClinicById(clinicId);
            if (clinic == null || clinic.getStatus() != EClinicStatus.SUSPENDED) {
                throw new IllegalArgumentException("Unable to enable clinic...");
            }
            clinic.setStatus(EClinicStatus.APPROVED);
            clinicRepo.save(clinic);

            UserAccount userAccount = clinic.getClinicAccount();
            userAccount.setIsEnabled(true);
            userAccountRepo.save(userAccount);

            return true;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occured in enableClinic In SystemAdminServiceImpl: {}", e);
            return false;
        }
    }

    @Override
    public List<?> getAllFeedbackPendingStatus() {
        return systemFeedbackRepo.findByPendingStatus();
    }

    @Override
    public List<?> getAllFeedbackCompleteStatus() {
        return systemFeedbackRepo.findByCompleteStatus();
    }

    public List<SystemFeedback> findFeedbackByRole(String role) {

        List<SystemFeedback> systemFeedback = systemFeedbackRepo.findByRole(role);
        if (systemFeedback.isEmpty() == true) {
            throw new IllegalArgumentException("No feedbacks found...");
        } else {
            return systemFeedback;
        }

    }

    public List<SystemFeedback> findFeedbackByDate(LocalDate startDate, LocalDate endDate) {

        List<SystemFeedback> systemFeedback = systemFeedbackRepo.findByDate(startDate, endDate);
        if (systemFeedback.isEmpty() == true) {
            throw new IllegalArgumentException("No feedbacks found...");
        } else {
            return systemFeedback;
        }

    }

    public List<SystemFeedback> findFeedbackByDateAndRole(LocalDate startDate, LocalDate endDate, String role) {

        List<SystemFeedback> systemFeedback = systemFeedbackRepo.findByDateAndRole(startDate, endDate, role);
        if (systemFeedback.isEmpty() == true) {
            throw new IllegalArgumentException("No feedbacks found...");
        } else {
            return systemFeedback;
        }

    }

    @Override
    public Boolean updateSystemFeedback(Integer systemFeedbackId,
            SystemFeedbackRequest request) {
        Optional<SystemFeedback> systemFeedbackOptional = systemFeedbackRepo
                .findById(systemFeedbackId);
        if (systemFeedbackOptional.isEmpty()) {
            throw new IllegalArgumentException("System feedback does not exist...");
        }
        SystemFeedback systemFeedback = systemFeedbackOptional.get();
        systemFeedback.setStatus(request.getStatus());
        systemFeedbackRepo.save(systemFeedback);
        return true;
    }

}
