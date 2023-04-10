package com.uow.FYP_23_S1_11.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uow.FYP_23_S1_11.Constants;
import com.uow.FYP_23_S1_11.domain.Appointment;
import com.uow.FYP_23_S1_11.domain.Clinic;
import com.uow.FYP_23_S1_11.domain.Doctor;
import com.uow.FYP_23_S1_11.domain.Patient;
import com.uow.FYP_23_S1_11.domain.PatientFeedback;
import com.uow.FYP_23_S1_11.domain.PatientFeedbackClinic;
import com.uow.FYP_23_S1_11.domain.PatientFeedbackDoctor;
import com.uow.FYP_23_S1_11.domain.PatientFeedbackNurse;
import com.uow.FYP_23_S1_11.domain.PatientFeedbackFrontDesk;
import com.uow.FYP_23_S1_11.domain.Specialty;
import com.uow.FYP_23_S1_11.domain.UserAccount;
import com.uow.FYP_23_S1_11.domain.request.BookUpdateAppointmentRequest;
import com.uow.FYP_23_S1_11.domain.request.PatientFeedbackRequest;
import com.uow.FYP_23_S1_11.enums.EAppointmentStatus;
import com.uow.FYP_23_S1_11.repository.AppointmentRepository;
import com.uow.FYP_23_S1_11.repository.ClinicRepository;
import com.uow.FYP_23_S1_11.repository.DoctorRepository;
import com.uow.FYP_23_S1_11.repository.PatientFeedbackRepository;
import com.uow.FYP_23_S1_11.repository.PatientRepository;
import com.uow.FYP_23_S1_11.repository.SpecialtyRepository;

//
import com.uow.FYP_23_S1_11.repository.EduMaterialRepository;
import com.uow.FYP_23_S1_11.repository.PatientFeedbackClinicRepository;
import com.uow.FYP_23_S1_11.repository.PatientFeedbackDoctorRepository;
import com.uow.FYP_23_S1_11.repository.PatientFeedbackNurseRepository;
import com.uow.FYP_23_S1_11.repository.PatientFeedbackFrontDeskRepository;
import com.uow.FYP_23_S1_11.domain.EducationalMaterial;

