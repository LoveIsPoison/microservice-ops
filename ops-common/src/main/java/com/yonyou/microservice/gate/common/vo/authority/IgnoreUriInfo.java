package com.yonyou.microservice.gate.common.vo.authority;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @author joy
 */
public class IgnoreUriInfo {
	private int id;
    
	private String uri;
	
	private String serviceId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}


}
