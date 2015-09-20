package com.sprouts.spm_framework.amq;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.sprouts.spm_framework.amq.message.BaseMessage;
import com.sprouts.spm_framework.utils.Logger;


public class AmqSender {
    private static ActiveMQConnectionFactory connectionFactory = null;
    private Connection connection = null;
    private Session session = null;
    private Destination destination;
    private MessageProducer producer = null;
    private Logger logger = new Logger();

    public AmqSender(AmqConfig config) {
        connectionFactory =
                new ActiveMQConnectionFactory(config.user, config.password, config.broker_url);
        try {
            connection = connectionFactory.createConnection();
            session = connection.createSession(config.transacted, config.acknowledgeMode);
            destination = session.createQueue(config.queueName);
            producer = session.createProducer(destination);
            producer.setDeliveryMode(config.deliveryMode);
            logger.info("AmqSender initialized done," + config.toString());
        } catch (JMSException e) {
            logger.error("init AmqSender failed:", e);
        }

    }

    /**
     * 初始化AMQ发送者，直接调用这个初始化
     * 
     * @param config
     * @return
     */
    public static AmqSender initAmqSender(AmqConfig config) {
        return new AmqSender(config);
    }

    public void close() {
        try {
            producer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            logger.error("error in close amq sender.");
        }
    }

    /**
     * 发送消息的方法
     * 
     * @param message
     */
    public void send(BaseMessage message) {
        if (message != null) {
            MapMessage msg = message.generateMessage(session);
            try {
                producer.send(msg);
            } catch (JMSException e) {
                logger.error("Exception in send AMQ!\n", e);
            }
        } else {
            logger.warn("the message you send is null.");
        }
    }

}
