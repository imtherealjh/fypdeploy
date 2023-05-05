package com.uow.FYP_23_S1_11.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.uow.FYP_23_S1_11.Constants;
import com.uow.FYP_23_S1_11.domain.Appointment;
import com.uow.FYP_23_S1_11.domain.Patient;
import com.uow.FYP_23_S1_11.domain.PatientFeedbackClinic;
import com.uow.FYP_23_S1_11.domain.PatientFeedbackDoctor;
import com.uow.FYP_23_S1_11.domain.Queue;
import com.uow.FYP_23_S1_11.domain.SystemFeedback;
import com.uow.FYP_23_S1_11.domain.UserAccount;
import com.uow.FYP_23_S1_11.domain.request.BookUpdateAppointmentRequest;
import com.uow.FYP_23_S1_11.domain.request.ClinicAndDoctorFeedbackRequest;
import com.uow.FYP_23_S1_11.domain.request.DoctorAvailableRequest;
import com.uow.FYP_23_S1_11.domain.request.PatientFeedbackRequest;
import com.uow.FYP_23_S1_11.enums.EAppointmentStatus;
import com.uow.FYP_23_S1_11.repository.AppointmentRepository;
import com.uow.FYP_23_S1_11.repository.ClinicRepository;
import com.uow.FYP_23_S1_11.repository.QueueRepository;
import com.uow.FYP_23_S1_11.repository.SystemFeedbackRepository;
import com.uow.FYP_23_S1_11.repository.UserAccountRepository;
import com.uow.FYP_23_S1_11.repository.PatientFeedbackClinicRepository;
import com.uow.FYP_23_S1_11.repository.PatientFeedbackDoctorRepository;
import com.uow.FYP_23_S1_11.repository.PatientRepository;

