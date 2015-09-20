package com.sprouts.spm_framework;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import com.sprouts.spm_framework.amq.AmqMessageListener;
import com.sprouts.spm_framework.amq.message.AppSerMessage;

public class Test {

    public static void main(String[] args) {
        // AmqConfig config =
        // AmqConfig.getDefaultConfig(SPMConstants.TEST_MQ, AppSerMessage.class,
        // new TestListener());
        // AmqReceiver receiver = AmqReceiver.initAmqReceiver(config);
        // receiver.receive();
        String content = "hello doggy %s, i love %s";
        System.out.println(String.format(content, "lqh", "holy"));
        // ArrayList<String> mailList = new ArrayList<String>();
        // mailList.add("haoshen.liu@foxmail.com");
        // MailService.getInstance().send(mailList, content);
    }

    static class TestListener extends AmqMessageListener implements MessageListener {

        @Override
        public void onMessage(Message arg0) {
            handleMessage(arg0);
        }

        @Override
        public void handleMessage(Message message) {
            MapMessage msg = (MapMessage) message;
            AppSerMessage asMsg = new AppSerMessage();
            asMsg = (AppSerMessage) asMsg.parseMapMsgToBaseMsg(msg);
            System.out.println(asMsg.toString());
        }

    }
}
