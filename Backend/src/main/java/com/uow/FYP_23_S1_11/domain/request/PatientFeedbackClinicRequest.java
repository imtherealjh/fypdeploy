package com.uow.FYP_23_S1_11.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatientFeedbackClinicRequest {
    @JsonProperty("clinicFeedbackId")
    private Integer clinicFeedbackId;

    @JsonProperty("ratings")
    private Integer ratings;

    @JsonProperty("feedback")
    private String feedback;
}
