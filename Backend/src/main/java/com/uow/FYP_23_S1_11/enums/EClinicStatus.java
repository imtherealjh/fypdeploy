package com.uow.FYP_23_S1_11.enums;

public enum EClinicStatus {
    PENDING("PENDING"), REJECTED("REJECTED"), APPROVED("APPROVED"), SUSPENDED("SUSPENDED"), DELETED("DELETED");

    private final String name;

    EClinicStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
