package com.uow.FYP_23_S1_11.domain.request;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import jakarta.validation.constraints.AssertTrue;
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
public class DoctorAvailableRequest {
    @NotNull
    private Integer doctorId;

    @NotEmpty
    private String date;

    @AssertTrue
    private boolean isValid() {
        try {
            LocalDate.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
