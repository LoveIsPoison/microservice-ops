package com.yonyou.microservice.auth.client.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yonyou.cloud.common.beans.RestResultResponse;

/**
 * @author joy
 */
@FeignClient(value = "${auth.serviceId}",configuration = {})
public interface ServiceAuthFeign {
	/**
	 * 获取允许访问的微服务id
	 * @param serviceId，微服务id
	 * @param secret，密码
	 * @return
	 */
    @RequestMapping(value = "/client/myClient")
    public RestResultResponse<List<String>> getAllowedClient(@RequestParam("serviceId") String serviceId, @RequestParam("secret") String secret);
    /**
     * 读取访问token
     * @param clientId，微服务id
     * @param secret，密码
     * @return
     */
    @RequestMapping(value = "/client/token",method = RequestMethod.POST)
    public RestResultResponse getAccessToken(@RequestParam("clientId") String clientId, @RequestParam("secret") String secret);


}
