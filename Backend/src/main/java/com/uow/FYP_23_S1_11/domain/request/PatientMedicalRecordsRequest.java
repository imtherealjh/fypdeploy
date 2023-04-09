package com.uow.FYP_23_S1_11.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Current illness field is mandatory, replace with a NIL if there's no current illnesses")
    private String currentIllnesses;

    @JsonProperty("pastIllnesses")
    @NotBlank(message = "Past illness field is mandatory, replace with a NIL if there's no past illnesses")
    private String pastIllnesses;

    @JsonProperty("hereditaryIllnesses")
    @NotBlank(message = "Hereditary illness field is mandatory, replace with a NIL if there's no hereditary illnesses")
    private String hereditaryIllnesses;

    @JsonProperty("allergies")
    @NotBlank(message = "Allergies field is mandatory, replace with a NIL if there's no allergies")
    private String allergies;
}
