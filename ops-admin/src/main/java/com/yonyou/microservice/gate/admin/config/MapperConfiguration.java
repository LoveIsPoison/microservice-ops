package com.yonyou.microservice.gate.admin.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author daniell
 *
 */
@Configuration
@MapperScan("com.yonyou.microservice.gate.admin.mapper")
public class MapperConfiguration{
	 
}