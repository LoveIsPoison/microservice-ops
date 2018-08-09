package com.yonyou.microservice.gate.server.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

//@Document(indexName = "urlstatistic", type = "urlrequest")
public class UrlRequestVO implements Serializable {
	private String id;
    private static final long serialVersionUID = -1L;
	private long startTime=0;
	private long stopTime=0;
	private long usedTime=0;
    private String startTimeLabel;
    private String stopTimeLabel;
    private String uri;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTimeLabel=formatter.format(new Date(startTime));
		this.startTime = startTime;
	}
	public long getStopTime() {
		return stopTime;
	}
	public void setStopTime(long stopTime) {
		this.stopTimeLabel=formatter.format(new Date(stopTime));
		this.stopTime = stopTime;
	}
	public long getUsedTime() {
		return usedTime;
	}
	public void setUsedTime(long usedTime) {
		this.usedTime = usedTime;
	}
	public String getStartTimeLabel() {
		return startTimeLabel;
	}
	public void setStartTimeLabel(String startTimeLabel) {
		this.startTimeLabel = startTimeLabel;
	}
	public String getStopTimeLabel() {
		return stopTimeLabel;
	}
	public void setStopTimeLabel(String stopTimeLabel) {
		this.stopTimeLabel = stopTimeLabel;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
    
}
