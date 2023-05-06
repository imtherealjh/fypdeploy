package com.uow.FYP_23_S1_11.service;

import com.uow.FYP_23_S1_11.domain.Appointment;
import com.uow.FYP_23_S1_11.domain.request.BookUpdateAppointmentRequest;

public interface AppointmentService {
    public Appointment getAppointmentById(Integer apptId);

    public Boolean bookAvailableAppointment(BookUpdateAppointmentRequest bookApptReq);

    public Boolean updateAppointment(BookUpdateAppointmentRequest updateApptReq);

    public Boolean deleteAppointment(Integer apptId);
}
