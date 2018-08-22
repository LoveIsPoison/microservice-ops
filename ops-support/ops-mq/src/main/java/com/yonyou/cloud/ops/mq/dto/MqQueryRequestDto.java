package com.yonyou.cloud.ops.mq.dto;

import com.yonyou.cloud.common.service.utils.EsPageQuery;

public class MqQueryRequestDto extends EsPageQuery {
	
	private String host;
	
	private String msgKey;
	
	private String data;
	
	private String produceStatus;
	
	private String consumeStatus;
	
	private String success;
	
	private Long occurStartTime;
	
	private Long occurEndTime;
	
	private String exchangeName;
	
	private String routingKey;
	
	private String sender;
	
	private Integer consumeFailTimes;
	
	private Integer produceFailTimes;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getMsgKey() {
		return msgKey;
	}

	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public Long getOccurStartTime() {
		return occurStartTime;
	}

	public void setOccurStartTime(Long occurStartTime) {
		this.occurStartTime = occurStartTime;
	}

	public Long getOccurEndTime() {
		return occurEndTime;
	}

	public void setOccurEndTime(Long occurEndTime) {
		this.occurEndTime = occurEndTime;
	}

	public String getExchangeName() {
		return exchangeName;
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	public String getRoutingKey() {
		return routingKey;
	}

	public void setRoutingKey(String routingKey) {
		this.routingKey = routingKey;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public Integer getConsumeFailTimes() {
		return consumeFailTimes;
	}

	public void setConsumeFailTimes(Integer consumeFailTimes) {
		this.consumeFailTimes = consumeFailTimes;
	}

	public Integer getProduceFailTimes() {
		return produceFailTimes;
	}

	public void setProduceFailTimes(Integer produceFailTimes) {
		this.produceFailTimes = produceFailTimes;
	}

	public String getProduceStatus() {
		return produceStatus;
	}

	public void setProduceStatus(String produceStatus) {
		this.produceStatus = produceStatus;
	}

	public String getConsumeStatus() {
		return consumeStatus;
	}

	public void setConsumeStatus(String consumeStatus) {
		this.consumeStatus = consumeStatus;
	}
	
}
