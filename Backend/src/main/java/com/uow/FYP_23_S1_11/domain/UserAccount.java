package com.uow.FYP_23_S1_11.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uow.FYP_23_S1_11.enums.ERole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
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
@NamedQuery(name = "findEmailInTables", query = "SELECT p.email FROM Patient p WHERE p.email = :email " +
        "UNION SELECT c.email FROM Clinic c WHERE c.email = :email " +
        "UNION SELECT d.email FROM Doctor d WHERE d.email = :email " +
        "UNION SELECT n.email FROM Nurse n WHERE n.email = :email " +
        "UNION SELECT f.email FROM FrontDesk f WHERE f.email = :email ")
@NamedQuery(name = "findNameInTables", query = "SELECT p.name FROM Patient p WHERE p.patientAccount = :account "
        + "UNION SELECT c.clinicName FROM Clinic c WHERE c.clinicAccount = :account " +
        "UNION SELECT d.name FROM Doctor d WHERE d.doctorAccount = :account " +
        "UNION SELECT n.name FROM Nurse n WHERE n.nurseAccount = :account " +
        "UNION SELECT f.name FROM FrontDesk f WHERE f.frontDeskAccount = :account ")
public class UserAccount implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer accountId;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private ERole role;
    private Boolean isEnabled = false;
    @Column(name = "verificationCode")
    private String verificationCode;

    @JsonIgnore
    @OneToOne(mappedBy = "clinicAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Clinic clinic;

    @JsonIgnore
    @OneToOne(mappedBy = "patientAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Patient patient;

    @JsonIgnore
    @OneToOne(mappedBy = "doctorAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Doctor doctor;

    @JsonIgnore
    @OneToOne(mappedBy = "nurseAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Nurse nurse;

    @JsonIgnore
    @OneToOne(mappedBy = "frontDeskAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private FrontDesk frontDesk;

    @JsonIgnore
    @OneToMany(mappedBy = "accountFeedback")
    private List<SystemFeedback> accountFeedback;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
