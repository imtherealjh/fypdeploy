package com.uow.FYP_23_S1_11.enums;

public enum EStatus {
    PENDING("PENDING"), APPROVED("APPROVED"), SYSTEM_ADMIN("SYS_ADMIN");

    private final String name;

    EStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
