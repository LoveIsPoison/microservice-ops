package com.yonyou.cloud.ops.alert.ops.alert.domain.dto;

import java.util.List;

import com.yonyou.cloud.ops.alert.ops.alert.entity.RuleInfo;
import com.yonyou.cloud.ops.alert.ops.alert.entity.RuleScope;
/**
 * 2018年3月8日15:48:54
 * @author daniell
 *
 */
public class RuleGroupBo {

	/**
	 * 規則組ID
	 */
	private Integer ruleGroupId; 
	/**
	 * 規則組名稱
	 */
	private String ruleGroupName;
	/**
	 * 規則組狀態
	 */
	private Boolean ruleGroupStatus;
	/**
	 * 規則列表
	 */
	private List<RuleInfo> rules;
	
	/**
	 * 服務器列表
	 */
	private List<RuleScope> ruleScopes;
	
	/**
	 * 用户组
	 */
	private List<GroupUsers> ugalist;

	
    private String descr;
    
    /**
     * 通知类型
     */
    private String alarmType;
	/**
	 * 邮件主题
	 */
    private String mailTitle;
    
    /**
     * 邮件内容
     */
    private String mailContent;
    
    private Boolean status;
    public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

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

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}


	public Integer getRuleGroupId() {
		return ruleGroupId;
	}

	public void setRuleGroupId(Integer ruleGroupId) {
		this.ruleGroupId = ruleGroupId;
	}

	public String getRuleGroupName() {
		return ruleGroupName;
	}

	public void setRuleGroupName(String ruleGroupName) {
		this.ruleGroupName = ruleGroupName;
	}

 
	public List<RuleInfo> getRules() {
		return rules;
	}

	public void setRules(List<RuleInfo> rules) {
		this.rules = rules;
	}

	public Boolean getRuleGroupStatus() {
		return ruleGroupStatus;
	}

	public void setRuleGroupStatus(Boolean ruleGroupStatus) {
		this.ruleGroupStatus = ruleGroupStatus;
	}

	public List<RuleScope> getRuleScopes() {
		return ruleScopes;
	}

	public void setRuleScopes(List<RuleScope> ruleScopes) {
		this.ruleScopes = ruleScopes;
	}

	public List<GroupUsers> getUgalist() {
		return ugalist;
	}

	public void setUgalist(List<GroupUsers> ugalist) {
		this.ugalist = ugalist;
	}

}
