package com.uow.FYP_23_S1_11.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthResponse {
    @JsonProperty("name")
    private String name;
    @JsonProperty("role")
    private String role;
    @JsonProperty("accessToken")
    private String accessToken;
}
