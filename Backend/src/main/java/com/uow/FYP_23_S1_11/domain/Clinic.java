package com.uow.FYP_23_S1_11.domain;

import java.io.Serializable;

import com.uow.FYP_23_S1_11.enums.EStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="CLINIC")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Clinic implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer clinicId;
    private String name;
    private String location;
    private String proofOfLicense;
    @Enumerated(EnumType.STRING)
    private EStatus approved;

    @OneToOne
    @JoinColumn(name = "clinicAccount", referencedColumnName = "accountId")
    private UserAccount clinicAccount;
}
