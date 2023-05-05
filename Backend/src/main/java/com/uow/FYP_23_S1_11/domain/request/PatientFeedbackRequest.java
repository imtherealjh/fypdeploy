package com.uow.FYP_23_S1_11.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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

public class PatientFeedbackRequest {
    @NotNull
    @JsonProperty("appointmentId")
    private Integer appointmentId;

    @NotNull
    @JsonProperty("ratings")
    @Min(value = 1, message = "Value should be greater then then equal to 1")
    @Max(value = 5, message = "Value should be less then then equal to 5")
    private Integer ratings;

    @NotEmpty
    @JsonProperty("feedback")
    private String feedback;
}