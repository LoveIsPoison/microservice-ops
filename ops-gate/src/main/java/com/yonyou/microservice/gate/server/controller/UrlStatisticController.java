package com.yonyou.microservice.gate.server.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlStatisticController {
	private static Logger logger=Logger.getLogger(UrlStatisticController.class);
	
	@ResponseBody
    @RequestMapping(value = "/url/statistic", method = RequestMethod.GET)
    public void urlStatistic() {
    	logger.info("--urlStatistic");
    }

}
