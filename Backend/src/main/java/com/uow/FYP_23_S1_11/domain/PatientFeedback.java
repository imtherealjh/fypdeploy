package com.uow.FYP_23_S1_11.domain;

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
@Table(name="PATIENT_FEEDBACK")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatientFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer feedbackId;
    private Integer ratings;
    private String feedback;

    @ManyToOne
    @JoinColumn(name="patientId", referencedColumnName="patientId")
    private Patient patient;
}
