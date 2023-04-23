package com.uow.FYP_23_S1_11.service;

import java.util.List;

import com.uow.FYP_23_S1_11.domain.PatientFeedbackClinic;
import com.uow.FYP_23_S1_11.domain.request.DoctorScheduleRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterDoctorRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterFrontDeskRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterNurseRequest;

public interface ClinicOwnerService {
    public List<?> getAllStaffs();

    public Boolean registerDoctor(
            List<RegisterDoctorRequest> registerDoctorRequest);

    public Boolean registerNurse(List<RegisterNurseRequest> registerNurseReq);

    public Boolean registerFrontDesk(List<RegisterFrontDeskRequest> registerFrontDeskReq);

    public Boolean insertDoctorSchedule(DoctorScheduleRequest doctorScheduleReq);

    public List<PatientFeedbackClinic> getByClinicFeedbackId(Integer clinicFeedbackId);
}
