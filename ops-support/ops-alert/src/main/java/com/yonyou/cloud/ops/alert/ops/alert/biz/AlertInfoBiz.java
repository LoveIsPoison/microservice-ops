package com.yonyou.cloud.ops.alert.ops.alert.biz;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xiaoleilu.hutool.date.DateUtil;
import com.yonyou.cloud.common.beans.PageResultResponse;
import com.yonyou.cloud.common.beans.RestResultResponse;
import com.yonyou.cloud.common.service.BaseService;
import com.yonyou.cloud.ops.alert.ops.alert.alarm.AlarmMessageContext;
import com.yonyou.cloud.ops.alert.ops.alert.alarm.mails.EmailMessage;
import com.yonyou.cloud.ops.alert.ops.alert.domain.constants.AlertStatus;
import com.yonyou.cloud.ops.alert.ops.alert.domain.dto.AlertInfoBo;
import com.yonyou.cloud.ops.alert.ops.alert.domain.dto.AlertInfoForm;
import com.yonyou.cloud.ops.alert.ops.alert.domain.dto.AlertInfoSearchForm;
import com.yonyou.cloud.ops.alert.ops.alert.domain.dto.GroupUsers;
import com.yonyou.cloud.ops.alert.ops.alert.domain.dto.RuleGroupForm;
import com.yonyou.cloud.ops.alert.ops.alert.domain.dto.UserBo;
import com.yonyou.cloud.ops.alert.ops.alert.domain.dto.mail.MessageTemplate;
import com.yonyou.cloud.ops.alert.ops.alert.entity.AlertInfo;
import com.yonyou.cloud.ops.alert.ops.alert.entity.RuleGroup;
import com.yonyou.cloud.ops.alert.ops.alert.entity.UserGroupAlert;
import com.yonyou.cloud.ops.alert.ops.alert.feign.IUserService;
import com.yonyou.cloud.ops.alert.ops.alert.mapper.AlertInfoMapper;
import com.yonyou.cloud.ops.alert.ops.alert.mapper.RuleGroupMapper;
import com.yonyou.cloud.ops.alert.ops.alert.utils.DateTimeUtils;

