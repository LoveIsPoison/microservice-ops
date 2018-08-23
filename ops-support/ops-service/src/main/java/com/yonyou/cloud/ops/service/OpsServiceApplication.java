package com.yonyou.cloud.ops.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.spring4all.swagger.EnableSwagger2Doc;

@SpringBootApplication
@EnableSwagger2Doc
@EnableDiscoveryClient
@EnableFeignClients
public class OpsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpsServiceApplication.class, args);
	}
}
