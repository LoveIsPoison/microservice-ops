package com.yonyou.cloud.ops.alert.ops.alert.entity;

import javax.persistence.*;

@Table(name = "rule_scope")
public class RuleScope {
    @Id
    private Integer id;

    private String type;

    @Column(name = "ip_app")
    private String ipApp;

    @Column(name = "group_id")
    private Integer groupId;

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
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return ip_app
     */
    public String getIpApp() {
        return ipApp;
    }

    /**
     * @param ipApp
     */
    public void setIpApp(String ipApp) {
        this.ipApp = ipApp;
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
}