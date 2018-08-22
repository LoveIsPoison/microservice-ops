package com.yonyou.cloud.ops.task.entity;

import java.io.Serializable;

/**
 * 定时任务实体
 * 
 * @author yang
 */
public class ScheduleEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 *  任务名
	 */
	private String jobName; 
	/**
	 *  任务组
	 */
	private String jobGroup; 
	/**
	 *  cron表达式
	 */
	private String cronExpression; 
	/**
	 * 状态
	 */
	private String status; 
	/**
	 * 描述
	 */
	private String description; 
	/**
	 *  执行的接口url
	 */
	private String url;
	/**
	 * 任务类型
	 */
	private ExecuteType executeType;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ScheduleEntity() {
		super();
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public ExecuteType getExecuteType() {
		return executeType;
	}

	public void setExecuteType(ExecuteType executeType) {
		this.executeType = executeType;
	}

	@Override
	public String toString() {
		return "ScheduleEntity [jobName=" + jobName + ", jobGroup=" + jobGroup + ", cronExpression=" + cronExpression
				+ ", status=" + status + ", description=" + description + ", url=" + url +", executeType=" +executeType+"]";
	}
}