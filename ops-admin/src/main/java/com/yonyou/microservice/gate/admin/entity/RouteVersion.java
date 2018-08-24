package com.yonyou.microservice.gate.admin.entity;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @author joy
 */
@Table(name = "gate_routes_version")
public class RouteVersion {
    @Id
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
