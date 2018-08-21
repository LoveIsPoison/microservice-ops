package com.yonyou.microservice.gate.server.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IgnoreUriController {
	private Logger logger = LoggerFactory.getLogger(IgnoreUriController.class);

	@ResponseBody
	@RequestMapping(value = "/ignoreuris/refresh", method = RequestMethod.GET)
	@CacheEvict(value = "gate", allEntries = true)
	public Map cleanIgnoreUriCache() {
		logger.info("--IgnoreUriController cleanIgnoreUriCache was called");
		Map<String, String> map = new HashMap();
		map.put("message", "ok");
		return map;
	}
}
