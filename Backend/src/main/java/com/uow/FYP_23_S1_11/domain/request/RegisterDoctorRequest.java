package com.uow.FYP_23_S1_11.domain.request;

import java.util.List;
import java.util.TreeSet;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uow.FYP_23_S1_11.constraints.OnCreate;
import com.uow.FYP_23_S1_11.constraints.OnUpdate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterDoctorRequest {

    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    @JsonProperty("staffId")
    private Integer staffId;

    @NotEmpty(groups = OnCreate.class)
    @Null(groups = OnUpdate.class)
    @JsonProperty("username")
    private String username;

    @NotEmpty(groups = OnCreate.class)
    @Null(groups = OnUpdate.class)
    @JsonProperty("password")
    private String password;

    @NotEmpty(groups = { OnUpdate.class, OnCreate.class })
    @JsonProperty("name")
    private String name;

    @Email
    @NotEmpty(groups = { OnUpdate.class, OnCreate.class })
    @JsonProperty("email")
    private String email;

    @NotEmpty(groups = { OnUpdate.class, OnCreate.class })
    @JsonProperty("profile")
    private String profile;

    @NotEmpty(groups = { OnUpdate.class, OnCreate.class })
    @JsonProperty("specialty")
    private List<String> specialty;

    @Valid
    @JsonProperty("schedule")
    private TreeSet<DoctorScheduleRequest> schedule;
}
