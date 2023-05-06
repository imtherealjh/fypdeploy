package com.uow.FYP_23_S1_11.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uow.FYP_23_S1_11.constraints.OnCreate;
import com.uow.FYP_23_S1_11.constraints.OnStaffUpdate;
import com.uow.FYP_23_S1_11.constraints.OnUpdate;

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
public class BookUpdateAppointmentRequest {
    @JsonProperty("patientId")
    @Null(groups = { OnCreate.class, OnUpdate.class })
    @NotNull(groups = OnStaffUpdate.class)
    private Integer patientId;

    @JsonProperty("originalApptId")
    @Null(groups = OnCreate.class)
    @NotNull(groups = { OnUpdate.class, OnStaffUpdate.class })
    private Integer originalApptId;

    @JsonProperty("apptId")
    @NotNull(groups = { OnCreate.class, OnUpdate.class, OnStaffUpdate.class  })
    private Integer apptId;
}
