package com.uow.FYP_23_S1_11.domain;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="PATIENT_MEDICAL_RECORDS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class PatientMedicalRecords implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer medicalRecordId;

    @NotBlank(message = "Current illness field is mandatory, replace with a NIL if there's no current illnesses")
    private String currentIllnesses;

    @NotBlank(message = "Past illness field is mandatory, replace with a NIL if there's no past illnesses")
    private String pastIllnesses;
    
    @NotBlank(message = "Hereditary illness field is mandatory, replace with a NIL if there's no hereditary illnesses")
    private String hereditaryIllnesses;

    @NotBlank(message = "Allergies field is mandatory, replace with a NIL if there's no allergies")
    private String allergies;
}
