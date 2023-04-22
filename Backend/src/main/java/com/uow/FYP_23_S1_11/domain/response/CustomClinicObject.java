package com.uow.FYP_23_S1_11.domain.response;

import com.uow.FYP_23_S1_11.domain.Clinic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CustomClinicObject {
    private Clinic clinic;
    private long totalAcc;
}
