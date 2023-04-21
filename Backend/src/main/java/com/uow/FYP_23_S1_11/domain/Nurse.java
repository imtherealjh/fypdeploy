package com.uow.FYP_23_S1_11.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
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
@Table(name = "NURSE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Nurse implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int nurseId;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(name = "nurseAccount", referencedColumnName = "accountId")
    private UserAccount nurseAccount;

    @ManyToOne
    @JoinColumn(name = "nurseClinic", referencedColumnName = "clinicId")
    private Clinic nurseClinic;
}
