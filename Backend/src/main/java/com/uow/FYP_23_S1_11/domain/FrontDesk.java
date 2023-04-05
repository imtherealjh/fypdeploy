package com.uow.FYP_23_S1_11.domain;

import java.io.Serializable;

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
@Table(name="FRONT_DESK")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FrontDesk implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int frontDeskId;
    private String name;

    @OneToOne
    @JoinColumn(name = "frontDeskAccount", referencedColumnName = "accountId")
    private UserAccount frontDeskAccount;

    @ManyToOne
    @JoinColumn(name = "frontDeskClinic", referencedColumnName = "clinicId")
    private Clinic frontDeskClinic;
}
