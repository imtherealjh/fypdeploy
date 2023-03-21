package com.uow.FYP_23_S1_11.enums;

public enum EWeekdays {
    SUNDAY("SUN"), MONDAY("MON"), TUESDAY("TUE"), WEDNESDAY("WED"), THURSDAY("THU"), FRIDAY("FRI"), SATURDAY("SAT");

    private final String name;

    EWeekdays(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
