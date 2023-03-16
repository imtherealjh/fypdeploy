package com.uow.FYP_23_S1_11.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClinicRegisterRequest {
    private String username;
    private String password;
    private String clinicName;
    private String clinicLocation;
    private String licenseBase64;
}
