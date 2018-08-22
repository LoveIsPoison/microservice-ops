package com.yonyou.cloud.ops.alert.ops.alert.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yonyou.cloud.ops.alert.ops.alert.entity.RuleInfo;

import tk.mybatis.mapper.common.Mapper;

public interface RuleInfoMapper extends Mapper<RuleInfo> {

	public List<RuleInfo> selectRuleInfoByAppOrIp(@Param("appName") String appName, @Param("hostIp") String hostIp);

	List<RuleInfo> selectRuleInfoAll(@Param("ruleName") String ruleName, @Param("groupName") String groupName,
			@Param("keyword") String keyword);
}