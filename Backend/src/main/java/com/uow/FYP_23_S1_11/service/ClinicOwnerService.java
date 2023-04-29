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

    public Object getDoctorById(Integer doctorId);

    public Object getNurseById(Integer nurseId);

    public Object getClerkById(Integer clerkId);

    public Boolean registerDoctor(
            List<RegisterDoctorRequest> registerDoctorRequest);

    public Boolean registerNurse(List<RegisterNurseRequest> registerNurseReq);

    public Boolean registerFrontDesk(List<RegisterFrontDeskRequest> registerFrontDeskReq);

    public Boolean generateClinicAppointmentSlots(List<LocalDate> generateClinicAppointmentReq);

    public Boolean generateDoctorAppointmentSlots(GenerateAppointmentRequest generateDoctorApptReq);

    public List<PatientFeedbackClinic> getByClinicFeedbackId(Integer clinicFeedbackId);

    public Boolean updateDoctor(RegisterDoctorRequest registerDoctorReq);

    public Boolean updateNurse(RegisterNurseRequest registerNurseReq);

    public Boolean updateClerk(RegisterFrontDeskRequest registerFrontDeskRequest);

    public Boolean suspendDoctor(Integer id);

    public Boolean suspendNurse(Integer id);

    public Boolean suspendClerk(Integer id);

    public Boolean activateDoctor(Integer id);

    public Boolean activateNurse(Integer id);

    public Boolean activateClerk(Integer id);

}
