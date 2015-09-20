package com.sprouts.spm_framework.amq;

import javax.jms.Message;



/**
 * AmqReceiver的messageListener必须继承的接口
 * 
 * @author Administrator
 * 
 */
public abstract class AmqMessageListener {

    public abstract void handleMessage(Message message);

}
