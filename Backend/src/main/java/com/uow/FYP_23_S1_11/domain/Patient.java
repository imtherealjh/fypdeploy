package com.uow.FYP_23_S1_11.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PATIENT")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Patient implements Serializable {
    @Id
    @Column(name = "patientId")
    private Integer patientId;
    private String name;
    @Temporal(TemporalType.DATE)
    private Date dob;
    private String gender;
    private String address;
    private Integer contact;

    @OneToOne
    @MapsId
    @JoinColumn(name = "patientId", referencedColumnName = "accountId")
    private UserAccount patientAccount;

    @OneToMany(mappedBy = "patient")
    private List<PatientFeedback> patientFeedback;
}
