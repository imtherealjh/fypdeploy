package com.uow.FYP_23_S1_11.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.uow.FYP_23_S1_11.enums.EAppointmentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "APPOINTMENTS")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Appointment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer appointmentId;
    private String description;
    private LocalDate apptDate;

    @JsonFormat(pattern = "HH:mm")
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @Temporal(TemporalType.TIME)
    private LocalTime apptTime;

    @Enumerated(EnumType.STRING)
    private EAppointmentStatus status;

    @ManyToOne
    @JoinColumn(name = "patientId", referencedColumnName = "patientId")
    @JsonIgnore
    private Patient apptPatient;

    @ManyToOne
    @JoinColumn(name = "doctorId", referencedColumnName = "doctorId")
    private Doctor apptDoctor;

    @ManyToOne
    @JoinColumn(name = "clinicId", referencedColumnName = "clinicId")
    @JsonIgnore
    private Clinic apptClinic;
}
