package com.uow.FYP_23_S1_11.enums;

public enum ESubscriptionTier {
    FREE("FREE"), PAID("PAID");

    private final String name;

    ESubscriptionTier(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
