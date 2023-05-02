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

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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

    private String diagnostic;

    @ManyToOne(fetch = FetchType.LAZY)
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

    @JsonIgnore
    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL)
    private Queue queue;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((appointmentId == null) ? 0 : appointmentId.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((apptDate == null) ? 0 : apptDate.hashCode());
        result = prime * result + ((apptTime == null) ? 0 : apptTime.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((diagnostic == null) ? 0 : diagnostic.hashCode());
        result = prime * result + ((apptPatient == null) ? 0 : apptPatient.hashCode());
        result = prime * result + ((apptDoctor == null) ? 0 : apptDoctor.hashCode());
        result = prime * result + ((apptClinic == null) ? 0 : apptClinic.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Appointment other = (Appointment) obj;
        if (appointmentId == null) {
            if (other.appointmentId != null)
                return false;
        } else if (!appointmentId.equals(other.appointmentId))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (apptDate == null) {
            if (other.apptDate != null)
                return false;
        } else if (!apptDate.equals(other.apptDate))
            return false;
        if (apptTime == null) {
            if (other.apptTime != null)
                return false;
        } else if (!apptTime.equals(other.apptTime))
            return false;
        if (status != other.status)
            return false;
        if (diagnostic == null) {
            if (other.diagnostic != null)
                return false;
        } else if (!diagnostic.equals(other.diagnostic))
            return false;
        if (apptPatient == null) {
            if (other.apptPatient != null)
                return false;
        } else if (!apptPatient.equals(other.apptPatient))
            return false;
        if (apptDoctor == null) {
            if (other.apptDoctor != null)
                return false;
        } else if (!apptDoctor.equals(other.apptDoctor))
            return false;
        if (apptClinic == null) {
            if (other.apptClinic != null)
                return false;
        } else if (!apptClinic.equals(other.apptClinic))
            return false;
        return true;
    }

}
