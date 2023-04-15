package com.uow.FYP_23_S1_11.domain.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatientRegisterRequest {
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
    @JsonProperty("name")
    private String name;
    @JsonProperty("dob")
    private Date dob;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("address")
    private String address;
    @JsonProperty("contact")
    private Integer contact;
    @JsonProperty("email")
    private String email;
}
