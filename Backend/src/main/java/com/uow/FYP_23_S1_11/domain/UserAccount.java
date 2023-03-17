package com.uow.FYP_23_S1_11.domain;

import java.io.Serializable;

import com.uow.FYP_23_S1_11.enums.EUserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "USERACCOUNT")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserAccount implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer accountId;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private EUserRole role;

    public UserAccount(String username, String password, EUserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @OneToOne(mappedBy = "clinicAccount")
    private Clinic clinic;

    @OneToOne(mappedBy = "patientAccount")
    private Patient patient;

    @OneToOne(mappedBy = "doctorAccount")
    private Doctor doctor;
}
