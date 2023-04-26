package com.uow.FYP_23_S1_11.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.uow.FYP_23_S1_11.domain.Clinic;
import com.uow.FYP_23_S1_11.domain.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    @Query("SELECT DISTINCT d FROM Doctor d INNER JOIN d.doctorSpecialty ds WHERE d.doctorClinic.clinicId = ?1 AND ds.type = ?2")
    public List<Doctor> findByClinicSpecialty(Integer clincId, String specialty);

    public List<Doctor> findByDoctorClinic(Clinic doctorClinic);

    public Doctor findByDoctorIdAndDoctorClinic(Integer id, Clinic doctorClinic);
}
