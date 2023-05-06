package com.uow.FYP_23_S1_11.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uow.FYP_23_S1_11.Constants;
import com.uow.FYP_23_S1_11.domain.Appointment;
import com.uow.FYP_23_S1_11.domain.Clinic;
import com.uow.FYP_23_S1_11.domain.Patient;
import com.uow.FYP_23_S1_11.domain.UserAccount;
import com.uow.FYP_23_S1_11.domain.request.BookUpdateAppointmentRequest;
import com.uow.FYP_23_S1_11.enums.EAppointmentStatus;
import com.uow.FYP_23_S1_11.repository.AppointmentRepository;
import com.uow.FYP_23_S1_11.repository.PatientRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private PatientRepository patientRepo;
    @Autowired
    private AppointmentRepository apptRepo;

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
            Appointment appt = getAppointmentById(bookApptReq.getApptId());

            if (appt == null || !verifiedAppointment(appt, EAppointmentStatus.AVAILABLE)) {
                throw new IllegalArgumentException("Appointment cannot be booked...");
            }

            UserAccount currentUser = Constants.getAuthenticatedUser();
            Patient patient = Optional.ofNullable(currentUser.getPatient())
                    .orElseGet(() -> patientRepo.findById(bookApptReq.getPatientId()).orElse(null));

            appt.setApptPatient(patient);
            appt.setStatus(EAppointmentStatus.BOOKED);
            apptRepo.save(appt);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    private boolean verifiedAppointment(Appointment verify, EAppointmentStatus status) {
        UserAccount currentUser = Constants.getAuthenticatedUser();
        Patient patient = currentUser.getPatient();

        Clinic clinic = Optional.ofNullable(currentUser.getClinic())
                .orElseGet(() -> Optional.ofNullable(currentUser.getNurse())
                        .map(elem -> elem.getNurseClinic())
                        .orElseGet(() -> Optional
                                .ofNullable(currentUser.getFrontDesk())
                                .map(elem -> elem.getFrontDeskClinic())
                                .orElse(null)));

        // check is it patient or frontdesk/nurse that is updating, if not throw error
        // if patient is editing, check if it is the correct patient
        // if frontdesk/nurse that is updating, check if the correct clinic
        return !((patient == null && clinic == null) ||
                (patient != null && patient.getPatientId() != verify.getApptPatient().getPatientId()) ||
                (clinic != null && clinic.getClinicId() != verify.getApptClinic().getClinicId() ||
                        !verify.getApptDoctor().getDoctorAccount().getIsEnabled() ||
                        !verify.getApptClinic().getClinicAccount().getIsEnabled() ||
                        verify.getStatus() != status ||
                        verify.getApptDate().isBefore(LocalDate.now())));

    }

    @Override
    public Boolean updateAppointment(BookUpdateAppointmentRequest updateApptReq) {
        try {
            Appointment origAppt = getAppointmentById(updateApptReq.getOriginalApptId());

            // validate if is the actual person updating the appointment
            // and ensure that older appointments cannot be updated
            if (origAppt == null || !verifiedAppointment(origAppt, EAppointmentStatus.BOOKED)) {
                throw new IllegalArgumentException("Appointment cannot be updated...");
            }

            if (origAppt.getAppointmentId() != updateApptReq.getApptId()) {
                bookAvailableAppointment(updateApptReq);
                removeAppointment(origAppt);
            }
            return true;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }

    public void removeAppointment(Appointment appointment) {
        appointment.setStatus(EAppointmentStatus.AVAILABLE);
        appointment.setApptPatient(null);
        apptRepo.save(appointment);
    }

    @Override
    public Boolean deleteAppointment(Integer apptId) {
        try {
            Appointment origAppt = getAppointmentById(apptId);
            if (origAppt == null || !verifiedAppointment(origAppt, EAppointmentStatus.BOOKED)) {
                throw new IllegalArgumentException("Appointment cannot be removed...");
            }

            removeAppointment(origAppt);
            return true;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
