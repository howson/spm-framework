package com.sprouts.spm_framework.enums;

public enum TimeType {
    MINUTE(0), HOUR(1), DAY(2);
    public int type;

    private TimeType(int type) {
        this.type = type;
    }
}
