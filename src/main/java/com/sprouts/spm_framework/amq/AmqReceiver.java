package com.sprouts.spm_framework.amq;

import java.util.ArrayList;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.sprouts.spm_framework.utils.Logger;

public class AmqReceiver {
    private static ActiveMQConnectionFactory connectionFactory = null;
    private Connection connection = null;
    private Session session = null;
    private Destination destination;
    private ArrayList<MessageConsumer> consumerList = null;
    private Logger logger = new Logger();
    private MessageListener messageListener;

    public AmqReceiver(AmqConfig config) {
        connectionFactory =
                new ActiveMQConnectionFactory(config.user, config.password, config.broker_url);
        try {
            connection = connectionFactory.createConnection();
            session = connection.createSession(config.transacted, config.acknowledgeMode);
            destination = session.createQueue(config.queueName);
            consumerList = new ArrayList<MessageConsumer>(config.consumerNum);
            for (int i = 0; i < config.consumerNum; i++) {
                MessageConsumer consumer = session.createConsumer(destination);
                consumerList.add(consumer);
            }
            messageListener = config.messageListener;
            logger.info("AmqReceiver initialized done," + config.toString());
        } catch (JMSException e) {
            logger.error("init AmqReceiver failed:", e);
        }
    }

    public static AmqReceiver initAmqReceiver(AmqConfig config) {
        return new AmqReceiver(config);
    }

    public void receive() {
        try {
            connection.start();
            for (MessageConsumer consumer : consumerList) {
                consumer.setMessageListener(messageListener);
                consumer.receive();
            }
        } catch (JMSException e) {
            logger.error("receive message error:", e);
        }
    }

}
