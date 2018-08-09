///********************************************************************** 
// * <pre>
// * FILE	          : SpringUrlAround.java				
// *			
// * AUTHOR         : ChenChong
// *                							
// *======================================================================
// * CHANGE HISTORY LOG                                             	
// *----------------------------------------------------------------------
// * MOD. NO.|  DATE    | NAME           | REASON		   | CHANGE REQ.
// *----------------------------------------------------------------------
// *         |2017��10��16�� |ChenChong       | Created       |          
// *       
// * DESCRIPTION: 
// * �ļ�˵��    
// * </pre>                           	
// ***********************************************************************/
//package com.yonyou.microservice.gate.server.service;
//
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.yonyou.microservice.gate.server.vo.ReqData;
//import com.yonyou.microservice.gate.server.vo.TimeInterval;
//
//@Service
//public class UrlStatisticService {
//	private static final Logger logger = LoggerFactory.getLogger(UrlStatisticService.class);
//	private static final Logger loggerCnt = LoggerFactory.getLogger("com.yonyou.f4.mvc.aop.urlCnt");
//	private static final Logger loggerAll = LoggerFactory.getLogger("com.yonyou.f4.mvc.aop.urlAll");
//    private static Timer timer = null;
//    private Map<String, ReqData> dayMap = new HashMap<String, ReqData>();
//	private Map<String, ReqData> xmap = new HashMap<String, ReqData>();
//	@Value("${url.statistic.interval}")
//    private long OUT_INTERVAL ;
//	@Value("${url.statistic.historySize}")
//	private int historySize;
//	private List<TimeInterval> dataList=new ArrayList();
////	private List<ReqData> dataList=new ArrayList();
//    
//    @PostConstruct
//    public void init() {
//		logger.info("{} start to initialize", this.getClass().getName());
////		this.timer = new Timer();
////        this.timer.scheduleAtFixedRate(new ReqTimerTask(),OUT_INTERVAL, OUT_INTERVAL);
//		logger.info("{} has been initialized", this.getClass().getName());
//    }
//    
//    @PreDestroy
//    public void destory() {
//		if(this.timer != null) {
//			this.timer.cancel();
//			this.timer = null;
//			logger.info("{} has been destoryed", this.getClass().getName());
//		}
//    }
//	
//	public Object around(ProceedingJoinPoint pjp) throws Throwable {
//		String uri = null;
//		
//		Class clazz = pjp.getTarget().getClass();
//		if(clazz.isAnnotationPresent(RequestMapping.class)) {
//			String[] classMappings = ((RequestMapping)clazz.getAnnotation(RequestMapping.class)).value();
//			if(classMappings != null && classMappings.length > 0) {
//				uri = classMappings[0];
//			}
//		}
//		
//		Method method = ((MethodSignature)pjp.getSignature()).getMethod();
//		if(method.isAnnotationPresent(RequestMapping.class)) {
//			String[] methodMappings = ((RequestMapping)method.getAnnotation(RequestMapping.class)).value();
//			if(methodMappings != null && methodMappings.length > 0) {
//				if(uri != null) {
//					uri = uri + methodMappings[0];
//				} else {
//					uri = methodMappings[0];
//				}
//			}
//		}
//		
//		long begin = System.currentTimeMillis();
//
//		Object object = pjp.proceed();
//		
//		if(uri != null) {
//	        ReqData dt = xmap.get(uri);
//	        if(dt == null) {
//	            synchronized(xmap) {
//	                if(!xmap.containsKey(uri)) {
//	                    dt = new ReqData();
//	                    dt.setUri(uri);
//	                    xmap.put(uri, dt);
//	                } else {
//	                	dt = xmap.get(uri);
//	                }
//	            }
//	        }
//
//	        long t = System.currentTimeMillis() - begin;
//	        dt.getCount().getAndIncrement();
//	        dt.getUseTime().getAndAdd(t);
//	        if(t > dt.getMax()) dt.setMax(t);
//	        if(t < dt.getMin()) dt.setMin(t);
//	        
//	        loggerAll.info("URI: {}, COST: {}", uri, t);
//		}
//		
//		return object;
//	}
//
//	
//	public ReqRps aroundStart(String uri) {
//		
//		long begin = System.currentTimeMillis();
//
//		
//		if(uri != null) {
//			ReqRps dt=new ReqRps();
//			dt.uri=uri;
//			dt.startTime=begin;
//	        return dt;
//		}
//		return null;
//	}
//	public void aroundStop(Object rdO) {
//		loggerAll.info("--aroundStop");
//			ReqRps dt = (ReqRps)rdO;
//			dt.stopTime=System.currentTimeMillis();
//			long t = dt.stopTime - dt.startTime;
//			dt.time=t;
//			
//	        ReqData rd = xmap.get(dt.uri);
//	        if(rd == null) {
//	            synchronized(xmap) {
//	                if(!xmap.containsKey(dt.uri)) {
//	                	rd = new ReqData();
//	                	rd.setUri(dt.uri);
//	                    xmap.put(dt.uri, rd);
//	                } else {
//	                	rd = xmap.get(dt.uri);
//	                }
//	            }
//	        }
//	        rd.setStartTime(dt.startTime);
//	        rd.setEndTime(dt.stopTime);
//	        rd.getCount().getAndIncrement();
//	        rd.getUseTime().getAndAdd(dt.time);
//	        if(t > rd.getMax()) rd.setMax(t);
//	        if(t < rd.getMin()) rd.setMin(t);		
//	        loggerAll.info("URI: {}, COST: {}", dt.uri, t);
//		
//	}
//
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
//    
//}
