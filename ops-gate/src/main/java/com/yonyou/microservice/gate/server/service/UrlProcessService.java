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
//	private static final Logger loggerCnt = LoggerFactory.getLogger("com.yonyou.f4.mvc.aop.urlCnt");
	private static final Logger loggerAll = LoggerFactory.getLogger("com.yonyou.f4.mvc.aop.urlAll");

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    private SimpleDateFormat formatterm = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
    private SimpleDateFormat formatterh = new SimpleDateFormat("yyyy-MM-dd HH"); 
    private SimpleDateFormat formatterd = new SimpleDateFormat("yyyy-MM-dd"); 
//    private static Timer timer = null;
//    private Map<String, ReqData> dayMap = new HashMap<String, ReqData>();
//	private Map<String, ReqData> xmap = new HashMap<String, ReqData>();
//	@Value("${url.statistic.interval}")
//    private long OUT_INTERVAL ;
//	@Value("${url.statistic.historySize}")
//	private int historySize;
//	private List<TimeInterval> dataList=new ArrayList();
    
    @PostConstruct
    public void init() {
//		logger.info("{} start to initialize", this.getClass().getName());
//		this.timer = new Timer();
//        this.timer.scheduleAtFixedRate(new ReqTimerTask(),OUT_INTERVAL, OUT_INTERVAL);
//		logger.info("{} has been initialized", this.getClass().getName());
    }
    
    @PreDestroy
    public void destory() {
//		if(this.timer != null) {
//			this.timer.cancel();
//			this.timer = null;
//			logger.info("{} has been destoryed", this.getClass().getName());
//		}
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
//		
//		UrlRequestVO dt1 = new UrlRequestVO();
//		dt1.setTime(dt.getTime());
//		dt1.setStartTime(dt.getStartTime());
//		dt1.setStartTimeLabel(dt.getStartTimeLabel());
//		dt1.setUri(dt.getUri());
//		dt1.setUsedTime(dt.getUsedTime());
//		dt1.setTimeUri(dt.getTimeUri());
//		dt1.setTimem(dt.getTimem());
//		dt1.setTimeh(dt.getTimeh());
//		dt1.setTimed(dt.getTimed());
//		RequestLog.getInstance().offerQueue(dt1);
//
//		UrlRequestVO dt2 = new UrlRequestVO();
//		dt2.setTime(dt.getTime());
//		dt2.setStartTime(dt.getStartTime());
//		dt2.setStartTimeLabel(dt.getStartTimeLabel());
//		dt2.setUri(dt.getUri()+"abc");
//		dt2.setUsedTime(dt.getUsedTime());
//		dt2.setTimeUri(dt.getTimeUri()+"abc");
//		dt2.setTimem(dt.getTimem());
//		dt2.setTimeh(dt.getTimeh());
//		dt2.setTimed(dt.getTimed());
//		RequestLog.getInstance().offerQueue(dt2);
	}

//
//    class ReqRps {
//    	long startTime=0;
//    	long stopTime=0;
//        String uri;
//        long time=0;
//    }
//
//    class ReqTimerTask extends TimerTask {
//		Calendar lastDate = Calendar.getInstance();
//    	
//		public void run() {
//			loggerAll.info("--ReqTimerTask.run");
//            Map<String,ReqData> tmp = null;
//            synchronized (xmap){
//                tmp = xmap;
//                xmap = new HashMap<String, ReqData>();
//            }
//            TimeInterval timeInterval=new TimeInterval();
//            long total=0;
//
//            timeInterval.setStopTime(System.currentTimeMillis());
//            timeInterval.setStartTime(timeInterval.getStopTime()-OUT_INTERVAL);
//            StringBuilder sbd = new StringBuilder();
//            sbd.append("\r\n#Report : "+OUT_INTERVAL/1000+"s \r\n");
//            sbd.append(String.format(ReqData.msgTitle, "ACTION", "COUNT", "MIN(ms)", "AVG(ms)", "MAX(ms)"));
//            for(ReqData dt : tmp.values()){
//            	dt.setEndTime(dt.getStartTime()+OUT_INTERVAL);
//                sbd.append(dt.toString());
//                timeInterval.getDataList().add(dt.clone());
//                total=total+dt.getCount().get();
//            }
//            timeInterval.setCount(total);
//            dataList.add(timeInterval);
//            if(dataList.size()>historySize){
//            	dataList.remove(0);
//            }
//            sbd.append("#---------------------------------------------------------------------");
//            loggerCnt.info(sbd.toString());
//
//            ReqData rd = null;
//            for(ReqData dt : tmp.values()){
//                rd = dayMap.get(dt.getUri());
//                if( rd==null ){
//                    rd = dt;
//                    dayMap.put(rd.getUri(), rd);
//                    logger.info("--dayMap add dt,size="+dayMap.size());
//                }else{
//                	logger.info("--dayMap count old="+rd.getCount()+",add "+dt.getCount().get());
//                	
//                    rd.getCount().getAndAdd(dt.getCount().get());
//                    rd.getUseTime().getAndAdd(dt.getUseTime().get());
//                    if(dt.getMin()<rd.getMin()) rd.setMin(dt.getMin());
//                    if(dt.getMax()>rd.getMax()) rd.setMax(dt.getMax());
//                }
//            }
//
//            sbd.setLength(0);
//            sbd.append("\r\n#Report : DAY \r\n");
//            sbd.append(String.format(ReqData.msgTitle, "ACTION", "COUNT", "MIN(ms)", "AVG(ms)", "MAX(ms)"));
//            for(ReqData dt : dayMap.values()){
//                sbd.append(dt.toString());
//            }
//            sbd.append("#---------------------------------------------------------------------");
//            loggerCnt.info(sbd.toString());
//
//            Calendar now = Calendar.getInstance();
//            if(now.get(Calendar.DAY_OF_MONTH) != lastDate.get(Calendar.DAY_OF_MONTH)) {
//                dayMap = new HashMap<String, ReqData>();
//                logger.info("--new dayMap");
//            }
//            lastDate = now;
//		}
//    }
//
//	public List<TimeInterval> getDataList() {
//		return dataList;
//	}
//
//	public void setDataList(List<TimeInterval> dataList) {
//		this.dataList = dataList;
//	}

    public static void main(String[] args) throws ParseException, UnsupportedEncodingException {
    	DateTime id=new DateTime(System.currentTimeMillis());
    	System.out.println(id.toString());
    }
}
