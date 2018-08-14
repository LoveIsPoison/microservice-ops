package com.yonyou.microservice.gate.server.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.microservice.gate.server.utils.ElasticsearchUtil;
import com.yonyou.microservice.gate.server.vo.UrlRequestStatisticVO;

@RestController
public class UrlStatisticController {
	private static Logger logger=Logger.getLogger(UrlStatisticController.class);
	@Autowired
	private ElasticsearchUtil elasticsearchUtil;
	
	@ResponseBody
    @RequestMapping(value = "/url/statistic/second", method = RequestMethod.GET)
    public List<UrlRequestStatisticVO> urlStatisticSecond(@RequestParam String startTime,
    		@RequestParam String stopTime) {
		return elasticsearchUtil.groupSec(startTime,stopTime);
    }

	@ResponseBody
    @RequestMapping(value = "/url/statistic/minute", method = RequestMethod.GET)
    public List<UrlRequestStatisticVO> urlStatisticMinute(@RequestParam String startTime,
    		@RequestParam String stopTime) {
		return elasticsearchUtil.groupMinu(startTime,stopTime);
    }

	@ResponseBody
    @RequestMapping(value = "/url/statistic/hour", method = RequestMethod.GET)
    public List<UrlRequestStatisticVO> urlStatisticHour(@RequestParam String startTime,
    		@RequestParam String stopTime) {
		return elasticsearchUtil.groupHour(startTime,stopTime);
    }

	@ResponseBody
    @RequestMapping(value = "/url/statistic/day", method = RequestMethod.GET)
    public List<UrlRequestStatisticVO> urlStatisticDay(@RequestParam String startTime,
    		@RequestParam String stopTime) {
		return elasticsearchUtil.groupDay(startTime,stopTime);
    }
}
