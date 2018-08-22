package com.yonyou.cloud.ops.alert.ops.alert.alarm.mails;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yonyou.cloud.ops.alert.ops.alert.domain.dto.mail.MessageTemplate;

@Service
public class EmailMessage extends AlarmMessageSender {

	private static final Logger log = LoggerFactory.getLogger(EmailMessage.class);

	/**
	 * 发送邮件的服务器的IP和端口
	 */
	@Value("${mail.server.host}")
	private String mailServerHost;
	/**
	 * 发送邮件的服务器的IP和端口
	 */
	@Value("${mail.server.port}")
	private String mailServerPort;

	/**
	 * 登陆邮件发送服务器的用户名和密码
	 */
	@Value("${mail.userName}")
	private String userName;
	/**
	 * 登陆邮件发送服务器的用户名和密码
	 */
	@Value("${mail.password}")
	private String password;

	/**
	 * 邮件发送者的地址
	 */
	@Value("${mail.fromAddress}")
	private String fromAddress;

	@Override
	public void sendMessage(List<MessageTemplate> msgTemplist) {
		log.info("发送email 了 哈哈哈哈");
		for (MessageTemplate msgTemp : msgTemplist) {
			/*
			 * 这个类主要是设置邮件 mailServerHost:邮箱服务器 userName:发送方邮件用户名 password:发送方邮件密码
			 * fromAddress:发送方邮件用户名 toAddress:接收方邮件用户名
			 */
			MailSenderInfo mailInfo = new MailSenderInfo();
			mailInfo.setMailServerHost(mailServerHost);
			mailInfo.setMailServerPort(mailServerPort);
			mailInfo.setValidate(true);
			mailInfo.setUserName(userName);
			mailInfo.setPassword(password);
			mailInfo.setFromAddress(fromAddress);
			mailInfo.setToAddress(msgTemp.getToAddress());
			mailInfo.setSubject(msgTemp.getSubject());
			mailInfo.setContent(msgTemp.getContent());
			// 这个类主要来发送邮件
			SimpleMailSender sms = new SimpleMailSender();
			boolean con = false;

			try {
				con = sms.sendTextMail(mailInfo);// 发送文体格式
			} catch (Exception e) {
				e.printStackTrace();
				log.info("错误原因是:" + e.getMessage());
			}
			if (con) {
				log.info("发送成功!");
			} else {
				log.info("发送失败!");
			}
		}

	}
}
