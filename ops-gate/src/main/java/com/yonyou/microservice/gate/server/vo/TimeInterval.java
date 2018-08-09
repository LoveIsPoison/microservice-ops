//package com.yonyou.microservice.gate.server.vo;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//public class TimeInterval {
//    private long startTime=0;
//    private long stopTime=0;
//    private String startTimeLabel;
//    private String stopTimeLabel;
//    private long count=0;
//	private List<ReqData> dataList=new ArrayList();
//	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
//	public long getStartTime() {
//		return startTime;
//	}
//	public void setStartTime(long startTime) {
//		this.startTimeLabel=formatter.format(new Date(startTime));
//		this.startTime = startTime;
//	}
//	public long getStopTime() {
//		return stopTime;
//	}
//	public void setStopTime(long stopTime) {
//		this.stopTimeLabel=formatter.format(new Date(stopTime));
//		this.stopTime = stopTime;
//	}
//	public List<ReqData> getDataList() {
//		return dataList;
//	}
//	public void setDataList(List<ReqData> dataList) {
//		this.dataList = dataList;
//	}
//	public long getCount() {
//		return count;
//	}
//	public void setCount(long count) {
//		this.count = count;
//	}
//	public String getStartTimeLabel() {
//		return startTimeLabel;
//	}
//	public void setStartTimeLabel(String startTimeLabel) {
//		this.startTimeLabel = startTimeLabel;
//	}
//	public String getStopTimeLabel() {
//		return stopTimeLabel;
//	}
//	public void setStopTimeLabel(String stopTimeLabel) {
//		this.stopTimeLabel = stopTimeLabel;
//	}
//	
//}
