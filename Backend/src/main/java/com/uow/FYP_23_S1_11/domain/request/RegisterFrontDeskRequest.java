package com.uow.FYP_23_S1_11.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uow.FYP_23_S1_11.constraints.OnCreate;
import com.uow.FYP_23_S1_11.constraints.OnStaffUpdate;
import com.uow.FYP_23_S1_11.constraints.OnUpdate;

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
public class RegisterFrontDeskRequest {
    @Null(groups = { OnCreate.class, OnUpdate.class })
    @NotNull(groups = OnStaffUpdate.class)
    @JsonProperty("staffId")
    private Integer staffId;

    @Null(groups = { OnStaffUpdate.class, OnUpdate.class })
    @NotEmpty(groups = OnCreate.class)
    @JsonProperty("username")
    private String username;

    @Null(groups = { OnStaffUpdate.class, OnUpdate.class })
    @NotEmpty(groups = OnCreate.class)
    @JsonProperty("password")
    private String password;

    @NotEmpty(groups = { OnStaffUpdate.class, OnUpdate.class, OnCreate.class })
    @JsonProperty("name")
    private String name;

    @Email(groups = { OnStaffUpdate.class, OnUpdate.class, OnCreate.class })
    @NotEmpty(groups = { OnStaffUpdate.class, OnUpdate.class, OnCreate.class })
    @JsonProperty("email")
    private String email;
}
