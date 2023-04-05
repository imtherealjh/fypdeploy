package com.uow.FYP_23_S1_11.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uow.FYP_23_S1_11.domain.Specialty;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Integer> {
    Specialty findByType(String type);
    List<Specialty> findByTypeIn (List<String> specialtyType);
}
