package com.uow.FYP_23_S1_11.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import com.uow.FYP_23_S1_11.Constants;
import com.uow.FYP_23_S1_11.domain.Appointment;
import com.uow.FYP_23_S1_11.domain.Clinic;
import com.uow.FYP_23_S1_11.domain.Doctor;
import com.uow.FYP_23_S1_11.domain.Patient;
import com.uow.FYP_23_S1_11.domain.PatientFeedbackClinic;
import com.uow.FYP_23_S1_11.domain.PatientFeedbackDoctor;
import com.uow.FYP_23_S1_11.domain.UserAccount;
import com.uow.FYP_23_S1_11.domain.request.ClinicAndDoctorFeedbackRequest;
import com.uow.FYP_23_S1_11.domain.request.DoctorAvailableRequest;
import com.uow.FYP_23_S1_11.enums.EAppointmentStatus;
import com.uow.FYP_23_S1_11.repository.AppointmentRepository;

import com.uow.FYP_23_S1_11.repository.PatientFeedbackClinicRepository;
import com.uow.FYP_23_S1_11.repository.PatientFeedbackDoctorRepository;
import com.uow.FYP_23_S1_11.repository.PatientRepository;

import com.uow.FYP_23_S1_11.domain.request.RegisterPatientRequest;
import com.uow.FYP_23_S1_11.domain.request.SearchLocReq;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private AppointmentService apptService;

    @Autowired
    private PatientRepository patientRepo;
    @Autowired
    private AppointmentRepository apptRepo;

    @Autowired
    private PatientFeedbackClinicRepository patientFeedbackClinicRepo;
    @Autowired
    private PatientFeedbackDoctorRepository patientFeedbackDoctorRepo;

    @Override
    public Object getPatientProfile() {
        UserAccount userAccount = Constants.getAuthenticatedUser();
        return userAccount.getPatient();
    }

    @Override
    public List<?> getAllAppointments() {
        UserAccount userAccount = Constants.getAuthenticatedUser();
        return apptRepo.findByApptPatient(userAccount.getPatient());
    }

    @Override
    public List<?> getAllClinicBySpecialty(String specialty) {
        TypedQuery<Clinic> query = entityManager.createQuery(
                "SELECT DISTINCT p FROM Clinic p " +
                        "JOIN FETCH p.doctor c1 " +
                        "JOIN c1.doctorAccount da " +
                        "WHERE NOT c1.doctorAppt IS EMPTY " +
                        "AND da.isEnabled = 1 " +
                        "AND p.status = 'APPROVED'" +
                        "AND c1.doctorId IN " +
                        "(SELECT c2.doctorId FROM Doctor c2 " +
                        "JOIN c2.doctorSpecialty gc " +
                        "WHERE gc.type = :specialty)",
                Clinic.class);
        query.setParameter("specialty", specialty);

        return query.getResultList();
    }

    @Override
    public Map<?, ?> getAllClinicSpecLoc(SearchLocReq searchLocReq, Pageable pageable) {
        TypedQuery<Doctor> query = entityManager.createQuery(
                "SELECT p FROM Doctor p " +
                        "JOIN p.doctorClinic dc " +
                        "JOIN p.doctorSpecialty ds " +
                        "JOIN p.doctorAccount da " +
                        "WHERE NOT p.doctorAppt IS EMPTY " +
                        "AND da.isEnabled = 1 " +
                        "AND dc.status = 'APPROVED' " +
                        "AND dc.location LIKE CONCAT('%',:location ,'%') " +
                        "AND ds.type = :specialty",
                Doctor.class);
        query.setParameter("location", searchLocReq.getLocation());
        query.setParameter("specialty", searchLocReq.getSpecialty());

        List<Map<String, Object>> data = query
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultStream()
                .map(obj -> {
                    Map<String, Object> object = new HashMap<>();
                    object.put("name", obj.getName());
                    object.put("doctorId", obj.getDoctorId());

                    Clinic clinic = obj.getDoctorClinic();
                    object.put("clinicName", clinic.getClinicName());
                    object.put("clinicLocation", clinic.getLocation());
                    object.put("clinicContact", clinic.getContactNo());
                    object.put("clinicEmail", clinic.getEmail());
                    return object;
                })
                .collect(Collectors.toList());

        TypedQuery<Long> countQuery = entityManager.createQuery(
                "SELECT COUNT(p) FROM Doctor p " +
                        "JOIN p.doctorClinic dc " +
                        "JOIN p.doctorSpecialty ds " +
                        "WHERE NOT p.doctorAppt IS EMPTY " +
                        "AND dc.status = 'APPROVED' " +
                        "AND dc.location LIKE CONCAT('%',:location ,'%') " +
                        "AND ds.type = :specialty",
                Long.class);
        query.setParameter("location", searchLocReq.getLocation());
        query.setParameter("specialty", searchLocReq.getSpecialty());

        Page<?> page = PageableExecutionUtils.getPage(data, pageable,
                () -> countQuery.getSingleResult());
        return Constants.convertToResponse(page);
    }

    @Override
    public List<Appointment> getDoctorAvailableAppointment(DoctorAvailableRequest req) {
        LocalDate parseDate = LocalDate.parse(req.getDate());
        return apptRepo.findAvailableApptByDoctorAndDay(req.getDoctorId(), EAppointmentStatus.AVAILABLE, parseDate);
    }

    @Override
    public Boolean updateProfile(RegisterPatientRequest updateProfileReq) {
        UserAccount currentUser = Constants.getAuthenticatedUser();
        Patient patient = currentUser.getPatient();

        TypedQuery<String> query = entityManager.createNamedQuery("findEmailInTables", String.class);
        query.setParameter("email", updateProfileReq.getEmail());

        List<String> results = query.getResultList();
        if (!results.isEmpty() && !patient.getEmail().equals(updateProfileReq.getEmail())) {
            throw new IllegalArgumentException("Username/Email has already been registered...");
        }

        patient.setName(updateProfileReq.getName());
        patient.setEmail(updateProfileReq.getEmail());
        patient.setAddress(updateProfileReq.getAddress());
        patient.setContactNo(updateProfileReq.getContactNo());
        patient.setEmergencyContact(updateProfileReq.getEmergencyContact());
        patient.setEmergencyContactNo(updateProfileReq.getEmergencyContactNo());

        patientRepo.save(patient);
        return true;
    }

    @Override
    public Boolean insertClinicAndDoctorFeedback(ClinicAndDoctorFeedbackRequest request) {
        try {
            Appointment origAppt = apptService.getAppointmentById(request.getAppointmentId());
            Map<?, ?> params = apptRepo.findByApptId(request.getAppointmentId());
            if (origAppt.getStatus() != EAppointmentStatus.COMPLETED) {
                throw new IllegalArgumentException("Appointment feedback cannot be inserted/updated");
            }

            PatientFeedbackClinic patientFeedbackClinic = params.get("fPC") == null ? new PatientFeedbackClinic()
                    : (PatientFeedbackClinic) params.get("fPC");
            patientFeedbackClinic.setClinicFeedback(origAppt.getApptClinic());
            patientFeedbackClinic.setPatientClinicFeedback(origAppt.getApptPatient());
            patientFeedbackClinic.setFeedback(request.getClinicFeedback());
            patientFeedbackClinic.setRatings(request.getClinicRatings());

            PatientFeedbackDoctor patientFeedbackDoctor = params.get("fPD") == null ? new PatientFeedbackDoctor()
                    : (PatientFeedbackDoctor) params.get("fPD");
            patientFeedbackDoctor.setDoctorFeedback(origAppt.getApptDoctor());
            patientFeedbackDoctor.setPatientDoctorFeedback(origAppt.getApptPatient());
            patientFeedbackDoctor.setFeedback(request.getDoctorFeedback());
            patientFeedbackDoctor.setRatings(request.getDoctorRatings());

            if (LocalDateTime.now().isAfter(patientFeedbackClinic.getLocalDateTime().plusDays(1))
                    || LocalDateTime.now().isAfter(patientFeedbackDoctor.getLocalDateTime().plusDays(1))) {
                throw new IllegalArgumentException("Appointment feedback cannot be inserted/updated");
            }

            patientFeedbackClinicRepo.save(patientFeedbackClinic);
            patientFeedbackDoctorRepo.save(patientFeedbackDoctor);

            return true;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

}
