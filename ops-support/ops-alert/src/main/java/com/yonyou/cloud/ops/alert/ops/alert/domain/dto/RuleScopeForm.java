package com.yonyou.cloud.ops.alert.ops.alert.domain.dto;

/**
 * 资源组信息
 * @author daniell
 *
 */
public class RuleScopeForm {
	private String type;

    private String[] ipAppList;
    
    private Integer groupId;
    
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

 

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String[] getIpAppList() {
		return ipAppList;
	}

	public void setIpAppList(String[] ipAppList) {
		this.ipAppList = ipAppList;
	} 

}
