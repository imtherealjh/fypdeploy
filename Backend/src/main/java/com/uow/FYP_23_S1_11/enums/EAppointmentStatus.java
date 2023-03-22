package com.uow.FYP_23_S1_11.enums;

public enum EAppointmentStatus {
    AVAILABLE("AVAILABLE"), BOOKED("BOOKED"), BLOCKED("BLOCKED");

    private final String name;

    EAppointmentStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
