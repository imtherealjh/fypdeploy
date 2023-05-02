package com.uow.FYP_23_S1_11.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uow.FYP_23_S1_11.Constants;
import com.uow.FYP_23_S1_11.domain.Appointment;
import com.uow.FYP_23_S1_11.domain.Clinic;
import com.uow.FYP_23_S1_11.domain.Patient;
import com.uow.FYP_23_S1_11.domain.PatientMedicalRecords;
import com.uow.FYP_23_S1_11.domain.UserAccount;
import com.uow.FYP_23_S1_11.domain.request.PatientMedicalRecordsRequest;
import com.uow.FYP_23_S1_11.domain.response.AppointmentResponse;
import com.uow.FYP_23_S1_11.domain.response.PatientAppointmentDetails;
import com.uow.FYP_23_S1_11.domain.response.RetrievePatient;
import com.uow.FYP_23_S1_11.enums.EAppointmentStatus;
import com.uow.FYP_23_S1_11.enums.ERole;
import com.uow.FYP_23_S1_11.repository.AppointmentRepository;
import com.uow.FYP_23_S1_11.repository.PatientMedicalRecordsRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class StaffServiceImpl implements StaffService {
        @PersistenceContext
        private EntityManager entityManager;
        @Autowired
        private PatientMedicalRecordsRepository patientMedicalRecordsRepo;
        @Autowired
        private AppointmentRepository apptRepo;

        @Override
        public List<?> getAllPatients() {
                UserAccount user = Constants.getAuthenticatedUser();

                Clinic clinic = Optional.ofNullable(user.getClinic())
                                .orElseGet(() -> Optional.ofNullable(user.getDoctor())
                                                .map(elem -> elem.getDoctorClinic())
                                                .orElseGet(() -> Optional.ofNullable(user.getNurse())
                                                                .map(elem -> elem.getNurseClinic())
                                                                .orElseGet(() -> Optional
                                                                                .ofNullable(user.getFrontDesk())
                                                                                .map(elem -> elem.getFrontDeskClinic())
                                                                                .orElse(null))));

                TypedQuery<Patient> query = entityManager.createQuery(
                                "SELECT a.apptPatient "
                                                + "FROM Appointment a "
                                                + "LEFT JOIN a.apptDoctor d "
                                                + "WHERE a.apptPatient IS NOT NULL AND "
                                                + "d.doctorClinic = :clinic "
                                                + "GROUP BY a.apptPatient, a.apptTime",
                                Patient.class);
                query.setParameter("clinic", clinic);

                return query.getResultList();
        }

        @Override
        public Object getPatientsByDate(LocalDate date) {
                UserAccount user = Constants.getAuthenticatedUser();

                Clinic clinic = Optional.ofNullable(user.getClinic())
                                .orElseGet(() -> Optional.ofNullable(user.getDoctor())
                                                .map(elem -> elem.getDoctorClinic())
                                                .orElseGet(() -> Optional.ofNullable(user.getNurse())
                                                                .map(elem -> elem.getNurseClinic())
                                                                .orElseGet(() -> Optional
                                                                                .ofNullable(user.getFrontDesk())
                                                                                .map(elem -> elem.getFrontDeskClinic())
                                                                                .orElse(null))));

                if (clinic == null) {
                        throw new IllegalArgumentException("Clinic not able to be found");
                }

                Session session = entityManager.unwrap(Session.class);

                Clinic eagerClinic = session.get(Clinic.class, clinic.getClinicId());
                Hibernate.initialize(eagerClinic.getClinicAccount());

                session.close();

                UserAccount userAccount = eagerClinic.getClinicAccount();
                if (!userAccount.getIsEnabled()) {
                        throw new IllegalArgumentException("Clinic not able to be found");
                }

                String hsql = "SELECT "
                                + "new com.uow.FYP_23_S1_11.domain.response.PatientAppointmentDetails(a.apptPatient, a.apptTime) "
                                + "FROM Appointment a "
                                + "JOIN a.apptDoctor d "
                                + "WHERE a.apptPatient IS NOT NULL AND ";

                hsql += user.getRole() == ERole.DOCTOR ? "a.apptDoctor = :doctor AND " : "";

                hsql += "a.apptClinic = :clinic AND "
                                + "a.status = :status AND "
                                + "a.apptDate = :date "
                                + "GROUP BY a.apptPatient, a.apptTime";

                TypedQuery<PatientAppointmentDetails> query = entityManager.createQuery(
                                hsql,
                                PatientAppointmentDetails.class);
                if (user.getRole() == ERole.DOCTOR) {
                        query.setParameter("doctor", user.getDoctor());
                }
                query.setParameter("clinic", clinic);
                query.setParameter("date", date);
                query.setParameter("status", EAppointmentStatus.BOOKED);

                RetrievePatient object = new RetrievePatient(query.getResultList(),
                                query.getResultList().size());
                return object;
        }

        @Override
        public List<?> checkVerifyAppointment(Integer patientId) {
                UserAccount user = Constants.getAuthenticatedUser();

                Clinic clinic = Optional.ofNullable(user.getClinic())
                                .orElseGet(() -> Optional.ofNullable(user.getDoctor())
                                                .map(elem -> elem.getDoctorClinic())
                                                .orElseGet(() -> Optional.ofNullable(user.getNurse())
                                                                .map(elem -> elem.getNurseClinic())
                                                                .orElseGet(() -> Optional
                                                                                .ofNullable(user.getFrontDesk())
                                                                                .map(elem -> elem.getFrontDeskClinic())
                                                                                .orElse(null))));

                if (clinic == null) {
                        throw new IllegalArgumentException("Clinic not able to be found");
                }

                String hsql = "SELECT A FROM Appointment A "
                                + "WHERE A.apptPatient.patientId = :patientId ";

                hsql += user.getRole() == ERole.DOCTOR ? "AND A.apptDoctor = :doctor " : "";

                hsql += "AND A.apptDate = CURRENT_DATE "
                                + "AND A.apptClinic = :clinic "
                                + "AND (A.status = 'CHECKED_IN' OR A.status = 'COMPLETED')";

                TypedQuery<Appointment> query = entityManager.createQuery(
                                hsql,
                                Appointment.class);
                query.setParameter("patientId", patientId);
                if (user.getRole() == ERole.DOCTOR) {
                        query.setParameter("doctor", user.getDoctor());
                }
                query.setParameter("clinic", clinic);

                return query.getResultList();
        }

        @Override
        public List<?> getAppointmentDetails(Integer patientId) {
                TypedQuery<AppointmentResponse> query = entityManager.createQuery(
                                "SELECT new com.uow.FYP_23_S1_11.domain.response.AppointmentResponse(A.appointmentId, A.apptDate, A.apptTime, A.apptDoctor.doctorId, A.apptDoctor.name, A.apptClinic.clinicName, A.diagnostic) "
                                                + "FROM Appointment A "
                                                + "WHERE A.apptPatient.patientId = :patientId "
                                                + "AND A.apptDate < CURRENT_DATE "
                                                + "AND A.status = 'COMPLETED' "
                                                + "ORDER BY A.apptDate DESC, A.apptTime DESC",
                                AppointmentResponse.class);
                query.setParameter("patientId", patientId);
                return query.setMaxResults(6).getResultList();
        }

        @Override
        public Boolean updateMedicalRecords(PatientMedicalRecordsRequest updateMedicalRecordsRequest) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                try {
                        Optional<PatientMedicalRecords> optionalRecords = patientMedicalRecordsRepo
                                        .findById(updateMedicalRecordsRequest.getMedicalRecordId());
                        Optional<Appointment> optionalAppt = apptRepo
                                        .findById(updateMedicalRecordsRequest.getAppointmentId());

                        if (optionalRecords.isEmpty() || optionalAppt.isEmpty()) {
                                throw new IllegalArgumentException("Medical records not found...");
                        }

                        PatientMedicalRecords origPatientMedicalRecords = optionalRecords.get();
                        Appointment origAppt = optionalAppt.get();
                        List<?> apptRecords = checkVerifyAppointment(
                                        origPatientMedicalRecords.getPatientmd().getPatientId());

                        if (!apptRecords.contains(origAppt)) {
                                throw new IllegalArgumentException("Unable to update records");
                        }

                        PatientMedicalRecords updatedRecords = (PatientMedicalRecords) mapper
                                        .convertValue(updateMedicalRecordsRequest, PatientMedicalRecords.class);
                        updatedRecords.setMedicalRecordId(origPatientMedicalRecords.getMedicalRecordId());
                        updatedRecords.setPatientmd(origPatientMedicalRecords.getPatientmd());

                        patientMedicalRecordsRepo.save(updatedRecords);

                        origAppt.setDiagnostic(updateMedicalRecordsRequest.getDiagnostic());
                        origAppt.setStatus(EAppointmentStatus.COMPLETED);

                        apptRepo.save(origAppt);

                        return true;
                } catch (IllegalArgumentException e) {
                        throw e;
                } catch (Exception e) {
                        System.out.println(e);
                        return false;
                }

        }

}