import com.uow.FYP_23_S1_11.domain.request.PatientFeedbackClinicRequest;
import com.uow.FYP_23_S1_11.domain.request.PatientFeedbackDoctorRequest;
import com.uow.FYP_23_S1_11.domain.request.PatientFeedbackNurseRequest;
import com.uow.FYP_23_S1_11.domain.request.PatientFeedbackFrontDeskRequest;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientFeedbackRepository patientFeedbackRepo;
    @Autowired
    private PatientRepository patientRepo;
    @Autowired
    private SpecialtyRepository specialtyRepo;
    @Autowired
    private ClinicRepository clinicRepo;
    @Autowired
    private DoctorRepository doctorRepo;
    @Autowired
    private AppointmentRepository apptRepo;
    //
    @Autowired
    private EduMaterialRepository eduMaterialRepo;
    @Autowired
    private PatientFeedbackClinicRepository patientFeedbackClinicRepo;
    @Autowired
    private PatientFeedbackDoctorRepository patientFeedbackDoctorRepo;
    @Autowired
    private PatientFeedbackNurseRepository patientFeedbackNurseRepo;
    @Autowired
    private PatientFeedbackFrontDeskRepository patientFeedbackFrontDeskRepo;

    @Override
    public List<Specialty> getAllSpecialty() {
        return specialtyRepo.findAll();
    }

    @Override
    public List<Clinic> getAllClinicBySpecialty(String specialty) {
        return clinicRepo.findBySpecialty(specialty);
    }

    @Override
    public List<Doctor> getAllDoctorsByClinicSpecialty(Integer clincId, String specialty) {
        return doctorRepo.findByClinicSpecialty(clincId, specialty);
    }

    @Override
    public List<Appointment> getDoctorAvailableAppointment(Integer doctorId, String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate parseDate = LocalDate.parse(date, formatter);
        return apptRepo.findAvailableApptByDoctorAndDay(doctorId, EAppointmentStatus.AVAILABLE, parseDate);
    }

    @Override
    public Boolean insertFeedback(PatientFeedbackRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            Optional<Patient> patient = patientRepo.findById(request.getPatientId());
            if (patient.isEmpty()) {
                throw new IllegalArgumentException("Invalid user");
            }

            PatientFeedback patientFeedback = (PatientFeedback) mapper.convertValue(request, PatientFeedback.class);
            patientFeedback.setPatient(patient.get());
            patientFeedbackRepo.save(patientFeedback);

            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public List<Appointment> getBookedAppointments() {
        try {
            UserAccount currentUser = Constants.getAuthenticatedUser();
            Patient patient = currentUser.getPatient();
            return apptRepo.findByApptPatient(patient);
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<Appointment>();
        }
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
            return new Appointment();
        }
    }

    @Override
    public Boolean bookAvailableAppointment(BookUpdateAppointmentRequest bookApptReq) {
        try {
            UserAccount currentUser = Constants.getAuthenticatedUser();
            Patient patient = currentUser.getPatient();
            // TODO: need to check if current appointment is booked
            Optional<Appointment> apptOptional = apptRepo.findById(bookApptReq.getApptId());
            if (apptOptional.isEmpty()) {
                throw new IllegalArgumentException("No available appointment...");
            }
            Appointment appt = apptOptional.get();
            appt.setStatus(EAppointmentStatus.BOOKED);
            appt.setDescription(bookApptReq.getDescription());
            appt.setApptPatient(patient);
            apptRepo.save(appt);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Boolean updateAppointment(Integer originalApptId, BookUpdateAppointmentRequest updateApptReq) {
        try {
            UserAccount currentUser = Constants.getAuthenticatedUser();
            Patient patient = currentUser.getPatient();
            Optional<Appointment> optionalOrigAppt = apptRepo.findById(originalApptId);
            if (optionalOrigAppt.isEmpty()) {
                throw new IllegalArgumentException("Appointment not found...");
            }

            Appointment origAppt = optionalOrigAppt.get();
            if (patient.getPatientId() == origAppt.getApptPatient().getPatientId()) {
                if (originalApptId == updateApptReq.getApptId()) {
                    origAppt.setDescription(updateApptReq.getDescription());
                    apptRepo.save(origAppt);
                } else {
                    origAppt.setStatus(EAppointmentStatus.AVAILABLE);
                    origAppt.setDescription(null);
                    origAppt.setApptPatient(null);
                    apptRepo.save(origAppt);
                    bookAvailableAppointment(updateApptReq);
                }
            }

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
            Optional<Appointment> apptOptional = apptRepo.findById(apptId);
            if (apptOptional.isEmpty()) {
                throw new IllegalArgumentException("No available appointment...");
            }
            Appointment appt = apptOptional.get();
            if (patient.getPatientId() == appt.getApptPatient().getPatientId()) {
                appt.setStatus(EAppointmentStatus.AVAILABLE);
                appt.setDescription(null);
                appt.setApptPatient(null);
                apptRepo.save(appt);
            }
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    //
    @Override
    public List<EducationalMaterial> getAllEduMaterial() {
        return eduMaterialRepo.findAll();
    }

    @Override
    public EducationalMaterial getEduMaterialById(Integer materialId) {
        try {
            Optional<EducationalMaterial> materialOptional = eduMaterialRepo.findById(materialId);
            if (materialOptional.isEmpty()) {
                throw new IllegalArgumentException("Educational material does not exist..");
            }
            return materialOptional.get();
        } catch (Exception e) {
            System.out.println(e);
            return new EducationalMaterial();
        }
    }

    @Override
    public Boolean insertClinicFeedback(PatientFeedbackClinicRequest request) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            PatientFeedbackClinic patientFeedbackClinic = (PatientFeedbackClinic) mapper.convertValue(request,
                    PatientFeedbackClinic.class);
            patientFeedbackClinicRepo.save(patientFeedbackClinic);
            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Boolean insertDoctorFeedback(PatientFeedbackDoctorRequest request) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            PatientFeedbackDoctor patientFeedbackDoctor = (PatientFeedbackDoctor) mapper.convertValue(request,
                    PatientFeedbackDoctor.class);
            patientFeedbackDoctorRepo.save(patientFeedbackDoctor);
            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Boolean insertNurseFeedback(PatientFeedbackNurseRequest request) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            PatientFeedbackNurse patientFeedbackNurse = (PatientFeedbackNurse) mapper.convertValue(request,
                    PatientFeedbackNurse.class);
            patientFeedbackNurseRepo.save(patientFeedbackNurse);
            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Boolean insertFrontDeskFeedback(PatientFeedbackFrontDeskRequest request) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            PatientFeedbackFrontDesk patientFeedbackFrontDesk = (PatientFeedbackFrontDesk) mapper.convertValue(request,
                    PatientFeedbackFrontDesk.class);
            patientFeedbackFrontDeskRepo.save(patientFeedbackFrontDesk);
            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
