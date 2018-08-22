package com.yonyou.cloud.ops.alert.ops.alert.domain.dto;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 接受的报警信息
 * @author daniell
 *
 */
@ApiModel(value="AlertInfoForm",description="报警信息")
public class AlertInfoForm implements Serializable{
    
	/**
     * 发生报警的时间
     */
   @ApiModelProperty(value="发生报警的时间",name="CreateDate",required=true)
    private Date CreateDate; 
     
    /**
     * 发生报警的服务名称
     */
    private String appName; 
    
	/**
     * 发送报警的服务器IP
     */
    private String appIp;
	
	/**
	 * 报警信息描述
	 */
    @ApiModelProperty(value="报警信息描述",name="alertDesc",required=true)
    private String alertDesc;
   
    /**
     * 报警详情信息
     */
    @ApiModelProperty(value="报警详情信息",name="alertDetail",required=true)
    private String alertDetail;  
    
    /**
     * 报警组名称
     */
    @ApiModelProperty(value="报警组名称",name="RuleGroupName",required=true,example="cup报警")
    private String ruleGroupName;
    /**
     * 报警用户组ID
     */
    @ApiModelProperty(value="报警用户组IDs",name="userGroupIds",required=true)
    private Integer[] userGroupIds;
    
    /**
     * 报警方式{phone;mail}
     */
    @ApiModelProperty(value="报警方式{phone;mail}",name="alarmType",required=true)
    private String alarmType;
    
    /**
     * 报警邮件标题
     */
    @ApiModelProperty(value="报警邮件标题",name="mailTitle",required=true)
    private String mailTitle;
    
    /**
     * 报警邮件内容
     */
    @ApiModelProperty(value="报警邮件内容",name="mailContent",required=true)
    private String mailContent;
   
 
	public Integer[] getUserGroupIds() {
		return userGroupIds;
	}
	public void setUserGroupIds(Integer[] userGroupIds) {
		this.userGroupIds = userGroupIds;
	}
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
	public Date getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(Date createDate) {
		CreateDate = createDate;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
 
	public String getAlertDesc() {
		return alertDesc;
	}
	public void setAlertDesc(String alertDesc) {
		this.alertDesc = alertDesc;
	}
 
	public String getAlertDetail() {
		return alertDetail;
	}
	public void setAlertDetail(String alertDetail) {
		this.alertDetail = alertDetail;
	}
	
    public String getAppIp() {
		return appIp;
	}
	public void setAppIp(String appIp) {
		this.appIp = appIp;
	}
	public String getRuleGroupName() {
		return ruleGroupName;
	}
	public void setRuleGroupName(String ruleGroupName) {
		this.ruleGroupName = ruleGroupName;
	}
 
}
