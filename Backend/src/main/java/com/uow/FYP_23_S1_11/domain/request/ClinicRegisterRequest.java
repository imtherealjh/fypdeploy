package com.uow.FYP_23_S1_11.domain.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClinicRegisterRequest {
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
    @JsonProperty("name")
    private String name;
    @JsonProperty("location")
    private String location;
    @JsonFormat(pattern="HH:mm")
    @JsonProperty("openingHrs")
    private String openingHrs;
    @JsonFormat(pattern="HH:mm")
    @JsonProperty("closingHrs")
    private String closingHrs;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @JsonProperty("apptDuration")
    private String apptDuration;
    @JsonProperty("licenseBase64")
    private String licenseBase64;
}
