package com.yonyou.cloud.ops.alert.ops.alert.domain.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.yonyou.cloud.common.service.utils.EsPageQuery;
import com.yonyou.cloud.ops.alert.ops.alert.entity.RuleGroup;
import com.yonyou.cloud.ops.alert.ops.alert.entity.RuleScope;

@JsonInclude(Include.NON_NULL)
public class RuleInfoDto extends EsPageQuery{
 
	private String name;
   
    private String ruleDesc;
    
	private String keyword;

    private Integer time;

    private Integer count; 
    
    private List<RuleGroup> ruleGroup;
    
    private List<RuleScope> ruleScorp;
    
    private String groupName;
    
    public List<RuleGroup> getRuleGroup() {
		return ruleGroup;
	}
	public void setRuleGroup(List<RuleGroup> ruleGroup) {
		this.ruleGroup = ruleGroup;
	}
	public List<RuleScope> getRuleScorp() {
		return ruleScorp;
	}
	public void setRuleScorp(List<RuleScope> ruleScorp) {
		this.ruleScorp = ruleScorp;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRuleDesc() {
		return ruleDesc;
	}
	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


}
