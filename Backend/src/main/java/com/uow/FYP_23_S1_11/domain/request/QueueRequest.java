package com.uow.FYP_23_S1_11.domain.request;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("queueId")
    private Integer queueId;

    @JsonProperty("date")
    // @NotNull(message = "Date field is mandatory")
    private LocalDate date;

    @JsonProperty("time")
    // @NotNull(message = "Time field is mandatory")
    private Time time;

    @JsonProperty("status")
    @NotBlank(message = "Status field is mandatory")
    private String status;

    @JsonProperty("queueNumber")
    @NotBlank(message = "Queue Number field is mandatory")
    private String queueNumber;

    @JsonProperty("checkAppointmentId")
    @NotNull(message = "Appointment id field is mandatory")
    private Integer checkAppointmentId;

    @JsonProperty("response")
    @NotNull(message = "Response field is mandatory")
    private String response;

    @JsonProperty("patientId")
    @NotNull(message = "Patient id field is mandatory")
    private Integer patientId;

    @JsonProperty("priority")
    @NotNull(message = "Priority field is mandatory")
    private String priority;
}
