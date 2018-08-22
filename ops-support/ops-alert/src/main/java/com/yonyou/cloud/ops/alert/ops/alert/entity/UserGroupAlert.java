package com.yonyou.cloud.ops.alert.ops.alert.entity;

import javax.persistence.*;

@Table(name = "user_group_alert")
public class UserGroupAlert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_group_id")
    private Integer userGroupId;

    @Column(name = "rule_group__id")
    private Integer ruleGroupId;

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
     * @return user_group_id
     */
    public Integer getUserGroupId() {
        return userGroupId;
    }

    /**
     * @param userGroupId
     */
    public void setUserGroupId(Integer userGroupId) {
        this.userGroupId = userGroupId;
    }

    /**
     * @return rule_group__id
     */
    public Integer getRuleGroupId() {
        return ruleGroupId;
    }

    /**
     * @param ruleGroupId
     */
    public void setRuleGroupId(Integer ruleGroupId) {
        this.ruleGroupId = ruleGroupId;
    }
}