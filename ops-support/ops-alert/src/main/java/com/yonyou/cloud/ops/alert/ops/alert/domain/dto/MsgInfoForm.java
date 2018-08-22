package com.yonyou.cloud.ops.alert.ops.alert.domain.dto;

/**
 * 重redis中接收msgInfo对象
 * @author daniell
 *
 */
public class MsgInfoForm {

	private String path;
	private String type;
	private String logDate;
	private String host;
	private String msginfo;
	private String threadId;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLogDate() {
		return logDate;
	}

	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getMsginfo() {
		return msginfo;
	}

	public void setMsginfo(String msginfo) {
		this.msginfo = msginfo;
	}

	public String getThreadId() {
		return threadId;
	}

	public void setThreadId(String threadId) {
		this.threadId = threadId;
	}

	@Override
	public String toString() {
		return "MsgInfoVO [path=" + path + ", type=" + type + ", logDate=" + logDate + ", host=" + host + ", msginfo="
				+ msginfo + ", threadId=" + threadId + "]";
	}
}
