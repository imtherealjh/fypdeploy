package com.uow.FYP_23_S1_11.service;

import com.uow.FYP_23_S1_11.domain.request.DoctorScheduleRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterDoctorRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterFrontDeskRequest;
import com.uow.FYP_23_S1_11.domain.request.RegisterNurseRequest;

public interface ClinicOwnerService {
    public Boolean registerDoctor(RegisterDoctorRequest registerDoctorRequest);
    public Boolean registerNurse(RegisterNurseRequest registerNurseReq);
    public Boolean registerFrontDesk(RegisterFrontDeskRequest registerFrontDeskReq);
    public Boolean insertDoctorSchedule(DoctorScheduleRequest doctorScheduleReq);
}
