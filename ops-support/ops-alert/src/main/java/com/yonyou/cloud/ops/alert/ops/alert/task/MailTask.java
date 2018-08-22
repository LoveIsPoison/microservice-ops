package com.yonyou.cloud.ops.alert.ops.alert.task;

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
public class MailTask {

	@Autowired
	AlertInfoBiz alertInfoBiz;

	@Scheduled(cron = "${redis.task.schedule}")
	public void sendMail() {
//		alertInfoBiz.sendMail();
	}

}
