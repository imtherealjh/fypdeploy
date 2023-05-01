package com.uow.FYP_23_S1_11.service;

import java.time.LocalDate;
import java.util.List;

public interface StaffService {
    public List<?> getAllPatients();

    public Object getPatientsByDate(LocalDate date);
}
