package com.uow.FYP_23_S1_11.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer patientId;
    private String name;
    @Temporal(TemporalType.DATE)
    private Date dob;
    private String gender;
    private String address;
    private Integer contact;

    @OneToOne
    @JoinColumn(name = "patientAccount", referencedColumnName = "accountId")
    private UserAccount patientAccount;

    @OneToMany(mappedBy = "patient")
    private List<PatientFeedback> patientFeedback;

    @OneToMany(mappedBy = "apptPatient")
    @JsonIgnore
    private List<Appointment> patientAppt;

    @JsonIgnore
    @OneToOne(mappedBy = "patientmd", cascade = CascadeType.ALL)
    private PatientMedicalRecords patientMedicalRecords;

    // @OneToOne
    // @JoinTable(name = "MY_JOIN_TABLE",
    // joinColumns = {
    // @JoinColumn(name = "Patient", referencedColumnName = "patientId")
    // },
    // inverseJoinColumns = {
    // @JoinColumn(name = "patientMedicalRecords", referencedColumnName =
    // "medicalRecordId", unique = true)
    // }
    // )
    // private PatientMedicalRecords patientMedicalRecords;

}
