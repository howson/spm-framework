package com.sprouts.spm_framework.amq.message;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Session;

public class AppSerMessage extends BaseMessage {

    private String hostName;
    private String centerMachine;
    private int port;
    private String type;
    private int frequency;

    @Override
    public MapMessage generateMessage(Session session) {
        MapMessage msg = null;
        try {
            msg = session.createMapMessage();
            msg.setString("hostName", hostName);
            msg.setString("centerMachine", centerMachine);
            msg.setInt("port", port);
            msg.setString("type", type);
            msg.setInt("frequency", frequency);
        } catch (JMSException e) {
            logger.error("generate AppSerMessage error:", e);
        }
        return msg;
    }


    @Override
    public String toString() {
        return "AppSerMessage [hostName=" + hostName + ", centerMachine=" + centerMachine
                + ", port=" + port + ", type=" + type + ", frequency=" + frequency + "]";
    }

    @Override
    public BaseMessage parseMapMsgToBaseMsg(MapMessage msg) {
        AppSerMessage appMessage = new AppSerMessage();
        try {
            appMessage.hostName = msg.getString("hostName");
            appMessage.centerMachine = msg.getString("centerMachine");
            appMessage.frequency = msg.getInt("frequency");
            appMessage.port = msg.getInt("port");
            appMessage.type = msg.getString("type");
        } catch (JMSException e) {
            logger.error("error in parse map msg to base appser msg:\n", e);
        }
        return appMessage;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getCenterMachine() {
        return centerMachine;
    }

    public void setCenterMachine(String centerMachine) {
        this.centerMachine = centerMachine;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

}
