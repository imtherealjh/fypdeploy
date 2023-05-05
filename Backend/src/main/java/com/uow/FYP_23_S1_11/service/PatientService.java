package com.uow.FYP_23_S1_11.service;

import java.util.List;

import com.uow.FYP_23_S1_11.domain.Appointment;
import com.uow.FYP_23_S1_11.domain.Queue;
import com.uow.FYP_23_S1_11.domain.request.BookUpdateAppointmentRequest;
import com.uow.FYP_23_S1_11.domain.request.ClinicAndDoctorFeedbackRequest;
import com.uow.FYP_23_S1_11.domain.request.DoctorAvailableRequest;
import com.uow.FYP_23_S1_11.domain.request.QueueRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterPatientRequest;

public interface PatientService {
        public Object getPatientProfile();

        public List<?> getAllAppointments();

        public List<?> getAllClinicBySpecialty(String specialty);

        public List<Appointment> getDoctorAvailableAppointment(DoctorAvailableRequest req);

        public Boolean bookAvailableAppointment(BookUpdateAppointmentRequest bookApptReq);

        public Appointment getAppointmentById(Integer apptId);

        public Boolean updateAppointment(BookUpdateAppointmentRequest updateApptReq);

        public Boolean updateProfile(RegisterPatientRequest updateProfileReq);

        public Boolean deleteAppointment(Integer apptId);

        public Boolean insertClinicAndDoctorFeedback(ClinicAndDoctorFeedbackRequest request);

        public Boolean insertQueueNumber(QueueRequest request);

        public List<Queue> getByQueueId(Integer queueId);
}
