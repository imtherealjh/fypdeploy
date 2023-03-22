package com.uow.FYP_23_S1_11.enums;

public enum EApprovalStatus {
    PENDING("PENDING"), APPROVED("APPROVED");

    private final String name;

    EApprovalStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
