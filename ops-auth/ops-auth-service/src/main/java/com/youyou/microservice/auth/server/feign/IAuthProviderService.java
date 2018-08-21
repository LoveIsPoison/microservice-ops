package com.youyou.microservice.auth.server.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yonyou.microservice.gate.common.vo.user.AuthProviderInfo;
/**
 *  @author joy
 */
@FeignClient(value = "ops-admin",configuration = {})
public interface IAuthProviderService {
  /**
   * 获取登录等认证提供者的配置
   * @return 配置列表
   */
  @RequestMapping(value = "/api/authProvider/all", method = RequestMethod.GET)
  public List<AuthProviderInfo> getAuthMapping();
}
