package com.uow.FYP_23_S1_11.enums;

public enum ESystemFeedbackStatus {
    UNSOLVED("UNSOLVED"), COMPLETED("COMPLETED");

    private final String name;

    ESystemFeedbackStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
