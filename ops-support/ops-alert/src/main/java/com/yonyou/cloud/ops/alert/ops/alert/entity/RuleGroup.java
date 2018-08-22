package com.yonyou.cloud.ops.alert.ops.alert.entity;

import javax.persistence.*;

@Table(name = "rule_group")
public class RuleGroup {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String name;

    private String descr;
    
    /**
     * 通知类型
     */
    @Column(name="alarm_type")
    private String alarmType;
    
    /**
     * 邮件主题
     */
    @Column(name="mail_title")
    private String mailTitle;
    
    /**
     * 邮件内容
     */
    @Column(name="mail_content")
    private String mailContent;
    
    private Boolean status;

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
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

 
}