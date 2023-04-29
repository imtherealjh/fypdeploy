package com.uow.FYP_23_S1_11.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Specialty")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Specialty implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int specialtyId;
    private String type;
    private String description;

    @JsonIgnore
    @ManyToMany(mappedBy = "doctorSpecialty")
    private List<Doctor> doctorList;
}
