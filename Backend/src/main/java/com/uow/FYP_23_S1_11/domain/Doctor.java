package com.uow.FYP_23_S1_11.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "DOCTOR")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Doctor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer doctorId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(name = "profile", nullable = false)
    private String profile;

    @OneToOne
    @JoinColumn(name = "doctorAccount", referencedColumnName = "accountId")
    @JsonIgnore
    private UserAccount doctorAccount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctorClinic", referencedColumnName = "clinicId", nullable = false)
    @JsonIgnore
    private Clinic doctorClinic;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "doctor_specialty", joinColumns = @JoinColumn(name = "doctorId"), inverseJoinColumns = @JoinColumn(name = "specialtyId"))
    private List<Specialty> doctorSpecialty;

    @JsonManagedReference
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<DoctorSchedule> doctorSchedule;

    @OneToMany(mappedBy = "apptDoctor")
    @JsonIgnore
    private List<Appointment> doctorAppt;

    @OneToMany(mappedBy = "doctorFeedback")
    @JsonIgnore
    private List<PatientFeedbackDoctor> feedbackDoctor;
}
