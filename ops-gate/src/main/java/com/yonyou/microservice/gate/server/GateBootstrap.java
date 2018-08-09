package com.yonyou.microservice.gate.server;


import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.spring4all.swagger.EnableSwagger2Doc;
import com.yonyou.cloud.common.beans.RestResultResponse;
import com.yonyou.cloud.zuul.db.api.EnableZuulProxyStore;
import com.yonyou.microservice.auth.client.EnableAceAuthClient;
import com.yonyou.microservice.filter.annotation.EnableGroovyFilter;
import com.yonyou.microservice.gate.common.msg.ZuulExceptionResponse;
import com.yonyou.microservice.gate.ratelimit.EnableOpsGateRateLimit;
import com.yonyou.microservice.gate.ratelimit.config.IUserPrincipal;
import com.yonyou.microservice.gate.ratelimit.config.properties.RateLimitProperties;
import com.yonyou.microservice.gate.server.config.UserPrincipal;
import com.yonyou.microservice.gate.server.utils.DbLog;
import com.yonyou.microservice.gate.server.utils.RequestLog;

/**
 * @author joy
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients({"com.yonyou.microservice.auth.client.feign","com.yonyou.microservice.gate.server.feign"})
@EnableZuulProxyStore
@ComponentScan(basePackages = {"com.yonyou.cloud.zuul.db","com.yonyou.microservice.gate.server"})
@EnableScheduling
@EnableAceAuthClient
@EnableOpsGateRateLimit
@RestController
@EnableGroovyFilter
@EnableCaching
@EnableSwagger2Doc
public class GateBootstrap {
	@Autowired
	private RateLimitProperties p;

    
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(){
    	return "ok";
    }
    public static void main(String[] args) throws ParseException, UnsupportedEncodingException {
        DbLog.getInstance().start();
        SpringApplication.run(GateBootstrap.class, args);
		RequestLog.getInstance().start();
		System.out.println("-----------GateBootstrap end");
    }

    @Bean
    @Primary
    IUserPrincipal userPrincipal(){
        return new UserPrincipal();
    }
}
 