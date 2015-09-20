package com.sprouts.spm_framework.amq.message;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Session;

import com.sprouts.spm_framework.enums.MonitorType;
import com.sprouts.spm_framework.enums.TimeType;

public class AlarmTaskMessage extends BaseMessage {

    private TimeType timeType;
    private int frequency;
    private int threshold;
    private MonitorType type;
    private int userId;
    private int monitorId;
    private int retry_time;
    private String email;

    @Override
    public MapMessage generateMessage(Session session) {
        MapMessage msg = null;
        try {
            msg = session.createMapMessage();
            msg.setInt("timeType", timeType.type);
            msg.setInt("frequency", frequency);
            msg.setInt("threshold", threshold);
            msg.setString("type", type.type);
            msg.setInt("userId", userId);
            msg.setInt("monitorId", monitorId);
            msg.setInt("retry_time", retry_time);
            msg.setString("email", email);
        } catch (JMSException e) {
            logger.error("generate AlarmTaskMessage error:", e);
        }
        return msg;
    }

    @Override
    public String toString() {
        return "AlarmTaskMessage [timeType=" + timeType + ", frequency=" + frequency
                + ", threshold=" + threshold + ", type=" + type + ", userId=" + userId
                + ", monitorId=" + monitorId + ", retry_time=" + retry_time + ", email=" + email
                + "]";
    }

    @Override
    public BaseMessage parseMapMsgToBaseMsg(MapMessage msg) {
        AlarmTaskMessage alarmMessage = new AlarmTaskMessage();
        try {
            alarmMessage.timeType = TimeType.valueOf(String.valueOf(msg.getInt("timeType")));
            alarmMessage.threshold = msg.getInt("threshold");
            alarmMessage.frequency = msg.getInt("frequency");
            alarmMessage.userId = msg.getInt("userId");
            alarmMessage.type = MonitorType.valueOf(msg.getString("type"));
            alarmMessage.email = msg.getString("email");
            alarmMessage.monitorId = msg.getInt("monitorId");
            alarmMessage.retry_time = msg.getInt("retry_time");
        } catch (JMSException e) {
            logger.error("error in parse map msg to base appser msg:\n", e);
        }
        return alarmMessage;
    }

    public TimeType getTimeType() {
        return timeType;
    }

    public void setTimeType(TimeType timeType) {
        this.timeType = timeType;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public MonitorType getType() {
        return type;
    }

    public void setType(MonitorType type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(int monitorId) {
        this.monitorId = monitorId;
    }

    public int getRetry_time() {
        return retry_time;
    }

    public void setRetry_time(int retry_time) {
        this.retry_time = retry_time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
