package com.yonyou.cloud.ops.alert.ops.alert.domain.dto;

public class RuleInfoForm {
    
    private String ruleDesc;
    private String  ruleKeyword;
    private Integer ruleTime;
    private Integer ruleCount;
	private String  ruleName; 
    private Integer groupId; 
    private Boolean ruleStatus;
    
    public String getRuleDesc() {
		return ruleDesc;
	}
	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}
	public String getRuleKeyword() {
		return ruleKeyword;
	}
	public void setRuleKeyword(String ruleKeyword) {
		this.ruleKeyword = ruleKeyword;
	}
	public Integer getRuleTime() {
		return ruleTime;
	}
	public void setRuleTime(Integer ruleTime) {
		this.ruleTime = ruleTime;
	}
	public Integer getRuleCount() {
		return ruleCount;
	}
	public void setRuleCount(Integer ruleCount) {
		this.ruleCount = ruleCount;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public Boolean getRuleStatus() {
		return ruleStatus;
	}
	public void setRuleStatus(Boolean ruleStatus) {
		this.ruleStatus = ruleStatus;
	}

}
