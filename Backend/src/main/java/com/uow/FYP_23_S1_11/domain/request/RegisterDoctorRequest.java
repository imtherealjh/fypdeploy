package com.uow.FYP_23_S1_11.domain.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterDoctorRequest {
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
    private String name;
    private String profile;
    private List<String> specialty;
}
