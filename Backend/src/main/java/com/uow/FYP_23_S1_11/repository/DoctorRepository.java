package com.uow.FYP_23_S1_11.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uow.FYP_23_S1_11.domain.Clinic;
import com.uow.FYP_23_S1_11.domain.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    public List<Doctor> findByDoctorClinic(Clinic doctorClinic);

    public Doctor findByDoctorIdAndDoctorClinic(Integer id, Clinic doctorClinic);

}
