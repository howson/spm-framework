package com.sprouts.spm_framework.enums;

public enum MonitorType {
    HTTP("http"), PING("ping"), TCP("tcp"), UDP("udp"), APPS_TOMCAT("tomcat"), APPS_JETTY("jetty"), CANCEL(
            "cancel");

    public String type;

    private MonitorType(String type) {
        this.type = type;
    }
}
