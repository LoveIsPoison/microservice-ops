package com.yonyou.cloud.ops.alert.ops.alert.domain.constants;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 报警状态
 * 
 * @author daniell
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AlertStatus {
	/**
	 * 报警信息已生成
	 */
	Trigger("已触发"),
	/**
	 * 报警信息已发生到用户
	 */
	Notice("已通知"),
	/**
	 * 用户正在处理报警信息
	 */
	Handle("处理中"),
	/**
	 * 报警已解除
	 */
	Finished("已完成"),

	UNKNOWN("未知");
	private String value;
	private String name;

	private AlertStatus(String name) {
		this.value = this.toString();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public static AlertStatus formatEnum(String key) {

		if (StringUtils.isBlank(key)) {
			return UNKNOWN;
		}
		try {
			return AlertStatus.valueOf(key);
		} catch (Exception e) {
			return UNKNOWN;
		}
	}
}
