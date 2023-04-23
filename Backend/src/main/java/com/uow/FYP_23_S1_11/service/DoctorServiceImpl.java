package com.uow.FYP_23_S1_11.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uow.FYP_23_S1_11.domain.request.PatientMedicalRecordsRequest;
import com.uow.FYP_23_S1_11.domain.response.PatientAppointmentDetails;
import com.uow.FYP_23_S1_11.domain.response.RetrieveDoctorPatient;
import com.uow.FYP_23_S1_11.enums.EAppointmentStatus;
import com.uow.FYP_23_S1_11.repository.PatientFeedbackDoctorRepository;
import com.uow.FYP_23_S1_11.repository.PatientMedicalRecordsRepository;
import com.uow.FYP_23_S1_11.Constants;
import com.uow.FYP_23_S1_11.domain.Doctor;
import com.uow.FYP_23_S1_11.domain.PatientFeedbackDoctor;
import com.uow.FYP_23_S1_11.domain.PatientMedicalRecords;
import com.uow.FYP_23_S1_11.domain.UserAccount;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    private PatientMedicalRecordsRepository patientMedicalRecordsRepo;
    @Autowired
    private PatientFeedbackDoctorRepository patientFeedbackDoctorRepo;

    @Override
    public Object getPatientsByApptDate(String apptDate) {
        try {
            UserAccount user = Constants.getAuthenticatedUser();
            Doctor doctor = user.getDoctor();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate parseDate = LocalDate.parse(apptDate, formatter);

            TypedQuery<PatientAppointmentDetails> query = entityManager.createQuery(
                    "SELECT "
                            + "new com.uow.FYP_23_S1_11.domain.response.PatientAppointmentDetails(a.apptPatient, a.apptTime) "
                            + "FROM Appointment a "
                            + "WHERE a.apptPatient IS NOT NULL AND "
                            + "a.apptDoctor = :doctor AND "
                            + "a.status = :status AND "
                            + "a.apptDate = :date "
                            + "GROUP BY a.apptPatient, a.apptTime",
                    PatientAppointmentDetails.class);
            query.setParameter("doctor", doctor);
            query.setParameter("date", parseDate);
            query.setParameter("status", EAppointmentStatus.BOOKED);

            RetrieveDoctorPatient object = new RetrieveDoctorPatient(query.getResultList(),
                    query.getResultList().size());
            return object;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public Boolean insertMedicalRecords(PatientMedicalRecordsRequest request) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            PatientMedicalRecords patientMedicalRecords = (PatientMedicalRecords) mapper.convertValue(request,
                    PatientMedicalRecords.class);
            patientMedicalRecordsRepo.save(patientMedicalRecords);
            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Boolean updateMedicalRecords(Integer medicalRecordsId,
            PatientMedicalRecordsRequest updateMedicalRecordsRequest) {
        Optional<PatientMedicalRecords> originalMedicalRecord = patientMedicalRecordsRepo
                .findById(medicalRecordsId);

        if (originalMedicalRecord.isEmpty() == false) {
            PatientMedicalRecords origPatientMedicalRecords = originalMedicalRecord.get();
            origPatientMedicalRecords.setCurrentIllnesses(updateMedicalRecordsRequest.getCurrentIllnesses());
            origPatientMedicalRecords.setPastIllnesses(updateMedicalRecordsRequest.getPastIllnesses());
            origPatientMedicalRecords.setHereditaryIllnesses(updateMedicalRecordsRequest.getHereditaryIllnesses());
            origPatientMedicalRecords.setAllergies(updateMedicalRecordsRequest.getAllergies());
            patientMedicalRecordsRepo.save(origPatientMedicalRecords);
            return true;
        } else {
            throw new IllegalArgumentException("Medical records not found...");
        }

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

}