package com.yonyou.cloud.ops.service.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import com.yonyou.cloud.ops.service.entity.ErkIstInfo;
import com.yonyou.cloud.ops.service.entity.EurekaApplications;

@Service
public class OpsServiceService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EurekaApi eurekaApi;
	
	@Autowired
	EurekaClient eurekaClient;

	public EurekaApplications getAll() {
		EurekaApplications applications = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			applications = mapper.readValue(eurekaApi.getAll(), EurekaApplications.class);
		} catch (JsonParseException e) {
			logger.warn("get eureka instanceList faild", e);
		} catch (JsonMappingException e) {
			logger.warn("get eureka instanceList faild", e);
		} catch (IOException e) {
			logger.warn("get eureka instanceList faild", e);
		}

		return applications;
	}
	
	public List<ErkIstInfo> getAllInst(){
		List<ErkIstInfo> erkIstInfoList = new ArrayList<ErkIstInfo>();
		Applications apps = eurekaClient.getApplications();
        for (Application app : apps.getRegisteredApplications()) {
            for (com.netflix.appinfo.InstanceInfo inst : app.getInstances()) {
            	ErkIstInfo erkIstInfo = new ErkIstInfo();
            	BeanUtils.copyProperties(inst, erkIstInfo);
            	erkIstInfoList.add(erkIstInfo);
            }
        }
        return erkIstInfoList;
	}

	public boolean delete(String appId, String instanceId) {
		try {
			eurekaApi.deleteOne(appId, instanceId);
		} catch (Exception e) {
			logger.error("delete {} error! message:{}",instanceId,e.getMessage());
			return false;
		}
		return true;
	}
	

}
