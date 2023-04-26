package com.uow.FYP_23_S1_11.service;

import java.time.LocalDate;
import java.util.List;

import com.uow.FYP_23_S1_11.domain.PatientFeedbackClinic;
import com.uow.FYP_23_S1_11.domain.request.GenerateAppointmentRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterDoctorRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterFrontDeskRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterNurseRequest;

public interface ClinicOwnerService {
    public Object getVisitingPaitents(LocalDate date);

    public List<?> getAllStaffs();

    public List<?> getAllDoctors();

    public Boolean registerDoctor(
            List<RegisterDoctorRequest> registerDoctorRequest);

    public Boolean registerNurse(List<RegisterNurseRequest> registerNurseReq);

    public Boolean registerFrontDesk(List<RegisterFrontDeskRequest> registerFrontDeskReq);

    public Boolean generateClinicAppointmentSlots(List<LocalDate> generateClinicAppointmentReq);

    public Boolean generateDoctorAppointmentSlots(GenerateAppointmentRequest generateDoctorApptReq);

    public List<PatientFeedbackClinic> getByClinicFeedbackId(Integer clinicFeedbackId);
}
