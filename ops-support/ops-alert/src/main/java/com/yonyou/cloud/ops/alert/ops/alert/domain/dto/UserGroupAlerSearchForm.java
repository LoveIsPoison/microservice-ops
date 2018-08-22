package com.yonyou.cloud.ops.alert.ops.alert.domain.dto;


public class UserGroupAlerSearchForm {
    private Integer userGroupId;
    private Integer ruleGroupId;
	public Integer getUserGroupId() {
		return userGroupId;
	}
	public void setUserGroupId(Integer userGroupId) {
		this.userGroupId = userGroupId;
	}
	public Integer getRuleGroupId() {
		return ruleGroupId;
	}
	public void setRuleGroupId(Integer ruleGroupId) {
		this.ruleGroupId = ruleGroupId;
	}
}
