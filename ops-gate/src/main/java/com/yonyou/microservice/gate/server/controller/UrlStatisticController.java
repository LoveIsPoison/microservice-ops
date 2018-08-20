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
    @RequestMapping(value = "/url/statistic", method = RequestMethod.GET)
    public List<UrlRequestStatisticVO> urlStatistic(@RequestParam String startTime,
    		@RequestParam String stopTime,@RequestParam String type,
    		@RequestParam(defaultValue="null") String sortField,
    		@RequestParam(defaultValue="asc") String order,
    		@RequestParam(defaultValue="20") int size) {
		List<UrlRequestStatisticVO> result=null;
		if("day".equals(type)){
			result= elasticsearchUtil.groupDay(startTime,stopTime,size);
		}else if("hour".equals(type)){
			result= elasticsearchUtil.groupHour(startTime,stopTime,size);
		}else if("minute".equals(type)){
			result= elasticsearchUtil.groupMinu(startTime,stopTime,size);
		}else if("second".equals(type)){
			result= elasticsearchUtil.groupSec(startTime,stopTime,size);
		}
//		if(!"null".equals(sortField)){
//			boolean desc=false;
//			if(!"asc".equals(order)){
//				logger.info("--sort desc");
//				desc=true;
//			}else {
//				logger.info("--sort asc");
//			}
//			if("time".equals(sortField)){
//				elasticsearchUtil.compareDate(result,desc);
//				logger.info("--order by time");
//			}else{
//				result=elasticsearchUtil.sort(result, sortField,desc);
//				logger.info("--order by "+sortField);
//			}
//		}
		this.sort(result, sortField, order);
		
		return result;
    }
	private List<UrlRequestStatisticVO> sort(List<UrlRequestStatisticVO> result,String sortField,String order){
		if(!"null".equals(sortField)){
			boolean desc=false;
			if(!"asc".equals(order)){
				logger.info("--sort desc");
				desc=true;
			}else {
				logger.info("--sort asc");
			}
			if("time".equals(sortField)){
				elasticsearchUtil.compareDate(result,desc);
				logger.info("--order by time");
			}else{
				result=elasticsearchUtil.sort(result, sortField,desc);
				logger.info("--order by "+sortField);
			}
		}
		
		return result;
	}

	@ResponseBody
    @RequestMapping(value = "/url/traffic", method = RequestMethod.GET)
    public List<UrlRequestStatisticVO> urlTraffic(@RequestParam String startTime,
    		@RequestParam String stopTime,@RequestParam String type,
    		@RequestParam(defaultValue="null") String sortField,
    		@RequestParam(defaultValue="asc") String order,
    		@RequestParam(defaultValue="20") int size) {
		List<UrlRequestStatisticVO> result=null;
		if("day".equals(type)){
			result= elasticsearchUtil.trafficDay(startTime,stopTime,size);
		}else if("hour".equals(type)){
			result= elasticsearchUtil.trafficHour(startTime,stopTime,size);
		}else if("minute".equals(type)){
			result= elasticsearchUtil.trafficMinu(startTime,stopTime,size);
		}else if("second".equals(type)){
			result= elasticsearchUtil.trafficSec(startTime,stopTime,size);
		}
//		if(!"null".equals(sortField)){
//			boolean desc=false;
//			if(!"asc".equals(order)){
//				desc=true;
//			}
//			result=elasticsearchUtil.sort(result, sortField,desc);
//		}
		this.sort(result, sortField, order);
		return result;
    }
}
