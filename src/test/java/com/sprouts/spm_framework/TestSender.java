package com.sprouts.spm_framework;

import com.sprouts.spm_framework.amq.AmqConfig;
import com.sprouts.spm_framework.amq.AmqSender;
import com.sprouts.spm_framework.amq.message.AppSerMessage;
import com.sprouts.spm_framework.utils.SPMConstants;

public class TestSender {
    public static void main(String[] args) {
        AmqConfig config =
                AmqConfig.getDefaultConfig(SPMConstants.TEST_MQ, AppSerMessage.class, null);
        AmqSender sender = AmqSender.initAmqSender(config);
        AppSerMessage message = new AppSerMessage();
        message.setHostName("125.216.243.192");
        message.setCenterMachine("125.216.243.186");
        message.setFrequency(5000);
        message.setPort(8080);
        message.setType("tomcat");
        sender.send(message);
        sender.close();

    }
}
