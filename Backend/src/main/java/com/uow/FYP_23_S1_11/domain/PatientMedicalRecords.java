package com.uow.FYP_23_S1_11.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PATIENT_MEDICAL_RECORDS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatientMedicalRecords implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer medicalRecordId;
    private Integer height = 0;
    private Integer weight = 0;
    private String hospitalizedHistory = "NIL";
    private String currentMedication = "NIL";
    private String foodAllergies = "NIL";
    private String drugAllergies = "NIL";
    private String bloodType = "NIL";
    private String medicalConditions = "NIL";

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "patientmd", referencedColumnName = "patientId")
    private Patient patientmd;
}
