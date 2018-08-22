package com.yonyou.cloud.ops.alert.ops.alert.biz;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yonyou.cloud.common.beans.PageResultResponse;
import com.yonyou.cloud.common.service.BaseService;
import com.yonyou.cloud.ops.alert.ops.alert.domain.dto.GroupUsers;
import com.yonyou.cloud.ops.alert.ops.alert.domain.dto.RuleGroupBo;
import com.yonyou.cloud.ops.alert.ops.alert.domain.dto.RuleGroupForm;
import com.yonyou.cloud.ops.alert.ops.alert.domain.dto.RuleGroupSearchForm;
import com.yonyou.cloud.ops.alert.ops.alert.domain.dto.RuleInfoForm;
import com.yonyou.cloud.ops.alert.ops.alert.entity.RuleGroup;
import com.yonyou.cloud.ops.alert.ops.alert.entity.RuleInfo;
import com.yonyou.cloud.ops.alert.ops.alert.entity.RuleScope;
import com.yonyou.cloud.ops.alert.ops.alert.entity.UserGroupAlert;
import com.yonyou.cloud.ops.alert.ops.alert.mapper.RuleGroupMapper;

/**
 * 2018年3月8日18:20:12
 * 
 * @author daniell
 *
 */
@Service
public class RuleGroupBiz extends BaseService<RuleGroupMapper, RuleGroup> {

	@Autowired
	RuleScopeBiz ruleScopebiz;
	@Autowired
	RuleInfoBiz ruleInfobiz;

	@Autowired
	UserGroupAlertBiz userGroupAlertBiz;

	/**
	 * 报警信息列表
	 * 
	 * @param searchForm
	 * @return
	 */
	public PageResultResponse<RuleGroupBo> selectBySearchForm(RuleGroupSearchForm searchForm) {
		Page<Object> result = PageHelper.startPage(searchForm.getPage(), searchForm.getLimit());
		List<RuleGroupBo> list = mapper.selectBySearchForm(searchForm);
		for (RuleGroupBo bo : list) {
			RuleScope rulescope = new RuleScope();
			rulescope.setGroupId(bo.getRuleGroupId());
			List<RuleScope> apps = ruleScopebiz.selectList(rulescope);
			if (apps.size() > 0) {
				bo.setRuleScopes(apps);
			}
		}
		return new PageResultResponse<RuleGroupBo>(result.getTotal(), list);
	}

	/**
	 * 报警信息详情
	 * 
	 * @param id
	 * @return
	 */
	public RuleGroupBo selectByGroupId(int id) {
		RuleGroupSearchForm searchForm = new RuleGroupSearchForm();
		searchForm.setRuleGroupId(id);
		List<RuleGroupBo> list = mapper.selectBySearchForm(searchForm);
		for (RuleGroupBo bo : list) {
			RuleScope rulescope = new RuleScope();
			rulescope.setGroupId(bo.getRuleGroupId());
			List<RuleScope> apps = ruleScopebiz.selectList(rulescope);
			if (apps.size() > 0) {
				bo.setRuleScopes(apps);
			}
			List<GroupUsers> guslist = userGroupAlertBiz.getList(bo.getRuleGroupId()).getData();
			if (guslist.size() > 0) {
				bo.setUgalist(guslist);
			}
			return bo;
		}
		return null;
	}

	public List<RuleGroup> getlist() {
		List<RuleGroup> list = mapper.selectRuleInfoAll();
		return list;
	}

	/**
	 * 创建报警规则组合
	 * 
	 * @param ruleGroup
	 */
	@Transactional(rollbackFor = RuntimeException.class)
	public void add(RuleGroupForm ruleGroupForm) {
		// 保存规则组
		RuleGroup rulegroup = new RuleGroup();
		BeanUtils.copyProperties(ruleGroupForm, rulegroup);

		int groupid;
		if (ruleGroupForm.getId() == null) {
			rulegroup.setStatus(true);
			mapper.insertRuleGroup(rulegroup);
			groupid = rulegroup.getId();
		} else {
			groupid = ruleGroupForm.getId();
			mapper.updateByPrimaryKeySelective(rulegroup);

			// 删除原有多余的app
			RuleScope ruleScopeSearch = new RuleScope();
			ruleScopeSearch.setGroupId(groupid);
			ruleScopebiz.delete(ruleScopeSearch);

			// 删除原有多余报警用户组
			RuleInfo ruleInfoSearch = new RuleInfo();
			ruleInfoSearch.setGroupId(groupid);
			ruleInfobiz.delete(ruleInfoSearch);

			// 删除原有多余报警用户组
			UserGroupAlert userGroupAlertSearch = new UserGroupAlert();
			userGroupAlertSearch.setRuleGroupId(groupid);
			userGroupAlertBiz.delete(userGroupAlertSearch);
		}

		// 保存关联资源组
		for (String ipApp : ruleGroupForm.getIpAppList()) {
			RuleScope ruleScope = new RuleScope();
			ruleScope.setIpApp(ipApp);
			ruleScope.setGroupId(groupid);
			ruleScope.setType("App");
			ruleScopebiz.insert(ruleScope);
		}

		// 保存规则
		for (RuleInfoForm ruleInfoForm : ruleGroupForm.getAlarmRules()) {
			RuleInfo rule = new RuleInfo();
			rule.setKeyword(ruleInfoForm.getRuleKeyword());
			rule.setTime(ruleInfoForm.getRuleTime());
			rule.setCount(ruleInfoForm.getRuleCount());
			rule.setGroupId(groupid);
			rule.setName(ruleInfoForm.getRuleName());
			ruleInfobiz.insert(rule);
		}

		// 保存报警用户组

		for (Integer ugid : ruleGroupForm.getUserGroupIds()) {
			UserGroupAlert userGroupAlert = new UserGroupAlert();
			userGroupAlert.setRuleGroupId(groupid);
			userGroupAlert.setUserGroupId(ugid);
			userGroupAlertBiz.insert(userGroupAlert);
		}

	}
}
