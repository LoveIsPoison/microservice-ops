package com.yonyou.cloud.ops.alert.ops.alert.domain.dto;

import com.yonyou.cloud.common.service.utils.EsPageQuery;

/**
 * 2018年3月8日15:33:46
 * @author daniell
 *
 */
public class RuleGroupSearchForm extends EsPageQuery{
	
	/**
	 * ruleGroupId
	 */

	private int ruleGroupId;
	/**
	 *  规则组名称
	 */
	private String groupName;
	/**
	 *  规则名称
	 */
	private String ruleName;
	/**
	 *  规则关键字
	 */
	private String keyword;
	/**
	 *  服务名称
	 */
	private String appName;
	/**
	 *  服务状态
	 */
	private String status; 

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getRuleGroupId() {
		return ruleGroupId;
	}

	public void setRuleGroupId(int ruleGroupId) {
		this.ruleGroupId = ruleGroupId;
	}

}
