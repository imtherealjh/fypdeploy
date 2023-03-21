package com.uow.FYP_23_S1_11.domain;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.uow.FYP_23_S1_11.enums.EStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name="CLINIC")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Clinic implements Serializable {
    @Id
    @Column(name="clinicId")
    private Integer clinicId;
    private String name;
    private String location;
    private String proofOfLicense;
    @JsonFormat(pattern="HH:mm")
    @JsonSerialize(using= LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @Temporal(TemporalType.TIME)
    private LocalTime openingHrs;
    @JsonFormat(pattern="HH:mm")
    @JsonSerialize(using= LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @Temporal(TemporalType.TIME)
    private LocalTime closingHrs;
    @JsonFormat(pattern="HH:mm")
    @JsonSerialize(using= LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @Temporal(TemporalType.TIME)
    private LocalTime apptDuration;
    @Column(length = 35)
    @Enumerated(EnumType.STRING)
    private EStatus status = EStatus.PENDING;

    @OneToOne
    @MapsId
    @JoinColumn(name = "clinicId", referencedColumnName = "accountId")
    private UserAccount clinicAccount;

    @OneToMany(mappedBy = "doctorClinic", cascade = CascadeType.ALL)
    private List<Doctor> doctor;

    @OneToMany(mappedBy = "nurseClinic", cascade = CascadeType.ALL)
    private List<Nurse> nurse;

    @OneToMany(mappedBy = "frontDeskClinic", cascade = CascadeType.ALL)
    private List<FrontDesk> frontDesk;
}
