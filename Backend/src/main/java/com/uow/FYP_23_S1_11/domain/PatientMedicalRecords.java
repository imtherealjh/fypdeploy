package com.uow.FYP_23_S1_11.domain;

import java.io.Serializable;

import jakarta.persistence.Entity;
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
    private Integer height;
    private Integer weight;
    private String hospitalizedHistory;
    private String currentMedication;
    private String foodAllergies;
    private String drugAllergies;
    private String bloodType;
    private String medicalConditions;
    private String emergencyContact;
    private Integer emergencyContactNumber;

    @OneToOne
    @JoinColumn(name = "patientmd", referencedColumnName = "patientId")
    private Patient patientmd;
}
