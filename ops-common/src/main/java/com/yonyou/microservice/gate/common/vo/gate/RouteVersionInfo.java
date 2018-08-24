package com.yonyou.microservice.gate.common.vo.gate;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @author joy
 */
public class RouteVersionInfo {
	private Integer id;   
    private String serviceId;
    private String version;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
    

}
