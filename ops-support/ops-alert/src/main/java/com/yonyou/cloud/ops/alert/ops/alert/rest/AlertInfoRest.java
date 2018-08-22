package com.yonyou.cloud.ops.alert.ops.alert.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.yonyou.cloud.common.annotation.YcApi;
import com.yonyou.cloud.common.beans.PageResultResponse;
import com.yonyou.cloud.common.controller.BaseController;
import com.yonyou.cloud.ops.alert.ops.alert.biz.AlertInfoBiz;
import com.yonyou.cloud.ops.alert.ops.alert.domain.dto.AlertInfoBo;
import com.yonyou.cloud.ops.alert.ops.alert.domain.dto.AlertInfoSearchForm;
import com.yonyou.cloud.ops.alert.ops.alert.entity.AlertInfo;

@RestController
@RequestMapping("/alertInfo")
public class AlertInfoRest extends BaseController<AlertInfoBiz, AlertInfo>{
	
	
	@RequestMapping(value = "/pagelist", method = RequestMethod.GET)
	@ResponseBody
	@YcApi
	public PageResultResponse<AlertInfoBo> list(AlertInfoSearchForm searchForm) {
	      //查询列表数据
       return baseService.selectByQuery(searchForm);
	}
}
