//package com.yonyou.cloud.ops.alert.ops.alert.mail;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailMessage{
//
//	private static final Logger log = LoggerFactory.getLogger(EmailMessage.class);
// 
//	public static void sendMessage() {
//		log.info("发送email 了 哈哈哈哈"); 
//			/*
//			 * 这个类主要是设置邮件 mailServerHost:邮箱服务器 userName:发送方邮件用户名 password:发送方邮件密码
//			 * fromAddress:发送方邮件用户名 toAddress:接收方邮件用户名
//			 */
//			MailSenderInfo mailInfo = new MailSenderInfo();
//			mailInfo.setMailServerHost("smtp.163.com");
//			mailInfo.setMailServerPort("25");
//			mailInfo.setValidate(true);
//			mailInfo.setUserName("yonyouauto_hu@163.com");
//			mailInfo.setPassword("admin123");
//			mailInfo.setFromAddress("yonyouauto_hu@163.com");
//			mailInfo.setToAddress("daniell_hu@163.com");
//			mailInfo.setSubject("邮件测试");
//			mailInfo.setContent("这是一份测试邮件，不用注意查收");
//			// 这个类主要来发送邮件
//			SimpleMailSender sms = new SimpleMailSender();
//			boolean con = false;
//
//			try {
//				con = sms.sendTextMail(mailInfo);// 发送文体格式
//			} catch (Exception e) {
//				e.printStackTrace();
//				System.out.println("错误原因是:" + e.getMessage());
//			}
//			if (con) {
//				System.out.println("发送成功!");
//			} else {
//				System.out.println("发送失败!");
//			}
//
//	}
//	
//	public static void main(String[] args) {
//		EmailMessage.sendMessage();
//	}
//}
