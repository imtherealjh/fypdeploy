package com.uow.FYP_23_S1_11.domain.request;

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
public class PatientMedicalRecordsRequest {
    @JsonProperty("medicalRecordId")
    private Integer medicalRecordId;

    @JsonProperty("height")
    @NotNull(message = "Height field is mandatory")
    private Integer height;

    @JsonProperty("weight")
    @NotNull(message = "Weight field is mandatory")
    private Integer weight;

    @JsonProperty("hospitalizedHistory")
    @NotBlank(message = "Hospitalized history field is mandatory, replace with a NIL if there's no hospitalized history")
    private String hospitalizedHistory;

    @JsonProperty("currentMedication")
    @NotBlank(message = "Current medications field is mandatory, replace with a NIL if there's no current medications")
    private String currentMedication;

    @JsonProperty("foodAllergies")
    @NotBlank(message = "Food allergies field is mandatory, replace with a NIL if there's no food allergies")
    private String foodAllergies;

    @JsonProperty("drugAllergies")
    @NotBlank(message = "Drug allergies field is mandatory, replace with a NIL if there's no drug allergies")
    private String drugAllergies;

    @JsonProperty("bloodType")
    @NotBlank(message = "Blood type field is mandatory")
    private String bloodType;

    @JsonProperty("medicalConditions")
    @NotBlank(message = "Medical conditions field is mandatory, replace with a NIL if there's no medical conditions")
    private String medicalConditions;

    @JsonProperty("emergencyContact")
    @NotBlank(message = "Emergency contact field is mandatory")
    private String emergencyContact;

    @JsonProperty("emergencyContactNumber")
    @NotNull(message = "Emergency contact number field is mandatory")
    private Integer emergencyContactNumber;

}
