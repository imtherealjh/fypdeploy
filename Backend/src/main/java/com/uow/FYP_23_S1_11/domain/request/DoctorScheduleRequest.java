package com.uow.FYP_23_S1_11.domain.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.uow.FYP_23_S1_11.enums.EWeekdays;

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
    @JsonProperty("day")
    private EWeekdays day;
    @JsonFormat(pattern="HH:mm")
    @JsonProperty("startTime")
    private String startTime;
    @JsonFormat(pattern="HH:mm")
    @JsonProperty("endTime")
    private String endTime;
}