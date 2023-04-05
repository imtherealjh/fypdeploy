package com.uow.FYP_23_S1_11.service;

import com.uow.FYP_23_S1_11.domain.request.GenerateAppointmentRequest;
import com.uow.FYP_23_S1_11.domain.request.GenerateClinicAppointmentRequest;

public interface ClerkService {
    public Boolean generateClinicAppointmentSlots(GenerateClinicAppointmentRequest generateClinicApptReq);
    public Boolean generateDoctorAppointmentSlots(GenerateAppointmentRequest generateDoctorApptReq);
}
