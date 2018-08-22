package com.yonyou.cloud.ops.task.job;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexcoder.dal.JdbcDao;
import com.xiaoleilu.hutool.json.JSONObject;
import com.xiaoleilu.hutool.json.JSONUtil;
import com.yonyou.cloud.ops.task.common.CommonConstants;
import com.yonyou.cloud.ops.task.utils.HttpUtil;
import com.yonyou.cloud.ops.task.utils.SpringUtil;


/**
 * 执行调度
 * 
 * @author BENJAMIN
 *
 */
public class ScheduledJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledJob.class);
    

    public ScheduledJob() {
    }
    
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    	long statrTime=System.currentTimeMillis();
    	JdbcDao jdbcDao=(JdbcDao) SpringUtil.getBean("jdbcDao");
    	 String out="";
    	 JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
    	 try {  
    		 logger.info("开始执行:"+dataMap.getString("url"));
    		 out=HttpUtil.sendGet(dataMap.getString("url"),"");
    		 logger.info("out:"+out);
		} catch (Exception e) {
			logger.error("计划调用失败:",e);
			e.printStackTrace();
		}
        long endTime=System.currentTimeMillis();
        String workTime=String.valueOf(endTime-statrTime);
        int returnFlag=0;  
        String localAddr="";
        try {
        	if(!StringUtils.isBlank(out)){
        		try {
        			JSONObject ob=JSONUtil.parseObj(out);
        			String resultCode=ob.getStr("result");
        			if("OK".equals(resultCode)) {
                		returnFlag=1;
                	}
				} catch (Exception e) {
					 logger.info("返回的不是一个对象"); 
				}
            }
        	localAddr=InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
        
		jdbcDao.updateForSql("INSERT INTO TS_SCHEDLED_LOG \n"
				+ "(JOB_GROUP ,\n"
				+ "SCHEDLED_NAME ,\n"
				+ "RETURN_FLAG ,\n"
				+ "URL_NAME,\n" 
				+ "CREATE_DATE,\n"
				+ "CREATE_BY ,\n"
				+ "RESPONSE_INFO,\n"
				+ "PC_IP,\n"
				+ "WORK_TIME \n"
				+ ") values (?,?,?,?,?,?,?,?,?)", 
				new Object[]{
						dataMap.getString("jobGroup"),
						dataMap.getString("jobName"),
						returnFlag,
						dataMap.get("url"),
					new Date(),
					CommonConstants.SERVICE_USER,
					out,
					localAddr,
					workTime
				});
		
    }
}
