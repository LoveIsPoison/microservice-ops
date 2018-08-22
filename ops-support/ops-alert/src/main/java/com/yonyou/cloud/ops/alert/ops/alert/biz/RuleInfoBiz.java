package com.yonyou.cloud.ops.alert.ops.alert.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yonyou.cloud.common.beans.PageResultResponse;
import com.yonyou.cloud.common.service.BaseService;
import com.yonyou.cloud.ops.alert.ops.alert.domain.dto.RuleInfoDto;
import com.yonyou.cloud.ops.alert.ops.alert.entity.RuleInfo;
import com.yonyou.cloud.ops.alert.ops.alert.mapper.RuleInfoMapper;


/**
 * 
 * @author daniell
 *
 */
@Service
public class RuleInfoBiz extends BaseService<RuleInfoMapper, RuleInfo> {
	@Autowired
	RuleGroupBiz ruleGroupbiz;
	@Autowired
	RuleScopeBiz ruleScopebiz;
	
	/**
	 * 根据appName 和hostIp查询报警规则
	 * @param appName
	 * @param hostIp
	 * @return
	 */
	public List<RuleInfo> selectRuleInfoByAppOrIp(String appName, String hostIp) {
		return mapper.selectRuleInfoByAppOrIp(appName, hostIp);
	}

	public List<RuleInfo> getlist(String ruleName, String groupName, String keyword) {
		List<RuleInfo> list = mapper.selectRuleInfoAll(ruleName, groupName, keyword);
		return list;
	}

	public PageResultResponse<RuleInfo> selectByQueryPagelist(RuleInfoDto dto) { 
	        
		List<RuleInfo> lists = mapper.selectRuleInfoAll(dto.getName(), dto.getGroupName(),dto.getKeyword());
//		List<RuleInfoDto> listdto =new ArrayList<RuleInfoDto>();
//		for(RuleInfo info :lists) {
//			RuleInfoDto resultdto =new RuleInfoDto();
//			BeanUtils.copyProperties(info, resultdto);
//			listdto.add(resultdto);
//		}
		Page<RuleInfo> result = PageHelper.startPage(dto.getPage(), dto.getLimit());
		return new PageResultResponse<RuleInfo>(result.getTotal(), lists);
	}
	

	
	
}
