package com.sprouts.spm_framework.amq;

import javax.jms.DeliveryMode;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;

import com.sprouts.spm_framework.utils.ConfigUtils;
import com.sprouts.spm_framework.utils.SPMConstants;

public class AmqConfig {

    public int acknowledgeMode;
    public int deliveryMode;
    public Class<?> messageType;
    public String queueName = null;
    public boolean transacted = false;
    public String user = null;
    public String password = null;
    public String broker_url = null;
    public MessageListener messageListener;
    public int consumerNum = 0;

    public AmqConfig() {
        super();
    }

    public AmqConfig(int acknowledgeMode, int deliveryMode, Class<?> messageType, String queueName,
            boolean transacted, String user, String password, String broker_url,
            MessageListener messageListener, int consumerNum) {
        super();
        this.acknowledgeMode = acknowledgeMode;
        this.deliveryMode = deliveryMode;
        this.messageType = messageType;
        this.queueName = queueName;
        this.transacted = transacted;
        this.user = user;
        this.password = password;
        this.broker_url = broker_url;
        this.messageListener = messageListener;
        this.consumerNum = consumerNum;
    }

    /**
     * 得到默认的amq配置
     * 
     * @param queueName
     * @param messageType
     * @return
     */
    public static AmqConfig getDefaultConfig(String queueName, Class<?> messageType,
            MessageListener messageListener) {
        AmqConfig config = new AmqConfig();
        config.acknowledgeMode = Session.AUTO_ACKNOWLEDGE;
        config.deliveryMode = DeliveryMode.PERSISTENT;
        config.messageType = messageType;
        config.user = ActiveMQConnection.DEFAULT_USER;
        config.password = ActiveMQConnection.DEFAULT_PASSWORD;
        config.broker_url = ConfigUtils.getString(SPMConstants.DEFAULT_BROKER);
        config.transacted = false;
        config.queueName = ConfigUtils.getString(queueName);
        config.consumerNum = 1;
        config.messageListener = messageListener;
        return config;
    }

    /**
     * 得到默认的amq配置
     * 
     * @param queueName
     * @param messageType
     * @return
     */
    public static AmqConfig getDefaultConfig(String broker, String queueName, Class<?> messageType,
            MessageListener messageListener) {
        AmqConfig config = new AmqConfig();
        config.acknowledgeMode = Session.AUTO_ACKNOWLEDGE;
        config.deliveryMode = DeliveryMode.PERSISTENT;
        config.messageType = messageType;
        config.user = ActiveMQConnection.DEFAULT_USER;
        config.password = ActiveMQConnection.DEFAULT_PASSWORD;
        config.broker_url = ConfigUtils.getString(broker);
        config.transacted = false;
        config.queueName = ConfigUtils.getString(queueName);
        config.consumerNum = 1;
        config.messageListener = messageListener;
        return config;
    }

    /**
     * 得到用户自定义的amq配置
     * 
     * @param aknowledgeMode
     * @param deliveryMode
     * @param messageType
     * @param queueName
     * @param transaction supported
     * @param user
     * @param password
     * @param broker-url
     * @return
     */
    public static AmqConfig getSpecifiedConfig(int akm, int dm, Class<?> mt, String qn, boolean tr,
            String user, String pwd, String url, MessageListener messageListener, int consumerNum) {
        return new AmqConfig(akm, dm, mt, qn, tr, user, pwd, url, messageListener, consumerNum);
    }

    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    @Override
    public String toString() {
        return "AmqConfig [acknowledgeMode=" + acknowledgeMode + ", deliveryMode=" + deliveryMode
                + ", messageType=" + messageType + ", queueName=" + queueName + ", transacted="
                + transacted + ", user=" + user + ", password=" + password + ", broker_url="
                + broker_url + "]";
    }

}
