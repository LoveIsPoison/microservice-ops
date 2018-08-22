package com.yonyou.cloud.ops.alert.ops.alert.domain;

public class UserGroupAlertBo {
    private Integer id;

    private Integer userGroupId;

    private Integer ruleGroupId;
    
    private String userGroupName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public String getUserGroupName() {
		return userGroupName;
	}

	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}
    
}
