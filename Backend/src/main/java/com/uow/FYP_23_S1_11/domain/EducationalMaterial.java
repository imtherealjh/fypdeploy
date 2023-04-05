package com.uow.FYP_23_S1_11.domain;

import java.util.List;

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
@Table(name="EDUCATIONAL_MATERIAL")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EducationalMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer materialId;
    private String title;
    private String content;

    //many to many for front-desk, doctor and patient
    @ManyToMany(mappedBy = "eduMatList")
    private List<Clinic> clinicList;
}
