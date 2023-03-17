package com.uow.FYP_23_S1_11.enums;

public enum ETokenType {
    REFRESH_TOKEN("REFRESH_TOKEN"), ACCESS_TOKEN("ACCESS_TOKEN");

    private final String name;
    
    ETokenType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
