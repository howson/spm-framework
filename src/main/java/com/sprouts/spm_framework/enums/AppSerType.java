package com.sprouts.spm_framework.enums;

public enum AppSerType {
    TOMCAT("tomcat"), JETTY("jetty");

    public String type;

    private AppSerType(String type) {
        this.type = type;
    }
}
