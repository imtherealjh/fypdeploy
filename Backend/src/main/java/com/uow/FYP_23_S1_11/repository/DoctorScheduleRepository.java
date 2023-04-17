package com.uow.FYP_23_S1_11.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uow.FYP_23_S1_11.domain.Doctor;
import com.uow.FYP_23_S1_11.domain.DoctorSchedule;
import com.uow.FYP_23_S1_11.enums.EWeekdays;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Integer> {
    DoctorSchedule findByDoctorAndDay(Doctor doctor, EWeekdays weekday);
}
