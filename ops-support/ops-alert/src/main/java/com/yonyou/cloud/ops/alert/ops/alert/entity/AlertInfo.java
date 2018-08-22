package com.yonyou.cloud.ops.alert.ops.alert.entity;

import java.util.Date;

import javax.persistence.*;

@Table(name = "alert_info")
public class AlertInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "alert_detail")
    private String alertDetail;

    @Column(name = "group_id")
    private Integer groupId;

    private String status;

    @Transient
    private String groupName;
    
    @Column(name="create_date")
    private Date CreateDate;
    
    /**
     * 操作時間
     */
    @Column(name="update_date")
    private Date updateDate;
    /**
     * 操作者
     */
    @Column(name="update_user")
    private String updateUser;
    
	@Transient
	private RuleGroup ruleGroup;
    
	/**
	 * 触发报警的服务名
	 */
	@Column(name="app_name")
	private String appName;
	
	@Column(name="alert_desc")
	private String alertDesc;
	
	public String getAlertDesc() {
		return alertDesc;
	}

	public void setAlertDesc(String alertDesc) {
		this.alertDesc = alertDesc;
	}
	
    public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

   
    public RuleGroup getRuleGroup() {
		return ruleGroup;
	}

	public void setRuleGroup(RuleGroup ruleGroup) {
		this.ruleGroup = ruleGroup;
	}

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
     * @return alert_detail
     */
    public String getAlertDetail() {
        return alertDetail;
    }

    /**
     * @param alertDetail
     */
    public void setAlertDetail(String alertDetail) {
        this.alertDetail = alertDetail;
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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Date getCreateDate() {
		return CreateDate;
	}

	public void setCreateDate(Date createDate) {
		CreateDate = createDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}