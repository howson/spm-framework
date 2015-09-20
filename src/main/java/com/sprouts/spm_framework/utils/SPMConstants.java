package com.sprouts.spm_framework.utils;

public class SPMConstants {

    public static String DEFAULT_BROKER = "overall.broker-url"; // 默认的amq的broker

    // 应用服务器监控相关配置
    public static String APP_SER_BROKER = "AppServer.amq.broker-url";
    public static String APP_SER_QUEUE = "AppServer.amq.queue-name";
    public static String APP_SER_RETRY = "AppServer.amq.retry-queue";
    public static String TEST_MQ = "AppServer.amq.test-queue";

    // 警报器相关配置
    public static String ALARMER_BROKER = "Alarmer.amq.broker-url";
    public static String ALARMER_QUEUE = "Alarmer.amq.queue-name";
}
