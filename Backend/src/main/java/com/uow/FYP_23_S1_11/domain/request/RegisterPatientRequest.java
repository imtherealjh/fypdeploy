package com.uow.FYP_23_S1_11.domain.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uow.FYP_23_S1_11.constraints.OnCreate;
import com.uow.FYP_23_S1_11.constraints.OnUpdate;
import com.uow.FYP_23_S1_11.constraints.ValueOfEnum;
import com.uow.FYP_23_S1_11.enums.EGender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterPatientRequest {
    @NotEmpty(groups = { OnCreate.class })
    @JsonProperty("username")
    private String username;

    @NotEmpty(groups = { OnCreate.class })
    @JsonProperty("password")
    private String password;

    @NotEmpty(groups = { OnCreate.class, OnUpdate.class })
    @JsonProperty("name")
    private String name;

    @NotNull(groups = { OnCreate.class })
    @JsonProperty("dob")
    private Date dob;

    @NotEmpty(groups = { OnCreate.class })
    @ValueOfEnum(groups = { OnCreate.class }, enumClass = EGender.class)
    @JsonProperty("gender")
    private String gender;

    @NotEmpty(groups = { OnCreate.class, OnUpdate.class })
    @JsonProperty("address")
    private String address;

    @NotNull(groups = { OnCreate.class, OnUpdate.class })
    @JsonProperty("contactNo")
    private Integer contactNo;

    @NotEmpty(groups = { OnCreate.class, OnUpdate.class })
    @JsonProperty("emergencyContact")
    private String emergencyContact;

    @NotNull(groups = { OnCreate.class, OnUpdate.class })
    @JsonProperty("emergencyContactNo")
    private Integer emergencyContactNo;

    @NotEmpty(groups = { OnCreate.class, OnUpdate.class })
    @Email(groups = { OnCreate.class, OnUpdate.class })
    @JsonProperty("email")
    private String email;
}
