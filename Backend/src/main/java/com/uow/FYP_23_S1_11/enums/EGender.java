package com.uow.FYP_23_S1_11.enums;

public enum EGender {
    M("M"), F("F");

    private final String name;

    EGender(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
