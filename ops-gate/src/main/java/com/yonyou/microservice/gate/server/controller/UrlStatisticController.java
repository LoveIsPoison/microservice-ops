package com.yonyou.microservice.gate.server.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.microservice.gate.server.service.UrlStatisticService;

@RestController
public class UrlStatisticController {
	private static Logger logger=Logger.getLogger(UrlStatisticController.class);
	
	@Autowired
	private UrlStatisticService urlStatisticService;
	
	@ResponseBody
    @RequestMapping(value = "/url/statistic", method = RequestMethod.GET)
    public List urlStatistic() {
    	logger.info("--urlStatistic");
        return urlStatisticService.getDataList();
    }

}
