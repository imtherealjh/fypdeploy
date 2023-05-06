package com.uow.FYP_23_S1_11.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.uow.FYP_23_S1_11.domain.Clinic;
import com.uow.FYP_23_S1_11.domain.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    public List<Doctor> findByDoctorClinic(Clinic doctorClinic);

    public Doctor findByDoctorIdAndDoctorClinic(Integer id, Clinic doctorClinic);

    @Query(value = "SELECT d.doctor_id,d.email,d.name,d.profile,d.doctor_account,d.doctor_clinic FROM doctor d INNER JOIN appointments a ON d.doctor_id = a.doctor_id INNER JOIN patient p ON a.patient_id = p.patient_id WHERE d.doctor_id = ?1", nativeQuery = true)
    public List<Doctor> findAppointmentAndPatientByDoctorId(Integer doctorId);
}
