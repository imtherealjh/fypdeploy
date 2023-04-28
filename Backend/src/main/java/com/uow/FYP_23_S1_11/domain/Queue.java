package com.uow.FYP_23_S1_11.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "Queue")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Queue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int queueId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String queueNumber;
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @Temporal(TemporalType.TIME)
    private LocalTime time;
    private String status;
    private String priority;

    @OneToOne
    @JoinColumn(name = "appointmentId", referencedColumnName = "appointmentId")
    private Appointment appointment;
}
