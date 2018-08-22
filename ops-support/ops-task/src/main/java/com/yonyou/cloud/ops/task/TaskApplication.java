package com.yonyou.cloud.ops.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient; 
import com.spring4all.swagger.EnableSwagger2Doc;
/**
 * 
 * @author daniell
 *
 */
@SpringBootApplication
@EnableSwagger2Doc
@EnableDiscoveryClient
public class TaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskApplication.class, args);
	}
}
