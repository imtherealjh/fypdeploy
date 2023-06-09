package com.uow.FYP_23_S1_11.domain;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.uow.FYP_23_S1_11.enums.EClinicStatus;

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
import jakarta.persistence.Lob;
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
@Table(name = "CLINIC")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Clinic implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer clinicId;

    @Column(name = "clinicName", nullable = false)
    private String clinicName;

    @Column(name = "location", nullable = false)
    private String location;

    @JsonIgnore
    @Lob
    @Column(name = "licenseProof", columnDefinition = "MEDIUMBLOB")
    private byte[] licenseProof;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "contactName", nullable = false)
    private String contactName;

    @Column(name = "contactNo", nullable = false)
    private Integer contactNo;

    @Enumerated(EnumType.STRING)
    private EClinicStatus status = EClinicStatus.PENDING;

    @Column(name = "openingHrs", nullable = false)
    @JsonFormat(pattern = "HH:mm")
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @Temporal(TemporalType.TIME)
    private LocalTime openingHrs;

    @Column(name = "closingHrs", nullable = false)
    @JsonFormat(pattern = "HH:mm")
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @Temporal(TemporalType.TIME)
    private LocalTime closingHrs;

    @Column(name = "apptDuration", nullable = false)
    @JsonFormat(pattern = "HH:mm")
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @Temporal(TemporalType.TIME)
    private LocalTime apptDuration;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinicAccount", referencedColumnName = "accountId")
    private UserAccount clinicAccount;

    @OneToMany(mappedBy = "doctorClinic", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Doctor> doctor;

    @JsonIgnore
    @OneToMany(mappedBy = "nurseClinic", cascade = CascadeType.ALL)
    private List<Nurse> nurse;

    @JsonIgnore
    @OneToMany(mappedBy = "frontDeskClinic", cascade = CascadeType.ALL)
    private List<FrontDesk> frontDesk;

    @JsonIgnore
    @OneToMany(mappedBy = "apptClinic", fetch = FetchType.LAZY)
    private List<Appointment> clinicsAppt;

    @JsonIgnore
    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL)
    private List<EducationalMaterial> eduMatList;

    @JsonIgnore
    @OneToMany(mappedBy = "clinicFeedback")
    private List<PatientFeedbackClinic> feedbackClinic;

    @JsonIgnore
    @OneToMany(mappedBy = "systemFeedbackClinic")
    private List<SystemFeedback> systemFeedback;

}
