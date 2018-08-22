package com.yonyou.cloud.ops.alert.ops.alert.handler;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yonyou.cloud.ops.alert.ops.alert.biz.AlertInfoBiz;
/**
 * 
 * @author daniell
 *
 */
@Configuration
@EnableScheduling
@Component
public class QueueHandler {
	private static final Logger loger = LoggerFactory.getLogger(QueueHandler.class);
	
	@Autowired
	AlertInfoHandler alertInfoHandler;
	
	@Scheduled(cron = "${redis.task.schedule}")
	public void queueHandler() {
		try {
			alertInfoHandler.redisFile();
		} catch (IOException e) {
			loger.info("redis 获取数据异常"+e.getMessage());
			e.printStackTrace();
		}
	}

}
