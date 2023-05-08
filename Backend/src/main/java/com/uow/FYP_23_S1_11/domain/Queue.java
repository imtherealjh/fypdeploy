package com.uow.FYP_23_S1_11.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import com.uow.FYP_23_S1_11.enums.EQueueStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
    // ALTER TABLE queue MODIFY queue_number INTEGER NOT NULL AUTO_INCREMENT; in sql
    private int queueNumber;
    private LocalDate date;
    private LocalTime time;
    @Enumerated(EnumType.STRING)
    private EQueueStatus status;

    @OneToOne
    @JoinColumn(name = "appointmentId", referencedColumnName = "appointmentId")
    private Appointment appointment;
}
