package com.uow.FYP_23_S1_11.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uow.FYP_23_S1_11.repository.DoctorRepository;
import com.uow.FYP_23_S1_11.repository.PatientFeedbackDoctorRepository;
import com.uow.FYP_23_S1_11.repository.PatientMedicalRecordsRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uow.FYP_23_S1_11.Constants;
import com.uow.FYP_23_S1_11.domain.Doctor;
import com.uow.FYP_23_S1_11.domain.PatientFeedbackDoctor;
import com.uow.FYP_23_S1_11.domain.PatientMedicalRecords;
import com.uow.FYP_23_S1_11.domain.UserAccount;
import com.uow.FYP_23_S1_11.domain.request.PatientMedicalRecordsRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterDoctorRequest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class DoctorServiceImpl implements DoctorService {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DoctorRepository doctorRepo;
    @Autowired
    private PatientFeedbackDoctorRepository patientFeedbackDoctorRepo;
    @Autowired
    private PatientMedicalRecordsRepository patientMedicalRecordsRepo;

    @Override
    public Object getProfile() {
        UserAccount user = Constants.getAuthenticatedUser();
        return user.getDoctor();
    }

    @Override
    public Boolean updateProfile(RegisterDoctorRequest registerDoctorRequest) {
        UserAccount user = Constants.getAuthenticatedUser();
        Doctor doctor = user.getDoctor();

        doctor.setName(registerDoctorRequest.getName());
        doctor.setEmail(registerDoctorRequest.getEmail());
        doctor.setProfile(registerDoctorRequest.getProfile());

        doctorRepo.save(doctor);
        return true;
    }

    @Override
    public List<PatientMedicalRecords> getByMedicalRecordsId(Integer medicalRecordId) {
        List<PatientMedicalRecords> patientMedicalRecords = patientMedicalRecordsRepo
                .findByMedicalRecordId(medicalRecordId);
        if (patientMedicalRecords.isEmpty() == false) {
            return patientMedicalRecords;
        } else {
            throw new IllegalArgumentException("Medical records not found...");
        }
    }

    @Override
    public List<PatientFeedbackDoctor> getByDoctorFeedbackId(Integer doctorFeedbackId) {
        List<PatientFeedbackDoctor> patientFeedbackDoctor = patientFeedbackDoctorRepo
                .findByDoctorFeedbackId(doctorFeedbackId);
        if (patientFeedbackDoctor.isEmpty() == false) {
            return patientFeedbackDoctor;
        } else {
            throw new IllegalArgumentException("Feedback not found...");
        }
    }

    @Override
    public Boolean insertMedicalRecords(PatientMedicalRecordsRequest request) {
        try {
            List<Doctor> doctor = doctorRepo
                    .findAppointmentAndPatientByDoctorId(request.getDoctorId());
            if (doctor.isEmpty() == false) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                PatientMedicalRecords patientMedicalRecords = (PatientMedicalRecords) mapper.convertValue(request,
                        PatientMedicalRecords.class);
                patientMedicalRecords.setMedicalRecordId(request.getPatientId());
                patientMedicalRecordsRepo.save(patientMedicalRecords);
                return true;
            } else {
                throw new IllegalArgumentException("No access to particular patient...");
            }

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Boolean updateMedicalRecords(PatientMedicalRecordsRequest request) {
        Optional<PatientMedicalRecords> originalMedicalRecord = patientMedicalRecordsRepo
                .findById(request.getMedicalRecordId());

        if (originalMedicalRecord.isEmpty()) {
            throw new IllegalArgumentException("Medical records not found...");
        }
        List<Doctor> doctor = doctorRepo
                .findAppointmentAndPatientByDoctorId(request.getDoctorId());
        if (doctor.isEmpty() == false) {
            PatientMedicalRecords origPatientMedicalRecords = originalMedicalRecord.get();
            origPatientMedicalRecords.setHeight(request.getHeight());
            origPatientMedicalRecords.setWeight(request.getWeight());
            origPatientMedicalRecords.setHospitalizedHistory(request.getHospitalizedHistory());
            origPatientMedicalRecords.setCurrentMedication(request.getCurrentMedication());
            origPatientMedicalRecords.setFoodAllergies(request.getFoodAllergies());
            origPatientMedicalRecords.setDrugAllergies(request.getDrugAllergies());
            origPatientMedicalRecords.setBloodType(request.getBloodType());
            origPatientMedicalRecords.setMedicalConditions(request.getMedicalConditions());
            origPatientMedicalRecords.setEmergencyContact(request.getEmergencyContact());
            origPatientMedicalRecords
                    .setEmergencyContactNumber(request.getEmergencyContactNumber());

            patientMedicalRecordsRepo.save(origPatientMedicalRecords);

            return true;
        } else {
            throw new IllegalArgumentException("No access to particular patient...");
        }
    }
}
