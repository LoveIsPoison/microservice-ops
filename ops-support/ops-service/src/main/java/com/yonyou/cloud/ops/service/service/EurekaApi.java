package com.yonyou.cloud.ops.service.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="SERVER-EUREKA",url="${server.eureka.url:http://10.180.4.221:8761/}")
public interface EurekaApi {
	
	@RequestMapping(value = "/eureka/apps",method=RequestMethod.GET,headers="Accept=application/json")
    public String getAll();
	
//	@RequestMapping(value = "/eureka/apps",method=RequestMethod.GET,headers="Accept=application/json")
//	public String deleteOne();
//	
	@RequestMapping(value = "/eureka/apps/{appID}/{instanceID}",method=RequestMethod.DELETE,headers="Accept=application/json")
	public String deleteOne(@PathVariable("appID") String appId, @PathVariable("instanceID") String instanceID);
}
