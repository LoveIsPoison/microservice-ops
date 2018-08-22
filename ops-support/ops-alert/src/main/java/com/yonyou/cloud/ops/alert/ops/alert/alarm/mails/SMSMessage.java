package com.yonyou.cloud.ops.alert.ops.alert.alarm.mails;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.cloud.ops.alert.ops.alert.domain.dto.mail.MessageTemplate;

public class SMSMessage extends AlarmMessageSender {

	private static final Logger log = LoggerFactory.getLogger(SMSMessage.class);

	@Override
	public void sendMessage(List<MessageTemplate> msgTemp) {
		log.info("发送短信了 哈哈哈哈");
		for (MessageTemplate message : msgTemp) {
			log.info("send SMS : {}", message);
		}
	}
}
