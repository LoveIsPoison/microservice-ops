package com.yonyou.microservice.gate.server.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yonyou.microservice.gate.common.vo.authority.IgnoreUriInfo;
import com.yonyou.microservice.gate.server.config.ZuulConfig;


/**
 * ${DESCRIPTION}
 *
 * @author joy
 * @create 2017-06-21 8:11
 */
@FeignClient(value = "ops-admin",configuration = {ZuulConfig.class})
@RequestMapping("api")
public interface IIgnoreUriService {
	/**
	 * 读取所有忽略的url
	 * @return
	 */
  @RequestMapping(value = "/ignoreUri/all", method = RequestMethod.GET)
  List<IgnoreUriInfo> getIgnoreUris();

}
