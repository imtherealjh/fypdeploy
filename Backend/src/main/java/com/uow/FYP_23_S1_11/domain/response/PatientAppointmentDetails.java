package com.uow.FYP_23_S1_11.domain.response;

import java.time.LocalTime;

import com.uow.FYP_23_S1_11.domain.Patient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PatientAppointmentDetails {
    private Patient patient;
    private LocalTime apptTime;
}
