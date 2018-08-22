package com.yonyou.cloud.ops.alert.ops.alert.mapper;

import java.util.List;

import com.yonyou.cloud.ops.alert.ops.alert.domain.dto.RuleGroupBo;
import com.yonyou.cloud.ops.alert.ops.alert.domain.dto.RuleGroupSearchForm;
import com.yonyou.cloud.ops.alert.ops.alert.entity.RuleGroup;

import tk.mybatis.mapper.common.Mapper;

public interface RuleGroupMapper extends Mapper<RuleGroup> {
	List<RuleGroup> selectRuleInfoAll();

	public int insertRuleGroup(RuleGroup ruleGroup);

	List<RuleGroupBo> selectBySearchForm(RuleGroupSearchForm searchForm);
}