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
public class PatientMedicalRecordsRequest {
    @JsonProperty("medicalRecordId")
    private Integer medicalRecordId;
    @JsonProperty("currentIllnesses")
    private String currentIllnesses;
    @JsonProperty("pastIllnesses")
    private String pastIllnesses;
    @JsonProperty("hereditaryIllnesses")
    private String hereditaryIllnesses;
    @JsonProperty("allergies")
    private String allergies;
}
