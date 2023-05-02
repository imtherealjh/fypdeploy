package com.uow.FYP_23_S1_11.domain.response;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AppointmentResponse {
    private int appointmentId;
    private LocalDate apptDate;
    private LocalTime apptTime;
    private int doctorId;
    private String doctorName;
    private String clinicName;
    private String diagnostic;
}
