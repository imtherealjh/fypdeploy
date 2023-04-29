package com.uow.FYP_23_S1_11.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "FRONT_DESK")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FrontDesk implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int frontDeskId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "frontDeskAccount", referencedColumnName = "accountId")
    private UserAccount frontDeskAccount;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "frontDeskClinic", referencedColumnName = "clinicId")
    private Clinic frontDeskClinic;
}
