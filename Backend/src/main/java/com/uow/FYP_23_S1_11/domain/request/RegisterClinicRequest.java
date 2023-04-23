package com.uow.FYP_23_S1_11.domain.request;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.AssertTrue;
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
public class RegisterClinicRequest {
    @NotEmpty
    @JsonProperty("username")
    private String username;

    @NotEmpty
    @JsonProperty("password")
    private String password;

    @NotEmpty
    @JsonProperty("clinicName")
    private String clinicName;

    @NotEmpty
    @Email
    @JsonProperty("email")
    private String email;

    @NotEmpty
    @JsonProperty("contactName")
    private String contactName;

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

    @NotNull
    @JsonIgnore
    @JsonProperty("customLicenseProof")
    private MultipartFile customLicenseProof;

    @AssertTrue(message = "License Proof must be a valid format")
    private Boolean isValidLicenseProof() {
        try {
            InputStream inputStream = customLicenseProof.getInputStream();
            ImageIO.read(inputStream);

            long fileSizeInBytes = customLicenseProof.getSize();
            long fileSizeInMB = fileSizeInBytes / (1024 * 1024);
            if (fileSizeInMB > 1) {
                throw new IOException("Invalid file size...");
            }
            return true;
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
    }
}
