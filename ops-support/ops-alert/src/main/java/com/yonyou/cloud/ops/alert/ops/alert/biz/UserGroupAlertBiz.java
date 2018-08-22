package com.yonyou.cloud.ops.alert.ops.alert.biz;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.cloud.common.beans.RestResultResponse;
import com.yonyou.cloud.common.service.BaseService;
import com.yonyou.cloud.ops.alert.ops.alert.domain.dto.GroupUsers;
import com.yonyou.cloud.ops.alert.ops.alert.entity.RuleScope;
import com.yonyou.cloud.ops.alert.ops.alert.entity.UserGroupAlert;
import com.yonyou.cloud.ops.alert.ops.alert.feign.IUserService;
import com.yonyou.cloud.ops.alert.ops.alert.mapper.UserGroupAlertMapper;
@Service
public class UserGroupAlertBiz extends BaseService<UserGroupAlertMapper, UserGroupAlert>{
	private static final Logger loger = LoggerFactory.getLogger(UserGroupAlertBiz.class);
	@Autowired
	private IUserService iUserService;
	
	/**
	 * 獲取用戶組下用戶
	 * @param ruleGroupId
	 * @return
	 */
	public RestResultResponse<List<GroupUsers>> getList(int ruleGroupId) {
		UserGroupAlert usergroupalert=new UserGroupAlert();
		usergroupalert.setRuleGroupId(ruleGroupId);
		List<UserGroupAlert> alertlist=mapper.select(usergroupalert);
		List<GroupUsers> bolsit=new ArrayList<>();
		
		for(UserGroupAlert  groupalert:alertlist) {
			RestResultResponse<GroupUsers> list=iUserService.getUsers(groupalert.getUserGroupId());
			bolsit.add(list.getData());
		}
		loger.info("调用admin 获取需要报警的用户组个数为"+bolsit.size());
		return new RestResultResponse<RuleScope>().success(true).data(bolsit);
	}
}
