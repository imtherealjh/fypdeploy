package com.uow.FYP_23_S1_11.domain.request;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.uow.FYP_23_S1_11.constraints.OnCreate;
import com.uow.FYP_23_S1_11.constraints.OnUpdate;

import jakarta.validation.constraints.AssertTrue;
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
public class RegisterClinicRequest {
    @NotEmpty(groups = OnCreate.class)
    @JsonProperty("username")
    private String username;

    @NotEmpty(groups = OnCreate.class)
    @JsonProperty("password")
    private String password;

    @NotEmpty(groups = OnCreate.class)
    @JsonProperty("clinicName")
    private String clinicName;

    @NotEmpty(groups = { OnCreate.class, OnUpdate.class })
    @Email(groups = { OnCreate.class, OnUpdate.class })
    @JsonProperty("email")
    private String email;

    @NotEmpty(groups = { OnCreate.class, OnUpdate.class })
    @JsonProperty("contactName")
    private String contactName;

    @NotNull(groups = { OnCreate.class, OnUpdate.class })
    @JsonProperty("contactNo")
    private Integer contactNo;

    @NotEmpty(groups = { OnCreate.class, OnUpdate.class })
    @JsonProperty("location")
    private String location;

    @NotEmpty(groups = { OnCreate.class, OnUpdate.class })
    @JsonFormat(pattern = "HH:mm")
    @JsonProperty("openingHrs")
    private String openingHrs;

    @NotEmpty(groups = { OnCreate.class, OnUpdate.class })
    @JsonFormat(pattern = "HH:mm")
    @JsonProperty("closingHrs")
    private String closingHrs;

    @AssertTrue(groups = { OnCreate.class,
            OnUpdate.class }, message = "Start Time and End Time must be valid (HH:mm) and End Time must be later than Start Time")
    private boolean isValid() {
        try {
            return LocalTime.parse(closingHrs).isAfter(LocalTime.parse(openingHrs));
        } catch (Exception e) {
            return false;
        }
    }

    @NotEmpty(groups = { OnCreate.class, OnUpdate.class })
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @JsonProperty("apptDuration")
    private String apptDuration;

    @AssertTrue(groups = { OnCreate.class, OnUpdate.class }, message = "Appt Duration must be a valid format (HH:mm)")
    private Boolean isValidApptDuration() {
        try {
            LocalTime.parse(closingHrs);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @NotNull(groups = OnCreate.class)
    @JsonIgnore
    @JsonProperty("customLicenseProof")
    private MultipartFile customLicenseProof;

    @AssertTrue(groups = OnCreate.class, message = "License Proof must be a valid format")
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
