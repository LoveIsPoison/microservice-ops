package com.yonyou.microservice.gate.server.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yonyou.microservice.gate.common.vo.authority.PermissionInfo;
import com.yonyou.microservice.gate.common.vo.user.UserInfo;
import com.yonyou.microservice.gate.server.config.ZuulConfig;


/**
 * ${DESCRIPTION}
 *
 * @author joy
 * @create 2017-06-21 8:11
 */
@FeignClient(value = "ops-admin",configuration = {ZuulConfig.class})
@RequestMapping("api")
public interface IUserService {
	/**
	 * 读取用户信息
	 * @param username
	 * @return
	 */
	  @RequestMapping(value = "/user/username/{username}", method = RequestMethod.GET)
	  public UserInfo getUserByUsername(@PathVariable("username") String username);
	  /**
	   * 读取用户允许的访问资源
	   * @param username
	   * @return
	   */
	  @RequestMapping(value = "/user/un/{username}/permissions", method = RequestMethod.GET)
	  public List<PermissionInfo> getPermissionByUsername(@PathVariable("username") String username);
	  /**
	   * 读取所有允许的访问资源
	   * @param username
	   * @return
	   */
	  @RequestMapping(value = "/permissions", method = RequestMethod.GET)
	  List<PermissionInfo> getAllPermissionInfo();
}
