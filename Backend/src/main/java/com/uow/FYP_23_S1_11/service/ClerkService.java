package com.uow.FYP_23_S1_11.service;

import com.uow.FYP_23_S1_11.domain.request.RegisterFrontDeskRequest;

import java.util.List;

import com.uow.FYP_23_S1_11.domain.Doctor;
import com.uow.FYP_23_S1_11.domain.request.EducationalMaterialRequest;

public interface ClerkService {
    public Object getProfile();

    public List<Doctor> getDoctorList();

    public Boolean updateProfile(RegisterFrontDeskRequest registerFrontDeskRequest);

    public Boolean createEduMaterial(EducationalMaterialRequest request);

    public Boolean updateEduMaterial(Integer materialId, EducationalMaterialRequest request);

    public Boolean checkInUser(Integer apptId);

    // public Boolean insertQueueNumber(QueueRequest request);

}
