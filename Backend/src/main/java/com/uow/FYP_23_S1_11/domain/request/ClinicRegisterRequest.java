package com.uow.FYP_23_S1_11.domain.request;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.AssertTrue;
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
public class ClinicRegisterRequest {
    @NotEmpty
    @JsonProperty("username")
    private String username;

    @NotEmpty
    @JsonProperty("password")
    private String password;

    @NotEmpty
    @JsonProperty("name")
    private String name;

    @NotEmpty
    @Email
    @JsonProperty("email")
    private String email;

    @NotEmpty
    @JsonProperty("location")
    private String location;

    @NotEmpty
    @JsonFormat(pattern = "HH:mm")
    @JsonProperty("openingHrs")
    private String openingHrs;

    @NotEmpty
    @JsonFormat(pattern = "HH:mm")
    @JsonProperty("closingHrs")
    private String closingHrs;

    @AssertTrue(message = "Start Time and End Time must be valid (HH:mm) and End Time must be later than Start Time")
    private boolean isValid() {
        try {
            return LocalTime.parse(closingHrs).isAfter(LocalTime.parse(openingHrs));
        } catch (Exception e) {
            return false;
        }
    }

    @NotEmpty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @JsonProperty("apptDuration")
    private String apptDuration;

    @AssertTrue(message = "Appt Duration must be a valid format (HH:mm)")
    private Boolean isValidApptDuration() {
        try {
            LocalTime.parse(closingHrs);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @NotEmpty
    @JsonProperty("licenseBase64")
    private String licenseBase64;
}
