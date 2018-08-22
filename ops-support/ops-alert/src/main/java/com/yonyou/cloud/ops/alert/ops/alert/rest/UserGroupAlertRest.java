package com.yonyou.cloud.ops.alert.ops.alert.rest;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.cloud.common.beans.RestResultResponse;
import com.yonyou.cloud.common.controller.BaseController;
import com.yonyou.cloud.ops.alert.ops.alert.biz.UserGroupAlertBiz;
import com.yonyou.cloud.ops.alert.ops.alert.domain.dto.GroupUsers;
import com.yonyou.cloud.ops.alert.ops.alert.domain.dto.UserGroupAlerBo;
import com.yonyou.cloud.ops.alert.ops.alert.entity.UserGroupAlert;

@RestController
@RequestMapping("/userGroupAlert")
public class UserGroupAlertRest extends BaseController<UserGroupAlertBiz, UserGroupAlert> {
 
	/**
	 * 根据规则组获取规则组下用户组信息和用户组下用户列表
	 * @param ruleGroupId
	 * @return
	 */
	@RequestMapping(value = "/getGroupUserlist", method = RequestMethod.GET)
	@ResponseBody
	public  RestResultResponse<List<GroupUsers>> list(int ruleGroupId) {
		return baseService.getList(ruleGroupId);
	}
}
