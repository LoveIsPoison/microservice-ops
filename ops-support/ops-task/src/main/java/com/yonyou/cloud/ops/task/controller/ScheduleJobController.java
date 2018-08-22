package com.yonyou.cloud.ops.task.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.quartz.CronExpression;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dexcoder.commons.pager.Pager;
import com.yonyou.cloud.ops.task.entity.ScheduleEntity;
import com.yonyou.cloud.ops.task.service.ScheduleJobService;
/**
 * 
 * @author daniell
 *
 */
@RestController
@RequestMapping("/taskManager")
public class ScheduleJobController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
    private ScheduleJobService scheduleJobService; 
    
    @RequestMapping(value="/apilogs",method=RequestMethod.GET)
    public Map<String, Object> getApiLogs(Integer page,Integer pageSize,String qry) throws Exception{
        Pager logpage = scheduleJobService.getAllLogs(page==null?1:page,pageSize==null?10:pageSize,qry);
        Map<String, Object> map = new HashMap<String, Object>(3);
        map.put("page", logpage);
        map.put("pages", logpage.getPages());
        return map;
    }
    
    @RequestMapping(value="/tasklogs",method=RequestMethod.GET)
    public Map<String, Object> tasklogs(Integer page,Integer pageSize,String taskName,Long startTime,Long endTime,String responseInfo) throws Exception{
    	Map<String, Object> querymap = new HashMap<String, Object>(4);
    	querymap.put("taskName", taskName);
    	querymap.put("startTime", startTime);
    	querymap.put("endTime", endTime);
    	querymap.put("responseInfo",responseInfo);
    	
        Pager logpage = scheduleJobService.getAllTaskLogs(page==null?1:page,pageSize==null?10:pageSize,querymap);
        Map<String, Object> map = new HashMap<String, Object>(3);
        map.put("page", logpage);
        map.put("pages", logpage.getPages());
        return map;
    }

     
    
    @RequestMapping(value="/list",method=RequestMethod.GET)
    public Map<String, Object> getAllJobs(String jobName){
        List<ScheduleEntity> scheduleEntities = scheduleJobService.getAllScheduleJob(jobName);
        Map<String, Object> map = new HashMap<String, Object>(2);
        map.put("page", scheduleEntities);
    	logger.info("----------------task,list.json,"+scheduleEntities.size());
        return map;
    }
 

    @RequestMapping(value="/save",method=RequestMethod.POST) 
    public Object create(@RequestBody ScheduleEntity scheduleEntity) {
        Map<String, Object> map = new HashMap<String, Object>(10);
        map.put("status", -1);
        // 判断表达式
        boolean f = CronExpression.isValidExpression(scheduleEntity
                .getCronExpression());
        if (!f) {
            map.put("msg", "cron表达式有误，不能被解析！");
            return map;
        }
        try {
        	logger.info("scheduleEn====="+scheduleEntity.toString());
            scheduleEntity.setStatus("1");
            scheduleJobService.add(scheduleEntity);
            map.put("status", 0);
            map.put("msg","保存成功!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            map.put("msg", "找不到指定的类");
        } catch (SchedulerException e) {
            e.printStackTrace();
            if (e.getMessage().contains(
                    "because one already exists with this identification")) {
                map.put("msg", "任务组中存在同样的任务名");
            } else {
                map.put("msg", "未知原因,添加任务失败");
            }
        } catch (Exception e) {
            map.put("msg", "系统错误!");
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 暂停任务
     */

    @RequestMapping(value="/stopJob" ,method=RequestMethod.GET)
    public Object stop(@RequestParam String jobName, @RequestParam String jobGroup) {
        Map<String, Object> map = new HashMap<String, Object>(5);
        map.put("status", -1);
        try {
            scheduleJobService.stopJob(jobName, jobGroup);
            map.put("status", 0);
            map.put("msg","暂停成功!");
        } catch (SchedulerException e) {
            e.printStackTrace();
            map.put("msg", "系统错误,请联系管理员!");
        }
        return map;
    }

    @RequestMapping(value="/delete" ,method=RequestMethod.DELETE)
    public Object delete(String jobName,String jobGroup) {
        Map<String, Object> map = new HashMap<String, Object>(5);
        map.put("status", -1);
        try {
            scheduleJobService.delJob(jobName, jobGroup);
            map.put("status", 0);
            map.put("msg","删除成功!");
        } catch (SchedulerException e) {
            e.printStackTrace();
            map.put("msg", "系统错误,请联系管理员!");
        }
        return map;
    }

    /**
     * 修改表达式
     */

    @RequestMapping(value="/stopJob" ,method=RequestMethod.PUT)
    public Object update(ScheduleEntity scheduleEntity) {
        Map<String, Object> map = new HashMap<String, Object>(5);
        map.put("status", -1);
        // 验证cron表达式 
        boolean f = CronExpression.isValidExpression(scheduleEntity
                .getCronExpression());
        if (!f) {
            map.put("msg", "cron表达式有误，不能被解析！");
            return map;
        }
        try {
            scheduleJobService.modifyTrigger(scheduleEntity.getJobName(),
                    scheduleEntity.getJobGroup(), scheduleEntity.getCronExpression());
            map.put("status", 0);
        } catch (SchedulerException e) {
            e.printStackTrace();
            map.put("msg", "系统错误,请联系管理员!");
        }
        return map;
    }

    /**
     * 立即运行一次
     */

    @RequestMapping(value="/stopJob" ,method=RequestMethod.POST)
    public Object stratNow(String jobName, String jobGroup) {
        Map<String, Object> map = new HashMap<String, Object>(5);
        map.put("status", -1);
        try {
            scheduleJobService.startNowJob(jobName, jobGroup);
            map.put("status", 0);
        } catch (SchedulerException e) {
            e.printStackTrace();
            map.put("msg", "系统错误,请联系管理员!");
        }
        return map;
    }

    /**
     * 恢复
     */
    @RequestMapping(value="/resume" ,method=RequestMethod.GET)
    public Object resume(@RequestParam String jobName, @RequestParam String jobGroup) {
        Map<String, Object> map = new HashMap<String, Object>(5);
        map.put("status", -1);
        try {
            scheduleJobService.restartJob(jobName, jobGroup);
            map.put("status", 0);
            map.put("msg","恢复成功!");
        } catch (SchedulerException e) {
            e.printStackTrace();
            map.put("msg", "系统错误,请联系管理员!");
        }
        return map;
    }
    
    
    @RequestMapping(value="/call" ,method=RequestMethod.PUT)
    public Object call()  {
        Map<String, Object> map = new HashMap<String, Object>(2);
        map.put("result", "OK");
        return map;
    }

    /**
     * 获取所有trigger
     */
    public void getTriggers(HttpServletRequest request) {
        List<ScheduleEntity> scheduleEntities = scheduleJobService.getTriggersInfo();
        request.setAttribute("triggers", scheduleEntities);
    }
    
    
    
//    @ResponseBody
//    @RequestMapping("apilog/{logId}")
//    public String getLogDetail(@PathVariable(value="logId")String logId) throws Exception{
//        return scheduleJobService.getApiLogtextById(logId);
//    }

}
