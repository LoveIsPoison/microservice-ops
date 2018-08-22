package com.yonyou.cloud.ops.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.cloud.common.annotation.YcApi;
import com.yonyou.cloud.common.beans.RestResultResponse;
import com.yonyou.cloud.ops.service.entity.ErkIstInfo;
import com.yonyou.cloud.ops.service.entity.EurekaApplications;
import com.yonyou.cloud.ops.service.service.OpsServiceService;


@RestController
public class OpsServiceController {
	
	@Autowired
	OpsServiceService opsServiceService;
	
	@RequestMapping(value="all",method=RequestMethod.GET)
	@YcApi
	public RestResultResponse<EurekaApplications> getAll(){
		return  new RestResultResponse<EurekaApplications>().data(opsServiceService.getAll()).success(true);
	}
	
	@RequestMapping(value="delete/{appId}/{instanceId}",method=RequestMethod.DELETE)
	@YcApi
	public RestResultResponse<?> deleteOne(@PathVariable("appId") String appId, @PathVariable("instanceId") String instanceId){
		return  new RestResultResponse<>().success(opsServiceService.delete(appId, instanceId));
	}
	
	@RequestMapping(value="allInst",method=RequestMethod.GET)
	@YcApi
	public RestResultResponse<List<ErkIstInfo>> getAllInst(){
		return  new RestResultResponse<List<ErkIstInfo>>().data(opsServiceService.getAllInst()).success(true);
	}
}
