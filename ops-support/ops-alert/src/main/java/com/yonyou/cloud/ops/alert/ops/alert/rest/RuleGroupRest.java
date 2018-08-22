package com.yonyou.cloud.ops.alert.ops.alert.rest;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.cloud.common.annotation.YcApi;
import com.yonyou.cloud.common.beans.PageResultResponse;
import com.yonyou.cloud.common.controller.BaseController;
import com.yonyou.cloud.ops.alert.ops.alert.biz.RuleGroupBiz;
import com.yonyou.cloud.ops.alert.ops.alert.domain.dto.RuleGroupBo;
import com.yonyou.cloud.ops.alert.ops.alert.domain.dto.RuleGroupForm;
import com.yonyou.cloud.ops.alert.ops.alert.domain.dto.RuleGroupSearchForm;
import com.yonyou.cloud.ops.alert.ops.alert.entity.RuleGroup;

@RestController
@RequestMapping("/ruleGroup")
public class RuleGroupRest extends BaseController<RuleGroupBiz, RuleGroup> {

	@RequestMapping(value = "/pagelist", method = RequestMethod.GET)
	@ResponseBody
	public PageResultResponse<RuleGroupBo> pagelist(RuleGroupSearchForm searchForm) {
		return baseService.selectBySearchForm(searchForm);
	}

	@RequestMapping(value = "/getlist", method = RequestMethod.GET)
	@ResponseBody
	public List<RuleGroup> list() {
		return baseService.getlist();
	}

	
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	@ResponseBody
	public  RuleGroupBo  groupdetail(@PathVariable int id) {
		return baseService. selectByGroupId(id);
	}
	/**
	 * 创建报警规则组合
	 * 
	 * @param ruleGroup
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@YcApi
	public void add(@RequestBody(required=false) RuleGroupForm ruleGroup) {
		baseService.add(ruleGroup);
	}
}
