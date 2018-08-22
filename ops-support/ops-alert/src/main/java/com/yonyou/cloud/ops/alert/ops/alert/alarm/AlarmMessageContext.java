package com.yonyou.cloud.ops.alert.ops.alert.alarm;

import java.util.List;

import com.yonyou.cloud.ops.alert.ops.alert.alarm.mails.AlarmMessageSender;
import com.yonyou.cloud.ops.alert.ops.alert.domain.dto.mail.MessageTemplate;

/**
 * 
 * @author daniell
 *
 */
public class AlarmMessageContext {
	AlarmMessageSender msgSend = null;

	public AlarmMessageContext(AlarmMessageSender msgSend) {
		this.msgSend = msgSend;
	}

	public void AlarmMessage(List<MessageTemplate> msgTemp) {
		this.msgSend.sendMessage(msgTemp);
	}
}