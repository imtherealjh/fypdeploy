package com.uow.FYP_23_S1_11.service;

import java.util.List;

import com.uow.FYP_23_S1_11.domain.Appointment;
import com.uow.FYP_23_S1_11.domain.Clinic;
import com.uow.FYP_23_S1_11.domain.Doctor;
import com.uow.FYP_23_S1_11.domain.EducationalMaterial;
import com.uow.FYP_23_S1_11.domain.PatientFeedbackClinic;
import com.uow.FYP_23_S1_11.domain.Specialty;
import com.uow.FYP_23_S1_11.domain.request.BookUpdateAppointmentRequest;
import com.uow.FYP_23_S1_11.domain.request.PatientFeedbackRequest;
import com.uow.FYP_23_S1_11.domain.request.PatientFeedbackClinicRequest;
import com.uow.FYP_23_S1_11.domain.request.PatientFeedbackDoctorRequest;
import com.uow.FYP_23_S1_11.domain.request.PatientFeedbackNurseRequest;
import com.uow.FYP_23_S1_11.domain.request.PatientFeedbackFrontDeskRequest;

public interface PatientService {
    public List<Specialty> getAllSpecialty();

    public List<Clinic> getAllClinicBySpecialty(String specialty);

    public List<Doctor> getAllDoctorsByClinicSpecialty(Integer clincId, String specialty);

    public List<Appointment> getDoctorAvailableAppointment(Integer doctorId, String date);

    public Boolean bookAvailableAppointment(BookUpdateAppointmentRequest bookApptReq);

    public List<Appointment> getBookedAppointments();

    public Appointment getAppointmentById(Integer apptId);

    public Boolean updateAppointment(Integer originalApptId, BookUpdateAppointmentRequest updateApptReq);

    public Boolean deleteAppointment(Integer apptId);

    public Boolean insertFeedback(PatientFeedbackRequest request);

    //
    public List<EducationalMaterial> getAllEduMaterial();

    public EducationalMaterial getEduMaterialById(Integer materialId);

    public Boolean insertClinicFeedback(PatientFeedbackClinicRequest request);

    public Boolean insertDoctorFeedback(PatientFeedbackDoctorRequest request);

    public Boolean insertNurseFeedback(PatientFeedbackNurseRequest request);

    public Boolean insertFrontDeskFeedback(PatientFeedbackFrontDeskRequest request);
}
