package com.uow.FYP_23_S1_11.domain.request;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.uow.FYP_23_S1_11.constraints.ValueOfEnum;
import com.uow.FYP_23_S1_11.enums.EWeekdays;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DoctorScheduleRequest {
    @JsonProperty("doctorId")
    private Integer doctorId;

    @NotEmpty
    @ValueOfEnum(enumClass = EWeekdays.class)
    @JsonProperty("day")
    private String day;

    @NotEmpty
    @JsonFormat(pattern = "HH:mm")
    @JsonProperty("startTime")
    private String startTime;

    @NotEmpty
    @JsonFormat(pattern = "HH:mm")
    @JsonProperty("endTime")
    private String endTime;

    @AssertTrue(message = "End Time must be later than Start Time")
    private boolean isValid() {
        return LocalTime.parse(endTime).isAfter(LocalTime.parse(startTime));
    }
}