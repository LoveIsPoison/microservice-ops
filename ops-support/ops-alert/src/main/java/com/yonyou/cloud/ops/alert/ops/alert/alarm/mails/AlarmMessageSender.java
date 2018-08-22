package com.yonyou.cloud.ops.alert.ops.alert.alarm.mails;

import java.util.List;

import com.yonyou.cloud.ops.alert.ops.alert.domain.dto.mail.MessageTemplate;

/**
 * 抽象产品  
 * @author daniell
 *
 */
public abstract class AlarmMessageSender {
	
	private String name;

	public abstract void sendMessage(List<MessageTemplate> msgTemp);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}