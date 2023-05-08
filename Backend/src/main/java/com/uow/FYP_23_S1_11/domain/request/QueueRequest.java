package com.uow.FYP_23_S1_11.domain.request;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uow.FYP_23_S1_11.constraints.ValueOfEnum;
import com.uow.FYP_23_S1_11.enums.EQueueStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QueueRequest {
    @JsonProperty("date")
    // @NotNull(message = "Date field is mandatory")
    private LocalDate date;

    @JsonProperty("time")
    // @NotNull(message = "Time field is mandatory")
    private Time time;

    @ValueOfEnum(enumClass = EQueueStatus.class)
    @JsonProperty("status")
    @NotBlank(message = "Status field is mandatory")
    private String status;

    @JsonProperty("checkAppointmentId")
    @NotNull(message = "Appointment id field is mandatory")
    private Integer checkAppointmentId;

    @JsonProperty("patientId")
    @NotNull(message = "Patient id field is mandatory")
    private Integer patientId;
}
