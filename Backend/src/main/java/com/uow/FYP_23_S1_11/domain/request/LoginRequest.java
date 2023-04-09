package com.uow.FYP_23_S1_11.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginRequest {
    @JsonProperty("username")
    @NotEmpty(message = "Username should not be empty")
    private String username;
    @JsonProperty("password")
    @NotEmpty(message = "Password should not be empty")
    private String password;
}
