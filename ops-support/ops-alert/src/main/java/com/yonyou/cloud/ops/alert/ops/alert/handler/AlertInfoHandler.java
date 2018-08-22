package com.yonyou.cloud.ops.alert.ops.alert.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.xiaoleilu.hutool.json.JSONObject;
import com.xiaoleilu.hutool.json.JSONUtil;
import com.yonyou.cloud.ops.alert.ops.alert.biz.AlertInfoBiz;
import com.yonyou.cloud.ops.alert.ops.alert.biz.RuleInfoBiz;
import com.yonyou.cloud.ops.alert.ops.alert.domain.constants.AlertStatus;
import com.yonyou.cloud.ops.alert.ops.alert.domain.dto.MsgInfoForm;
import com.yonyou.cloud.ops.alert.ops.alert.entity.AlertInfo;
import com.yonyou.cloud.ops.alert.ops.alert.entity.RuleInfo;
import com.yonyou.cloud.ops.alert.ops.alert.utils.DateTimeUtils;
import com.yonyou.cloud.ops.alert.ops.alert.utils.FilterUtil;

/**
 * 
 * @author daniell
 *
 */
@Component
public class AlertInfoHandler {

	private static final Logger loger = LoggerFactory.getLogger(AlertInfoHandler.class);

	@Value("${redis.listener.key}")
	private String listenerKey;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private RedisTemplate<String, Long> redisTemplateLong;

	@Autowired
	private RuleInfoBiz ruleInfoBiz;

	@Autowired
	private AlertInfoBiz alertInfoBiz;

	public void redisFile() throws IOException {

		// String msgInfos = redisTemplate.opsForList().leftPop(listenerKey);
		List<String> msgInfolists = redisTemplate.opsForList().range(listenerKey, 0, 100);
		
		loger.info("当前从redis队列中获取了："+msgInfolists.size()+"条数据");
		redisTemplate.opsForList().trim(listenerKey,1, 100);
		for (String msgInfos : msgInfolists) {
			if (StringUtils.isNotBlank(msgInfos)) {
				JSONObject jsonObject = JSONUtil.parseObj(msgInfos);
				MsgInfoForm vo = JSONUtil.toBean(jsonObject, MsgInfoForm.class);

				//查询包含改APPname的报警规则
				List<RuleInfo> vv = ruleInfoBiz.selectRuleInfoByAppOrIp(vo.getType(), vo.getHost());
				String msginfo = vo.getMsginfo();
				for (RuleInfo rule : vv) {
					loger.info("log内容"+msginfo);
					loger.info("报警关键字"+rule.getKeyword());
					if (FilterUtil.findTextIgnoreCase(msginfo, rule.getKeyword())) {
						loger.info("触发报警关键字"+rule.getKeyword());

						List<MsgInfoForm> list = new ArrayList<>();
						list.add(vo);

						// 规则组+关键字作为队列的key/
						String queueKey = rule.getGroupId() + rule.getKeyword();
						Long keylength = redisTemplateLong.opsForList().leftPush(queueKey,
								DateTimeUtils.parseDateTime(vo.getLogDate()));
						//获取队列所有内容
						List<Long> keylist = redisTemplateLong.opsForList().range(queueKey, 0, keylength);
						Long lastvalue = keylist.get(0);
						if (keylength >= rule.getCount()) {
							Long startValue = keylist.get(Integer.valueOf(rule.getCount() - 1));
							if (lastvalue - startValue <= rule.getTime()*1000*60L) {
//								redisTemplateLong.opsForList().trim(queueKey, 0, keylength - 1);
								redisTemplateLong.opsForList().trim(queueKey, 100,  -1);
								loger.info("触发报警规则，满足报警条件");
								AlertInfo alertInfo = new AlertInfo();
								alertInfo.setGroupId(rule.getGroupId());
								alertInfo.setAlertDetail(msginfo);
								alertInfo.setStatus(AlertStatus.Trigger.getValue());
								alertInfo.setAppName(vo.getType());
								alertInfo.setCreateDate(new Date());
								alertInfo.setAlertDesc(rule.getKeyword()+":"+rule.getTime()+"分钟出现"+rule.getCount()+"次 ;log地址:"+vo.getPath());
								alertInfoBiz.insertSelective(alertInfo);
							}
						}
					}
				}
			}
		}

	}
}
