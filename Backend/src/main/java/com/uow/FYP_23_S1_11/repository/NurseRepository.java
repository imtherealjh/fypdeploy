package com.uow.FYP_23_S1_11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uow.FYP_23_S1_11.domain.Nurse;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, Integer> {
    
}
