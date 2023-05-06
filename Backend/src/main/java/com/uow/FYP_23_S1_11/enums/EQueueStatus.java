package com.uow.FYP_23_S1_11.enums;

public enum EQueueStatus {
    WAITING_IN_QUEUE("WAITING_IN_QUEUE"), CONSULTING("CONSULTING"), MISSED_QUEUE("MISSED_QUEUE"), COMPLETE("COMPLETE");

    private final String name;

    EQueueStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
