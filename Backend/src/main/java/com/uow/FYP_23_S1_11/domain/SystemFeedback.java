package com.uow.FYP_23_S1_11.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uow.FYP_23_S1_11.enums.ERole;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "SYSTEM_FEEDBACK")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class SystemFeedback implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer systemFeedbackId;
    private String feedback;
    @Enumerated(EnumType.STRING)
    private ERole accountType;
    private Integer accountId;
    private String status;
    private LocalDate Date;
    private LocalTime Time;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "clinicName", referencedColumnName = "clinicName")
    private Clinic systemFeedbackClinic;
}
