package com.uow.FYP_23_S1_11.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatientMedicalRecordsRequest {
    @NotNull
    @JsonProperty("medicalRecordId")
    private Integer medicalRecordId;

    @NotNull
    @JsonProperty("appointmentId")
    private Integer appointmentId;

    @NotNull
    @JsonProperty("patientId")
    private Integer patientId;

    @NotNull
    @JsonProperty("doctorId")
    private Integer doctorId;

    @NotBlank
    @JsonProperty("diagnostic")
    private String diagnostic;

    @NotNull
    @JsonProperty("height")
    private Integer height;

    @NotNull
    @JsonProperty("weight")
    private Integer weight;

    @NotBlank
    @JsonProperty("hospitalizedHistory")
    private String hospitalizedHistory;

    @NotBlank
    @JsonProperty("currentMedication")
    private String currentMedication;

    @NotBlank
    @JsonProperty("foodAllergies")
    private String foodAllergies;

    @NotBlank
    @JsonProperty("drugAllergies")
    private String drugAllergies;

    @NotBlank
    @JsonProperty("bloodType")
    private String bloodType;

    @NotBlank
    @JsonProperty("medicalConditions")
    private String medicalConditions;

    @NotBlank
    @JsonProperty("emergencyContact")
    private String emergencyContact;

    @NotNull
    @JsonProperty("emergencyContactNumber")
    private Integer emergencyContactNumber;
}
