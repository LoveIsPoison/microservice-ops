package com.yonyou.microservice.gate.server.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yonyou.microservice.gate.common.vo.log.LogInfo;
import com.yonyou.microservice.gate.server.config.ZuulConfig;

/**
 * ${DESCRIPTION}
 *
 * @author joy
 * @create 2017-07-01 15:16
 */
@FeignClient(value="ops-admin",configuration = {ZuulConfig.class})
public interface ILogService {
	/**
	 * 写入日志
	 * @param info
	 */
	  @RequestMapping(value="/api/log/save",method = RequestMethod.POST)
	  public void saveLog(LogInfo info);
}
