package com.uow.FYP_23_S1_11.service;

import java.util.List;

import com.uow.FYP_23_S1_11.domain.Appointment;
import com.uow.FYP_23_S1_11.domain.Clinic;
import com.uow.FYP_23_S1_11.domain.Doctor;
import com.uow.FYP_23_S1_11.domain.EducationalMaterial;
import com.uow.FYP_23_S1_11.domain.Specialty;
import com.uow.FYP_23_S1_11.domain.request.BookUpdateAppointmentRequest;
import com.uow.FYP_23_S1_11.domain.request.DoctorAvailableRequest;
import com.uow.FYP_23_S1_11.domain.request.PatientFeedbackClinicRequest;
import com.uow.FYP_23_S1_11.domain.request.PatientFeedbackDoctorRequest;

import jakarta.mail.MessagingException;

//
import com.uow.FYP_23_S1_11.domain.MailDetails;
import com.uow.FYP_23_S1_11.domain.request.MailRequest;
//

public interface PatientService {

    public List<?> getUpcomingAppointments();

    public List<?> getPastAppointments();

    public List<?> getAllClinicBySpecialty(String specialty);

    public List<Doctor> getAllDoctorsByClinicSpecialty(Integer clincId, String specialty);

    public List<Appointment> getDoctorAvailableAppointment(DoctorAvailableRequest req);

    public Boolean bookAvailableAppointment(BookUpdateAppointmentRequest bookApptReq);

    public Appointment getAppointmentById(Integer apptId);

    public Boolean updateAppointment(Integer originalApptId, BookUpdateAppointmentRequest updateApptReq);

    public Boolean deleteAppointment(Integer apptId);

    public List<EducationalMaterial> getAllEduMaterial();

    public EducationalMaterial getEduMaterialById(Integer materialId);

    public String sendSimpleMail(MailRequest details);

    // public String sendMailWithAttachment(MailRequest details);

    public Boolean insertClinicFeedback(PatientFeedbackClinicRequest request);

    public Boolean insertDoctorFeedback(PatientFeedbackDoctorRequest request);
}
