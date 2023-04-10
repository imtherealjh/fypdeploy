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
public class PatientFeedbackFrontDeskRequest {
    @JsonProperty("frontDeskFeedbackId")
    private Integer frontDeskFeedbackId;

    @JsonProperty("ratings")
    private Integer ratings;

    @JsonProperty("feedback")
    private String feedback;

    @JsonProperty("patientId")
    private Integer patientId;

    @JsonProperty("frontDeskId")
    private Integer frontDeskId;
}
