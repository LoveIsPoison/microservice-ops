package com.yonyou.cloud.ops.alert.ops.alert.rest;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.cloud.common.annotation.YcApi;
import com.yonyou.cloud.common.beans.PageResultResponse;
import com.yonyou.cloud.common.beans.RestResultResponse;
import com.yonyou.cloud.common.controller.BaseController;
import com.yonyou.cloud.ops.alert.ops.alert.biz.RuleInfoBiz;
import com.yonyou.cloud.ops.alert.ops.alert.domain.dto.RuleInfoDto;
import com.yonyou.cloud.ops.alert.ops.alert.entity.RuleInfo;

@RestController
@RequestMapping("/ruleInfo")
public class RuleInfoRest extends BaseController<RuleInfoBiz, RuleInfo> {

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getlist", method = RequestMethod.GET)
	@ResponseBody
	@YcApi
	public RestResultResponse<List<RuleInfo>> all(String ruleName, String groupName, String keyword) {
		List<RuleInfo> list = baseService.getlist(ruleName, groupName, keyword);
		return new RestResultResponse<RuleInfo>().success(true).data(list);
	}

	@RequestMapping(value = "/pagelist" , method = RequestMethod.POST)
	@ResponseBody
	@YcApi
	public PageResultResponse<RuleInfo> pagelist(@RequestBody RuleInfoDto dto) {
		return baseService.selectByQueryPagelist(dto);
	}
	
	
	

}
