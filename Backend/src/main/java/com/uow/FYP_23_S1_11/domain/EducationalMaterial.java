package com.uow.FYP_23_S1_11.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "EDUCATIONAL_MATERIAL")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EducationalMaterial implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer materialId;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;

    // many to many for front-desk, doctor and patient
    @ManyToOne
    @JoinColumn(name = "clinic", referencedColumnName = "clinicId", nullable = false)
    private Clinic clinic;
}
