package com.uow.FYP_23_S1_11.domain.request;

import java.time.LocalDate;
import java.util.List;

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
public class GenerateAppointmentRequest {
    @NotEmpty
    private List<@NotNull Integer> doctorIds;
    @NotEmpty
    private List<@NotNull LocalDate> apptDates;
}
