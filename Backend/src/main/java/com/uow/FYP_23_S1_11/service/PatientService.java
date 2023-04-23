package com.uow.FYP_23_S1_11.service;

import java.util.List;

import com.uow.FYP_23_S1_11.domain.Appointment;
import com.uow.FYP_23_S1_11.domain.Clinic;
import com.uow.FYP_23_S1_11.domain.Doctor;
import com.uow.FYP_23_S1_11.domain.EducationalMaterial;
import com.uow.FYP_23_S1_11.domain.Specialty;
import com.uow.FYP_23_S1_11.domain.request.BookUpdateAppointmentRequest;
import com.uow.FYP_23_S1_11.domain.request.ClinicAndDoctorFeedbackRequest;
import com.uow.FYP_23_S1_11.domain.request.DoctorAvailableRequest;
import com.uow.FYP_23_S1_11.domain.request.PatientFeedbackClinicRequest;
import com.uow.FYP_23_S1_11.domain.request.PatientFeedbackDoctorRequest;
import com.uow.FYP_23_S1_11.domain.request.QueueRequest;

import jakarta.mail.MessagingException;

//
import com.uow.FYP_23_S1_11.domain.MailDetails;
import com.uow.FYP_23_S1_11.domain.request.MailRequest;
//

public interface PatientService {

        public List<?> getUpcomingAppointments();

        public List<?> getPastAppointments();

        public List<?> getAllClinicBySpecialty(String specialty);

        public List<Appointment> getDoctorAvailableAppointment(DoctorAvailableRequest req);

        public Boolean bookAvailableAppointment(BookUpdateAppointmentRequest bookApptReq);

        public Appointment getAppointmentById(Integer apptId);

        public Boolean updateAppointment(BookUpdateAppointmentRequest updateApptReq);

        public Boolean deleteAppointment(Integer apptId);

        public List<EducationalMaterial> getAllEduMaterial();

        public EducationalMaterial getEduMaterialById(Integer materialId);

        public String sendSimpleMail(MailRequest details);

        // public String sendMailWithAttachment(MailRequest details);

        public Boolean insertClinicFeedback(PatientFeedbackClinicRequest request);

        public Boolean insertDoctorFeedback(PatientFeedbackDoctorRequest request);

        public Boolean insertClinicAndDoctorFeedback(ClinicAndDoctorFeedbackRequest request);

        public Boolean updateClinicFeedback(Integer clinicFeedbackId,
                        PatientFeedbackClinicRequest updateClinicFeedbackRequest);

        public Boolean updateDoctorFeedback(Integer doctorFeedbackId,
                        PatientFeedbackDoctorRequest updateDoctorFeedbackRequest);

        public Boolean deleteClinicFeedback(Integer clinicFeedbackId);

        public Boolean deleteDoctorFeedback(Integer doctorFeedbackId);

        public Boolean insertQueueNumber(QueueRequest request);
}
