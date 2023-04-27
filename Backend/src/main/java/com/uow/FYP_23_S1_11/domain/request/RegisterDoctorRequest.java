package com.uow.FYP_23_S1_11.domain.request;

import java.util.List;
import java.util.TreeSet;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterDoctorRequest {
    @NotEmpty
    @JsonProperty("username")
    private String username;

    @NotEmpty
    @JsonProperty("password")
    private String password;

    @NotEmpty
    @JsonProperty("name")
    private String name;

    @Email
    @NotEmpty
    @JsonProperty("email")
    private String email;

    @NotEmpty
    @JsonProperty("profile")
    private String profile;

    @NotEmpty
    @JsonProperty("specialty")
    private List<String> specialty;

    @Valid
    @JsonProperty("schedule")
    private TreeSet<DoctorScheduleRequest> schedule;
}
