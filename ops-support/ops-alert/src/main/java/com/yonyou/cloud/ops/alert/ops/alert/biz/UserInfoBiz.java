package com.yonyou.cloud.ops.alert.ops.alert.biz;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yonyou.cloud.common.service.BaseService;
import com.yonyou.cloud.ops.alert.ops.alert.entity.UserInfo;
import com.yonyou.cloud.ops.alert.ops.alert.mapper.UserInfoMapper;
@Service
public class UserInfoBiz extends BaseService<UserInfoMapper, UserInfo> {
	public List<UserInfo> selectUserByRuleGroup(Integer ruleGroupId) {
		return mapper.selectUserByRuleGroup(ruleGroupId);
	}
}
