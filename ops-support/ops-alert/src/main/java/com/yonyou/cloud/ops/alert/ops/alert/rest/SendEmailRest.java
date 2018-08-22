package com.yonyou.cloud.ops.alert.ops.alert.rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.cloud.common.annotation.YcApi;
import com.yonyou.cloud.ops.alert.ops.alert.biz.AlertInfoBiz;

@RestController
@RequestMapping("/email")
public class SendEmailRest {

	@Autowired
	AlertInfoBiz alertInfoBiz;
	
	@RequestMapping(value = "/send", method = RequestMethod.GET)
	@ResponseBody
	@YcApi
	public void sendMail() {
			alertInfoBiz.sendMail(); 
	}
}