import com.uow.FYP_23_S1_11.domain.request.QueueRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterPatientRequest;
import com.uow.FYP_23_S1_11.domain.request.SystemFeedbackRequest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ClinicRepository clinicRepo;
    @Autowired
    private PatientRepository patientRepo;
    @Autowired
    private AppointmentRepository apptRepo;
    @Autowired
    private UserAccountRepository userAccRepo;
    @Autowired
    private SystemFeedbackRepository systemFeedbackRepo;

    @Autowired
    private PatientFeedbackClinicRepository patientFeedbackClinicRepo;
    @Autowired
    private PatientFeedbackDoctorRepository patientFeedbackDoctorRepo;
    @Autowired
    private QueueRepository queueRepo;

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
        return clinicRepo.findBySpecialty(specialty);
    }

    @Override
    public List<Appointment> getDoctorAvailableAppointment(DoctorAvailableRequest req) {
        LocalDate parseDate = LocalDate.parse(req.getDate());
        return apptRepo.findAvailableApptByDoctorAndDay(req.getDoctorId(), EAppointmentStatus.AVAILABLE, parseDate);
    }

    @Override
    public Appointment getAppointmentById(Integer apptId) {
        try {
            Optional<Appointment> apptOptional = apptRepo.findById(apptId);
            if (apptOptional.isEmpty()) {
                throw new IllegalArgumentException("Appointment does not exist");
            }
            return apptOptional.get();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public Boolean bookAvailableAppointment(BookUpdateAppointmentRequest bookApptReq) {
        try {
            UserAccount currentUser = Constants.getAuthenticatedUser();
            Patient patient = currentUser.getPatient();
            Appointment appt = getAppointmentById(bookApptReq.getApptId());
            // Ensure that the appointment is available and ensure that the
            // appointment date is not after today
            // ensure that the doctor is not suspended
            if (appt == null ||
                    !appt.getApptDoctor().getDoctorAccount().getIsEnabled() ||
                    !appt.getApptClinic().getClinicAccount().getIsEnabled() ||
                    appt.getStatus() != EAppointmentStatus.AVAILABLE ||
                    appt.getApptDate().isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Appointment cannot be booked...");
            }

            appt.setStatus(EAppointmentStatus.BOOKED);
            appt.setApptPatient(patient);
            apptRepo.save(appt);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    private void removeAppointment(Appointment appointment) {
        appointment.setStatus(EAppointmentStatus.AVAILABLE);
        appointment.setApptPatient(null);
        apptRepo.save(appointment);
    }

    @Override
    public Boolean updateAppointment(BookUpdateAppointmentRequest updateApptReq) {
        try {
            UserAccount currentUser = Constants.getAuthenticatedUser();
            Patient patient = currentUser.getPatient();

            Appointment origAppt = getAppointmentById(updateApptReq.getOriginalApptId());

            // validate if is the actual person updating the appointment
            // and ensure that older appointments cannot be updated
            if (origAppt == null ||
                    patient.getPatientId() != origAppt.getApptPatient().getPatientId() ||
                    !origAppt.getApptDoctor().getDoctorAccount().getIsEnabled() ||
                    !origAppt.getApptClinic().getClinicAccount().getIsEnabled() ||
                    origAppt.getStatus() != EAppointmentStatus.BOOKED ||
                    origAppt.getApptDate().isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Appointment cannot be updated...");
            }

            if (origAppt.getAppointmentId() == updateApptReq.getApptId()) {
                apptRepo.save(origAppt);
            } else {
                bookAvailableAppointment(updateApptReq);
                removeAppointment(origAppt);
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean updateProfile(RegisterPatientRequest updateProfileReq) {
        try {
            UserAccount currentUser = Constants.getAuthenticatedUser();
            Patient patient = currentUser.getPatient();

            patient.setName(updateProfileReq.getName());
            patient.setEmail(updateProfileReq.getEmail());
            patient.setAddress(updateProfileReq.getAddress());
            patient.setContactNo(updateProfileReq.getContactNo());
            patient.setEmergencyContact(updateProfileReq.getEmergencyContact());
            patient.setEmergencyContactNo(updateProfileReq.getEmergencyContactNo());

            patientRepo.save(patient);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean deleteAppointment(Integer apptId) {
        try {
            UserAccount currentUser = Constants.getAuthenticatedUser();
            Patient patient = currentUser.getPatient();

            Appointment origAppt = getAppointmentById(apptId);
            if (origAppt == null || patient.getPatientId() != origAppt.getApptPatient().getPatientId()) {
                throw new IllegalArgumentException("Appointment cannot be removed...");
            }

            removeAppointment(origAppt);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Boolean insertClinicAndDoctorFeedback(ClinicAndDoctorFeedbackRequest request) {
        try {
            Appointment origAppt = getAppointmentById(request.getAppointmentId());
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

    @Override
    public Boolean updateClinicFeedback(Integer clinicFeedbackId,
            PatientFeedbackRequest request) {
        Optional<PatientFeedbackClinic> patientFeedbackClinicOptional = patientFeedbackClinicRepo
                .findById(clinicFeedbackId);
        if (patientFeedbackClinicOptional.isEmpty()) {
            throw new IllegalArgumentException("Clinic feedback does not exist...");
        }
        PatientFeedbackClinic patientFeedbackClinic = patientFeedbackClinicOptional.get();
        patientFeedbackClinic.setRatings(request.getRatings());
        patientFeedbackClinic.setFeedback(request.getFeedback());
        patientFeedbackClinicRepo.save(patientFeedbackClinic);
        return true;
    }

    @Override
    public Boolean updateDoctorFeedback(Integer doctorFeedbackId,
            PatientFeedbackRequest request) {
        Optional<PatientFeedbackDoctor> patientFeedbackDoctorOptional = patientFeedbackDoctorRepo
                .findById(doctorFeedbackId);
        if (patientFeedbackDoctorOptional.isEmpty()) {
            throw new IllegalArgumentException("Doctor feedback does not exist...");
        }
        PatientFeedbackDoctor patientFeedbackDoctor = patientFeedbackDoctorOptional.get();
        patientFeedbackDoctor.setRatings(request.getRatings());
        patientFeedbackDoctor.setFeedback(request.getFeedback());
        patientFeedbackDoctorRepo.save(patientFeedbackDoctor);
        return true;
    }

    @Override
    public Boolean deleteClinicFeedback(Integer clinicFeedbackId) {
        Optional<PatientFeedbackClinic> patientFeedbackClinicOptional = patientFeedbackClinicRepo
                .findById(clinicFeedbackId);
        if (patientFeedbackClinicOptional.isEmpty()) {
            throw new IllegalArgumentException("Clinic feedback does not exist...");
        }
        PatientFeedbackClinic patientFeedbackClinic = patientFeedbackClinicOptional.get();
        patientFeedbackClinicRepo.delete(patientFeedbackClinic);
        return true;
    }

    @Override
    public Boolean deleteDoctorFeedback(Integer doctorFeedbackId) {
        Optional<PatientFeedbackDoctor> patientFeedbackDoctorOptional = patientFeedbackDoctorRepo
                .findById(doctorFeedbackId);
        if (patientFeedbackDoctorOptional.isEmpty()) {
            throw new IllegalArgumentException("Doctor feedback does not exist...");
        }
        PatientFeedbackDoctor patientFeedbackDoctor = patientFeedbackDoctorOptional.get();
        patientFeedbackDoctorRepo.delete(patientFeedbackDoctor);
        return true;
    }

    @Override
    public Boolean insertQueueNumber(QueueRequest request) {
        try {
            String response = request.getResponse();
            if (response.equalsIgnoreCase("yes")) {
                Integer apptId = request.getCheckAppointmentId();
                Integer patientId = request.getPatientId();
                List<Patient> patient = patientRepo
                        .findByPatientIdAndDate(patientId, LocalDate.now());
                Optional<Appointment> apptOptional = apptRepo.findById(apptId);
                Appointment appointment = apptOptional.get();
                if (patient.isEmpty() || LocalDate.now().equals(appointment.getApptDate()) == false) {
                    throw new IllegalArgumentException("Your appointment is not today...");
                } else {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                    mapper.registerModule(new JavaTimeModule());
                    Queue queue = (Queue) mapper.convertValue(request,
                            Queue.class);
                    queue.setDate(LocalDate.now());
                    queue.setTime(appointment.getApptTime());
                    queue.setStatus("WAITING_IN_QUEUE");
                    queue.setPriority("APPOINTMENT_MADE");
                    queueRepo.save(queue);
                    return true;
                }
            } else if (response.equalsIgnoreCase("no")) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                mapper.registerModule(new JavaTimeModule());
                Queue queue = (Queue) mapper.convertValue(request,
                        Queue.class);
                queue.setDate(LocalDate.now());
                queue.setTime(LocalTime.now().plusMinutes(45));
                queue.setStatus("WAITING_IN_QUEUE");
                queue.setPriority("WALK_IN_CUSTOMER");
                queueRepo.save(queue);
                return true;
            } else {
                throw new IllegalArgumentException("Please select yes or no...");
            }

        } catch (Exception e) {
            System.out.print(e);
            return false;
        }
    }

    @Override
    public List<Queue> getByQueueNumber(Integer queueNumber) {
        List<Queue> queue = queueRepo
                .findCountByQueueNumber(queueNumber);
        if (queue.isEmpty() == false) {
            return queue;
        } else {
            throw new IllegalArgumentException("Queue number not found...");
        }
    }

    @Override
    public Boolean insertSystemFeedback(SystemFeedbackRequest request) {
        try {

            SystemFeedback systemFeedback = new SystemFeedback();

            var user = userAccRepo.findByUsername(request.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found!!"));
            if (user != null) {
                systemFeedback.setAccountId(user.getAccountId());
                systemFeedback.setStatus("UNSOLVED");
                systemFeedback.setAccountType(user.getRole());
                systemFeedback.setDate(LocalDate.now());
                systemFeedback.setFeedback(request.getFeedback());
                systemFeedbackRepo.save(systemFeedback);
                return true;
            }
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
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
        systemFeedback.setFeedback(request.getFeedback());
        systemFeedbackRepo.save(systemFeedback);
        return true;
    }

    @Override
    public Boolean deleteSystemFeedback(Integer systemFeedbackId) {
        Optional<SystemFeedback> systemFeedbackOptional = systemFeedbackRepo
                .findById(systemFeedbackId);
        if (systemFeedbackOptional.isEmpty()) {
            throw new IllegalArgumentException("System feedback does not exist...");
        }
        SystemFeedback systemFeedback = systemFeedbackOptional.get();
        systemFeedbackRepo.delete(systemFeedback);
        return true;
    }

}
