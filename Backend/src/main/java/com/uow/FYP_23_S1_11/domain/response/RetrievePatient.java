package com.uow.FYP_23_S1_11.domain.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RetrievePatient {
    private List<?> patientList;
    private long noOfPatients;
}
