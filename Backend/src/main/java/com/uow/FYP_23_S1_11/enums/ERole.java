package com.uow.FYP_23_S1_11.enums;

public enum ERole {
    PATIENT("PATIENT"), CLINIC_OWNER("CLINC_OWNER"), SYSTEM_ADMIN("SYS_ADMIN"), DOCTOR("DOCTOR"), NURSE("NURSE"), FRONT_DESK("FRONT_DESK");

    private final String name;

    ERole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