/**
 * 
 * @author daniell
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class AlertInfoBiz extends BaseService<AlertInfoMapper, AlertInfo> {
	private static final Logger loger = LoggerFactory.getLogger(AlertInfoBiz.class);
	@Autowired
	EmailMessage emailMessage;
	@Autowired
	UserInfoBiz userInfoBiz;

	@Autowired
	IUserService iUserService;

	@Autowired
	UserGroupAlertBiz userGroupAlertBiz;

	@Autowired
	RuleGroupMapper ruleGroupMapper;

	public Integer creatGroupByHttp(RuleGroupForm ruleGroupForm) {
		// 保存规则组
		RuleGroup rulegroup = new RuleGroup();
		BeanUtils.copyProperties(ruleGroupForm, rulegroup);
		List<RuleGroup> list = ruleGroupMapper.select(rulegroup);
		if (list.size() == 0) {
			rulegroup.setStatus(true);
			ruleGroupMapper.insertRuleGroup(rulegroup);
			return rulegroup.getId();
		} else {
			// 删除原有多余报警用户组
			UserGroupAlert userGroupAlertSearch = new UserGroupAlert();
			userGroupAlertSearch.setRuleGroupId(list.get(0).getId());
			userGroupAlertBiz.delete(userGroupAlertSearch);
		}

		// 保存报警用户组
		for (Integer ugid : ruleGroupForm.getUserGroupIds()) {
			UserGroupAlert userGroupAlert = new UserGroupAlert();
			userGroupAlert.setRuleGroupId(list.get(0).getId());
			userGroupAlert.setUserGroupId(ugid);
			userGroupAlertBiz.insert(userGroupAlert);
		}
		return list.get(0).getId();
	}

	public RestResultResponse<AlertInfo> httpAlertInfoSave(AlertInfoForm form) {

		RuleGroupForm ruleGroupForm = new RuleGroupForm();
		ruleGroupForm.setName(form.getRuleGroupName());
		if(StringUtils.isNotBlank(form.getMailContent())) {
			ruleGroupForm.setMailContent(form.getMailContent());
		}
		if(StringUtils.isNotBlank(form.getMailContent())) {
			ruleGroupForm.setMailTitle(form.getMailContent());
		}
		if(StringUtils.isNotBlank(form.getAlarmType())) {
			ruleGroupForm.setAlarmType(form.getAlarmType());
		}
		 
		ruleGroupForm.setUserGroupIds(form.getUserGroupIds()); 
		

		Integer groupId = creatGroupByHttp(ruleGroupForm);

		AlertInfo info = new AlertInfo();
		BeanUtils.copyProperties(form, info);
		info.setGroupId(groupId);
		info.setStatus(AlertStatus.Trigger.getValue());
		mapper.insert(info);
		return new RestResultResponse<AlertInfo>().success(true);

	}

	public PageResultResponse<AlertInfoBo> selectByQuery(AlertInfoSearchForm searchForm) {
		Page<Object> result = PageHelper.startPage(searchForm.getPage(), searchForm.getLimit());
		List<AlertInfoBo> list = mapper.selectAlertBO(searchForm);
		return new PageResultResponse<AlertInfoBo>(result.getTotal(), list);
	}

	public void sendMail() {
		AlertInfoSearchForm searchForm = new AlertInfoSearchForm();
		searchForm.setStatus(AlertStatus.Trigger.getValue());
		List<AlertInfoBo> alertbo = mapper.selectAlertBO(searchForm);

		loger.info("当前正在通知的邮件有："+alertbo.size()+"封");
		List<MessageTemplate> msgTemp = new ArrayList<MessageTemplate>();
		AlarmMessageContext context = new AlarmMessageContext(emailMessage);
		for (AlertInfoBo alertBo : alertbo) {
			alertBo.getAlertDetail();
			alertBo.getGroupName();
			MessageTemplate msg = new MessageTemplate();
			msg.setSubject(alertBo.getMailTitle());

			StringBuffer sbu = new StringBuffer();
			sbu.append(alertBo.getMailContent());
			sbu.append("\n服务名称：");
			sbu.append(alertBo.getAppName());
			sbu.append("\n触发报警规则组：");
			sbu.append(alertBo.getGroupName());
			sbu.append("\n规则描述：");
			sbu.append(alertBo.getAlertDesc());
			sbu.append("\n报错详情信息如下:\n");
			sbu.append(alertBo.getAlertDetail());

			msg.setContent(sbu.toString());
			loger.info(sbu.toString());
			List<GroupUsers> groupUserslists = userGroupAlertBiz.getList(alertBo.getGroupId()).getData();
			Set<String> emailhs = new HashSet<String>();
			for (GroupUsers gus : groupUserslists) {
				int i = 0;
				for (UserBo user : gus.getMembers()) {
					if (StringUtils.isNotBlank(user.getEmail())) {
						emailhs.add(user.getEmail());
						i++;
					}
				}
				String[] arr = new String[emailhs.size()];
				String[] aa = emailhs.toArray(arr);
				msg.setToAddress(aa);
				if (emailhs.size() != 0) {
					msgTemp.add(msg);
				}

			}
			context.AlarmMessage(msgTemp);

			// 更正状态为已通知
			alertBo.setStatus(AlertStatus.Notice.getValue());

			AlertInfo ainfo = new AlertInfo();
			BeanUtils.copyProperties(alertBo, ainfo);
			if(alertBo.getCreateDate()!=null) {
				ainfo.setCreateDate(alertBo.getCreateDate());
			}
			mapper.updateByPrimaryKey(ainfo);
		}
	}

}
