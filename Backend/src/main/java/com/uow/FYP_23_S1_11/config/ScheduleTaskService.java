package com.uow.FYP_23_S1_11.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.uow.FYP_23_S1_11.domain.Doctor;
import com.uow.FYP_23_S1_11.repository.DoctorRepository;

@Configuration
@EnableScheduling
public class ScheduleTaskService {
    @Autowired
    private DoctorRepository doctorRepository;

    @Scheduled(cron = "30 * * * * *")
    public void execute() {
        List<Doctor> doctorList = doctorRepository.findAll();
    }
}
