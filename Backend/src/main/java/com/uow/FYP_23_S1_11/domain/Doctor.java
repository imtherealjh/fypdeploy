package com.uow.FYP_23_S1_11.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="DOCTOR")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int doctorId;
    private String name;
    private String profile;
    private String speciality;

    @OneToOne
    @JoinColumn(name = "doctorAccount", referencedColumnName = "accountId")
    private UserAccount doctorAccount;

    @ManyToOne
    @JoinColumn(name="clinic", referencedColumnName = "clinicId")
    private Clinic clinic;
}
