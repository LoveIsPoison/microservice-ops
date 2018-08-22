package com.yonyou.cloud.ops.alert.ops.alert.mapper;

import java.util.List;

import com.yonyou.cloud.ops.alert.ops.alert.entity.UserInfo;
import tk.mybatis.mapper.common.Mapper;

public interface UserInfoMapper extends Mapper<UserInfo> {
	public List<UserInfo> selectUserByRuleGroup(Integer ruleGroupId);
}