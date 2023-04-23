package com.uow.FYP_23_S1_11.domain.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @NotEmpty
    @JsonProperty("username")
    private String username;

    @NotEmpty
    @JsonProperty("password")
    private String password;

    @NotEmpty
    @JsonProperty("name")
    private String name;

    @NotNull
    @JsonProperty("dob")
    private Date dob;

    @NotEmpty
    @ValueOfEnum(enumClass = EGender.class)
    @JsonProperty("gender")
    private String gender;

    @NotEmpty
    @JsonProperty("address")
    private String address;

    @NotNull
    @JsonProperty("contact")
    private Integer contact;

    @NotEmpty
    @Email
    @JsonProperty("email")
    private String email;
}
