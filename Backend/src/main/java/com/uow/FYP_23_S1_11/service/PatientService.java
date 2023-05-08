package com.uow.FYP_23_S1_11.service;

import java.util.List;

import com.uow.FYP_23_S1_11.domain.Appointment;
import com.uow.FYP_23_S1_11.domain.Queue;
import com.uow.FYP_23_S1_11.domain.request.ClinicAndDoctorFeedbackRequest;
import com.uow.FYP_23_S1_11.domain.request.DoctorAvailableRequest;
import com.uow.FYP_23_S1_11.domain.request.QueueRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterPatientRequest;
import com.uow.FYP_23_S1_11.domain.request.SystemFeedbackRequest;

public interface PatientService {
        public Object getPatientProfile();

        public List<?> getAllAppointments();

        public List<?> getAllClinicBySpecialty(String specialty);

        public List<Appointment> getDoctorAvailableAppointment(DoctorAvailableRequest req);

        public Boolean updateProfile(RegisterPatientRequest updateProfileReq);

        public Boolean insertClinicAndDoctorFeedback(ClinicAndDoctorFeedbackRequest request);

        public Boolean insertQueueNumber(QueueRequest request);

        public Integer getByQueueNumber(Integer queueNumber);

        public Boolean insertSystemFeedback(SystemFeedbackRequest request);

        public Boolean updateSystemFeedback(Integer systemFeedbackId,
                        SystemFeedbackRequest request);

        public Boolean deleteSystemFeedback(Integer systemFeedbackId);
}
