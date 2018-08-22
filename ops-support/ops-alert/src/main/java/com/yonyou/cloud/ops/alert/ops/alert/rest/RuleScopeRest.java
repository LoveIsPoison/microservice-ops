package com.yonyou.cloud.ops.alert.ops.alert.rest;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.yonyou.cloud.common.annotation.YcApi;
import com.yonyou.cloud.common.beans.RestResultResponse;
import com.yonyou.cloud.common.controller.BaseController;
import com.yonyou.cloud.ops.alert.ops.alert.biz.RuleScopeBiz;
import com.yonyou.cloud.ops.alert.ops.alert.entity.RuleScope;

@RestController
@RequestMapping("/ruleScope")
public class RuleScopeRest extends BaseController<RuleScopeBiz, RuleScope> {
 
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getlist", method = RequestMethod.GET)
	@ResponseBody
	@YcApi
	public RestResultResponse<List<RuleScope>> getlist(@RequestParam(required=false) Map<String, Object> params) {
		List<RuleScope> list = baseService.selectByQuery(params);
		return new RestResultResponse<RuleScope>().success(true).data(list);
	}
}
