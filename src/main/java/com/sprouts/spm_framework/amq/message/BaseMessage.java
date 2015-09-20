package com.sprouts.spm_framework.amq.message;

import javax.jms.MapMessage;
import javax.jms.Session;

import com.sprouts.spm_framework.utils.Logger;

public abstract class BaseMessage {

    public static Logger logger = new Logger();

    public abstract MapMessage generateMessage(Session session);

    public abstract String toString();

    public abstract BaseMessage parseMapMsgToBaseMsg(MapMessage msg);

}
