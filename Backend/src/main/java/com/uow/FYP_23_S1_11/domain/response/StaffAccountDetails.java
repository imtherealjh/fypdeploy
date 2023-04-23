package com.uow.FYP_23_S1_11.domain.response;

import com.uow.FYP_23_S1_11.enums.ERole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class StaffAccountDetails {
    private Integer accountId;
    private String name;
    private ERole role;
    private String email;
}
