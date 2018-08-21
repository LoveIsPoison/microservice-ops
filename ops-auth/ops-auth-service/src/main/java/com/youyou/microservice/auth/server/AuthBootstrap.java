package com.youyou.microservice.auth.server;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 *  @author joy
 */
@SpringBootApplication
@EnableFeignClients({"com.youyou.microservice.auth.server.feign"})
@EnableDiscoveryClient
@MapperScan("com.youyou.microservice.auth.server.mapper")
@ComponentScan({"com.yonyou.cloud.mom.client.config","com.youyou.microservice.auth.server"})
public class AuthBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(AuthBootstrap.class, args);
    }
}
