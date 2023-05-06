package com.uow.FYP_23_S1_11.enums;

public enum EQueuePriority {
    WALK_IN_CUSTOMER("WALK_IN_CUSTOMER"), APPOINTMENT_MADE("APPOINTMENT_MADE");

    private final String name;

    EQueuePriority(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
