package com.yonyou.cloud.ops.alert.ops.alert.rest;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.cloud.common.annotation.YcApi;
import com.yonyou.cloud.ops.alert.ops.alert.handler.AlertInfoHandler;
import com.yonyou.cloud.ops.alert.ops.alert.handler.QueueHandler;

@RestController
@RequestMapping("/queuehandler")
public class QueueHandlerRest {
	private static final Logger loger = LoggerFactory.getLogger(QueueHandler.class);
	
	@Autowired
	AlertInfoHandler alertInfoHandler;
	
	@RequestMapping(value = "/redis", method = RequestMethod.GET)
	@ResponseBody
	@YcApi
	public void queueHandler() {
		try {
			alertInfoHandler.redisFile();
		} catch (IOException e) {
			loger.info("redis 获取数据异常"+e.getMessage());
			e.printStackTrace();
		}
	}
}
