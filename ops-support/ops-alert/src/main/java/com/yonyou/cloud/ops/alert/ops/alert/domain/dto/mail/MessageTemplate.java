package com.yonyou.cloud.ops.alert.ops.alert.domain.dto.mail;

public class MessageTemplate {
	/**
	 * 邮件接收者的地址
	 */
	private String toAddress[];

	/**
	 * 是否需要身份验证
	 */
	private boolean validate = false;
	/**
	 * 邮件主题
	 */
	private String subject;
	/**
	 * 邮件的文本内容
	 */
	private String content;
	public String[] getToAddress() {
		return toAddress;
	}
	public void setToAddress(String[] toAddress) {
		this.toAddress = toAddress;
	}
	/**
	 * 邮件附件的文件名
	 */
	private String[] attachFileNames;
	public boolean isValidate() {
		return validate;
	}
	public void setValidate(boolean validate) {
		this.validate = validate;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String[] getAttachFileNames() {
		return attachFileNames;
	}
	public void setAttachFileNames(String[] attachFileNames) {
		this.attachFileNames = attachFileNames;
	}
}
