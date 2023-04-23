package com.uow.FYP_23_S1_11.service;

import com.uow.FYP_23_S1_11.domain.request.GenerateAppointmentRequest;
import com.uow.FYP_23_S1_11.domain.request.GenerateClinicAppointmentRequest;
import com.uow.FYP_23_S1_11.domain.request.QueueRequest;
import com.uow.FYP_23_S1_11.domain.EducationalMaterial;
import com.uow.FYP_23_S1_11.domain.request.EducationalMaterialRequest;

public interface ClerkService {
    public Boolean generateClinicAppointmentSlots(GenerateClinicAppointmentRequest generateClinicApptReq);

    public Boolean generateDoctorAppointmentSlots(GenerateAppointmentRequest generateDoctorApptReq);

    //
    public Boolean createEduMaterial(EducationalMaterialRequest request);

    public Boolean deleteEduMaterial(Integer materialId);

    public Boolean updateEduMaterial(Integer materialId, EducationalMaterialRequest request);

    public Boolean updateQueueNumber(Integer queueId,
            QueueRequest updateQueueRequest);

    public Boolean deleteQueueNumber(Integer queueId);
}
