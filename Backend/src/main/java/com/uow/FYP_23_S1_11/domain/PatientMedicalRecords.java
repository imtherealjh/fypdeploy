package com.uow.FYP_23_S1_11.domain;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private String currentIllnesses;
    private String pastIllnesses;
    private String hereditaryIllnesses;
    private String allergies;
}
