package com.uow.FYP_23_S1_11.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "PATIENT_FEEDBACK_DOCTOR")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class PatientFeedbackDoctor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer doctorFeedbackId;
    private Integer ratings;
    private String feedback;
    private LocalDateTime localDateTime = LocalDateTime.now();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "doctorId", referencedColumnName = "doctorId")
    private Doctor doctorFeedback;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "patientId", referencedColumnName = "patientId")
    private Patient patientDoctorFeedback;
}
