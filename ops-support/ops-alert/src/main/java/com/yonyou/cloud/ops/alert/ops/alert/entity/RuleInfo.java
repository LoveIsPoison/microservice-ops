package com.yonyou.cloud.ops.alert.ops.alert.entity;

import javax.persistence.*;

@Table(name = "rule_info")
public class RuleInfo {
    @Id
    private Integer id;

    @Column(name = "rule_desc")
    private String ruleDesc;

    private String keyword;

    private Integer time;

    private Integer count;
    
    private String name;

    @Column(name = "group_id")
    private Integer groupId;
    
    @Transient
    private RuleGroup ruleGroup;
    
    /**
     * 规则状态{1：启用，0：禁用}
     */
    private Boolean status;
	/**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return rule_desc
     */
    public String getRuleDesc() {
        return ruleDesc;
    }

    /**
     * @param ruleDesc
     */
    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }

    /**
     * @return keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * @param keyword
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * @return time
     */
    public Integer getTime() {
        return time;
    }

    /**
     * @param time
     */
    public void setTime(Integer time) {
        this.time = time;
    }

    /**
     * @return count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * @return group_id
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * @param groupId
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

	public RuleGroup getRuleGroup() {
		return ruleGroup;
	}

	public void setRuleGroup(RuleGroup ruleGroup) {
		this.ruleGroup = ruleGroup;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
 
}