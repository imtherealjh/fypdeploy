package com.uow.FYP_23_S1_11.service;

import com.uow.FYP_23_S1_11.domain.request.RegisterNurseRequest;

public interface NurseService {
    public Object getProfile();

    public Boolean updateProfile(RegisterNurseRequest registerNurseRequest);
}
