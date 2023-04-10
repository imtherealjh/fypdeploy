package com.uow.FYP_23_S1_11.domain;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PATIENT_FEEDBACK_CLINIC")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class PatientFeedbackClinic implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer clinicFeedbackId;
    private Integer ratings;
    private String feedback;
    private Integer clinicId;
    private Integer patientId;

    // @ManyToOne
    // @JoinColumn(name = "clinicId", referencedColumnName = "clinicId")
    // private Clinic clinic;

    // @ManyToOne
    // @JoinColumn(name = "patientId", referencedColumnName = "patientId")
    // private Patient patient;
}
