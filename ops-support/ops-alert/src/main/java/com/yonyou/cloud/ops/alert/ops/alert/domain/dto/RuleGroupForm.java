package com.yonyou.cloud.ops.alert.ops.alert.domain.dto;

import java.util.List;
public class RuleGroupForm {
 
	
    private Integer id;

    private String name;

    private String descr;

    private Boolean status;
    private String[] ipAppList;
    
    private Integer[] userGroupIds;
    private String alarmType;
    private String mailTitle;
    private String mailContent;
   
    private List<RuleInfoForm> alarmRules;

	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	public String getMailTitle() {
		return mailTitle;
	}

	public void setMailTitle(String mailTitle) {
		this.mailTitle = mailTitle;
	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}
 
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String[] getIpAppList() {
		return ipAppList;
	}

	public void setIpAppList(String[] ipAppList) {
		this.ipAppList = ipAppList;
	}

	public Integer[] getUserGroupIds() {
		return userGroupIds;
	}

	public void setUserGroupIds(Integer[] userGroupIds) {
		this.userGroupIds = userGroupIds;
	}

	public List<RuleInfoForm> getAlarmRules() {
		return alarmRules;
	}

	public void setAlarmRules(List<RuleInfoForm> alarmRules) {
		this.alarmRules = alarmRules;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

 
}
