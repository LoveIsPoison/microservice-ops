package com.yonyou.cloud.ops.alert.ops.alert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.spring4all.swagger.EnableSwagger2Doc;
/**
 * 
 * @author daniell
 *
 */
@SpringBootApplication
@EnableSwagger2Doc
@EnableDiscoveryClient
@EnableFeignClients
@EnableEurekaClient
public class AlarmApplication {
	public static void main(String[] args) {
		SpringApplication.run(AlarmApplication.class, args);
	}
}
