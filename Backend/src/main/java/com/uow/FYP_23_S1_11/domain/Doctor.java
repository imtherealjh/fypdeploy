package com.uow.FYP_23_S1_11.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer doctorId;
    private String name;
    private String profile;

    @OneToOne
    @JoinColumn(name = "doctorAccount", referencedColumnName = "accountId")
    private UserAccount doctorAccount;

    @ManyToOne
    @JoinColumn(name = "doctorClinic", referencedColumnName = "clinicId")
    private Clinic doctorClinic;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "doctor_specialty", joinColumns = @JoinColumn(name = "doctorId"), inverseJoinColumns = @JoinColumn(name = "specialtyId"))
    private List<Specialty> doctorSpecialty;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<DoctorSchedule> doctorSchedule;
}
