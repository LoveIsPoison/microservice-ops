package com.yonyou.cloud.ops.alert.ops.alert.api;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.cloud.common.annotation.YcApi;
import com.yonyou.cloud.common.beans.RestResultResponse;
import com.yonyou.cloud.common.controller.BaseController;
import com.yonyou.cloud.ops.alert.ops.alert.biz.AlertInfoBiz;
import com.yonyou.cloud.ops.alert.ops.alert.domain.dto.AlertInfoForm;
import com.yonyou.cloud.ops.alert.ops.alert.entity.AlertInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author daniell
 *
 */
@RestController
@RequestMapping("alertInfoApi")
@Api(tags="http类型报警信息")
public class AlertInfoApi extends BaseController<AlertInfoBiz, AlertInfo>{
	
	
	
	@RequestMapping(value="/httpAlertInfoSave",method = RequestMethod.POST)
	@ResponseBody
	@YcApi
	@ApiOperation(value="保存http报警信息",notes="各个属性必填") 
	public  RestResultResponse<AlertInfo> httpAlertInfoSave(@RequestBody AlertInfoForm form) {
		return baseService.httpAlertInfoSave(form);
	}

}
