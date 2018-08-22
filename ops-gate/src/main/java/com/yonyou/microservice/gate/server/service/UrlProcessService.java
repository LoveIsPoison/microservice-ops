/********************************************************************** 
 * <pre>
 * FILE	          : SpringUrlAround.java				
 *			
 * AUTHOR         : ChenChong
 *                							
 *======================================================================
 * CHANGE HISTORY LOG                                             	
 *----------------------------------------------------------------------
 * MOD. NO.|  DATE    | NAME           | REASON		   | CHANGE REQ.
 *----------------------------------------------------------------------
 *         |2017��10��16�� |ChenChong       | Created       |          
 *       
 * DESCRIPTION: 
 * �ļ�˵��    
 * </pre>                           	
 ***********************************************************************/
package com.yonyou.microservice.gate.server.service;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xiaoleilu.hutool.date.DateTime;
import com.yonyou.microservice.gate.server.utils.RequestLog;
import com.yonyou.microservice.gate.server.vo.UrlRequestVO;

@Service
public class UrlProcessService {
	private static final Logger logger = LoggerFactory.getLogger(UrlProcessService.class);
	private static final Logger loggerAll = LoggerFactory.getLogger("com.yonyou.f4.mvc.aop.urlAll");

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    private SimpleDateFormat formatterm = new SimpleDateFormat("yyyy-MM-dd HH:mm:00"); 
    private SimpleDateFormat formatterh = new SimpleDateFormat("yyyy-MM-dd HH:00:00"); 
    private SimpleDateFormat formatterd = new SimpleDateFormat("yyyy-MM-dd 00:00:00"); 
    
    @PostConstruct
    public void init() {
    }
    
    @PreDestroy
    public void destory() {
    }

	
	public UrlRequestVO aroundStart(String uri) {

    	UrlRequestVO v=new UrlRequestVO();
    	
		long begin = System.currentTimeMillis();

		if(uri != null) {
			Date id=new Date(begin);
			v.setTime(formatter.format(id));
			v.setTimem(formatterm.format(id));
			v.setTimeh(formatterh.format(id));
			v.setTimed(formatterd.format(id));
			v.setStartTime(begin);
			v.setUri(uri);
			v.setStartTime(begin);
			v.setTimeUri(v.getTime()+v.getUri());
	        return v;
		}
		return null;
	}
	public void aroundStop(Object rdO) {
		loggerAll.info("--aroundStop");
		UrlRequestVO dt = (UrlRequestVO)rdO;
		dt.setStopTime(System.currentTimeMillis());
		long t = dt.getStopTime() - dt.getStartTime();
		dt.setUsedTime(t);
		RequestLog.getInstance().offerQueue(dt);
	}


    public static void main(String[] args) throws ParseException, UnsupportedEncodingException {
    	DateTime id=new DateTime(System.currentTimeMillis());
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00"); 
    	System.out.println(formatter.format(id));
    }
}
