package com.yonyou.cloud.ops.notify.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.cloud.common.service.BaseService;
import com.yonyou.cloud.ops.notify.entity.NotifyAppUrl;
import com.yonyou.cloud.ops.notify.mapper.NotifyAppUrlMapper;

import tk.mybatis.mapper.common.Mapper;

@Service
public class NotifyAppUrlService  extends BaseService<Mapper<NotifyAppUrl>, NotifyAppUrl>{

	private Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired
	private NotifyAppUrlMapper notifyAppUrlMapper;
	
	public NotifyAppUrl getAppUrl(String url,String appId){
		NotifyAppUrl na=new NotifyAppUrl();
		List<NotifyAppUrl> list=notifyAppUrlMapper.getAppUrl(appId);
		if(list.size()==1){
			if(url==null || "".equals(url)){
				url=list.get(0).getUrl();
			}
			na.setContentType(list.get(0).getContentType());
			na.setUrl(url);
		}else{
			if(url!=null){
				na.setUrl(url);
				na.setContentType("application/json");
				return na;
			}else	if(list.size()==0){
				logger.error("error:app has no callbak url,appid="+appId);
			}else{
				logger.error("error:app callbak url size>1,size="+list.size()+",appId="+appId);
			}
			return null;
		}
		return na;
	}
}
