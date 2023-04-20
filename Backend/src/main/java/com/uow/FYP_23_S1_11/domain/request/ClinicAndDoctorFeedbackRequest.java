package com.uow.FYP_23_S1_11.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClinicAndDoctorFeedbackRequest {
    @JsonProperty("feedbackId")
    private Integer feedbackId;

    @JsonProperty("clinicRatings")
    @Min(value = 1, message = "Value should be greater then then equal to 1")
    @Max(value = 5, message = "Value should be less then then equal to 5")
    private Integer clinicRatings;

    @JsonProperty("clinicFeedback")
    private String clinicFeedback;

    @JsonProperty("doctorRatings")
    @Min(value = 1, message = "Value should be greater then then equal to 1")
    @Max(value = 5, message = "Value should be less then then equal to 5")
    private Integer doctorRatings;

    @JsonProperty("doctorFeedback")
    private String doctorFeedback;

    @JsonProperty("patientId")
    private Integer patientId;

    @JsonProperty("clinicId")
    private Integer clincId;

    @JsonProperty("doctorId")
    private Integer doctorId;
}
