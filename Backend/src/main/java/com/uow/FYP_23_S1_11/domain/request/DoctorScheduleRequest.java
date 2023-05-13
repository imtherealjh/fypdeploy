package com.uow.FYP_23_S1_11.domain.request;

import java.time.LocalTime;
import java.util.Comparator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.uow.FYP_23_S1_11.constraints.OnCreate;
import com.uow.FYP_23_S1_11.constraints.OnStaffUpdate;
import com.uow.FYP_23_S1_11.constraints.OnUpdate;
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
public class DoctorScheduleRequest implements Comparable<DoctorScheduleRequest> {
    @JsonProperty("doctorId")
    private Integer doctorId;

    @NotEmpty(groups = { OnCreate.class, OnStaffUpdate.class })
    @ValueOfEnum(enumClass = EWeekdays.class)
    @JsonProperty("day")
    private String day;

    @NotEmpty(groups = { OnCreate.class, OnStaffUpdate.class })
    @JsonProperty("startTime")
    private String startTime;

    @NotEmpty(groups = { OnCreate.class, OnStaffUpdate.class })
    @JsonProperty("endTime")
    private String endTime;

    @AssertTrue(groups = { OnCreate.class,
            OnStaffUpdate.class }, message = "Start Time and End Time must be valid (HH:mm) and End Time must be later than Start Time")
    private boolean isValid() {
        try {
            return LocalTime.parse(endTime).isAfter(LocalTime.parse(startTime));
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int compareTo(DoctorScheduleRequest o) {
        return Comparator.comparing(DoctorScheduleRequest::getDay)
                .thenComparing(DoctorScheduleRequest::getStartTime)
                .thenComparing(DoctorScheduleRequest::getEndTime)
                .compare(this, o);
    }

}