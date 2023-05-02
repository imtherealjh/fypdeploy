package com.uow.FYP_23_S1_11.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uow.FYP_23_S1_11.enums.EGender;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
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
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String name;
    @Temporal(TemporalType.DATE)
    private Date dob;
    private String address;
    @Enumerated(EnumType.STRING)
    private EGender gender;
    private Integer contactNo;
    private String emergencyContact;
    private Integer emergencyContactNo;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patientAccount", referencedColumnName = "accountId")
    private UserAccount patientAccount;

    @JsonIgnore
    @OneToMany(mappedBy = "apptPatient", fetch = FetchType.LAZY)
    private List<Appointment> patientAppt;

    @OneToOne(mappedBy = "patientmd", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PatientMedicalRecords patientMedicalRecords;

    @JsonIgnore
    @OneToMany(mappedBy = "patientClinicFeedback")
    private List<PatientFeedbackClinic> feedbackPatientClinic;

    @JsonIgnore
    @OneToMany(mappedBy = "patientDoctorFeedback")
    private List<PatientFeedbackDoctor> feedbackPatientDoctor;
}
