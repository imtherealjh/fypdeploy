package com.uow.FYP_23_S1_11.domain;

import java.io.Serializable;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.uow.FYP_23_S1_11.enums.EWeekdays;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="DOCTOR_SCHEDULE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DoctorSchedule implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int scheduleId;
    @JsonProperty("day")
    @Enumerated(EnumType.STRING)
    private EWeekdays day;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm")
    @JsonSerialize(using= LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @Temporal(TemporalType.TIME)
    private LocalTime startTime;
    @JsonFormat(pattern="HH:mm")
    @JsonSerialize(using= LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @Temporal(TemporalType.TIME)
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "doctorId")
    private Doctor doctor;
}
