package com.yonyou.microservice.gate.server.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yonyou.cloud.common.jwt.JwtInfo;
import com.yonyou.microservice.gate.server.config.ZuulConfig;


/**
 * ${DESCRIPTION}
 *
 * @author joy
 * @create 2017-06-21 8:11
 */
@FeignClient(value = "ops-auth",configuration = {ZuulConfig.class})
public interface IAuthService {
	/**
	 * 读取用户信息
	 * @param username
	 * @return
	 */
	  @RequestMapping(value = "/jwt/userInfo", method = RequestMethod.POST)
	  public JwtInfo getUserInfo(String jwt) throws Exception;
}
