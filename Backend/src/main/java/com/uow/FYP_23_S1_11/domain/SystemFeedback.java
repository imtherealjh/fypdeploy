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
    private String status;
    private LocalDateTime localDateTime = LocalDateTime.now();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "accountId", referencedColumnName = "accountId")
    private UserAccount accountFeedback;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "clinic", referencedColumnName = "clinicId")
    private Clinic systemFeedbackClinic;
}
