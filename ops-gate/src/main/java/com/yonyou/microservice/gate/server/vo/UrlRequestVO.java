package com.yonyou.microservice.gate.server.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.xiaoleilu.hutool.date.DateTime;

//@Document(indexName = "urlstatistic", type = "urlrequest")
public class UrlRequestVO implements Serializable {
	private String time;
	private String timem;
	private String timeh;
	private String timed;
    private static final long serialVersionUID = -1L;
	private long startTime=0;
	private long stopTime=0;
	private long usedTime=0;
    private String startTimeLabel;
    private String stopTimeLabel;
    private String uri;
    private String key=UUID.randomUUID().toString();
    private String timeUri;
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
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTimeUri() {
		return timeUri;
	}
	public void setTimeUri(String timeUri) {
		this.timeUri = timeUri;
	}
	public String getTimem() {
		return timem;
	}
	public void setTimem(String timem) {
		this.timem = timem;
	}
	public String getTimeh() {
		return timeh;
	}
	public void setTimeh(String timeh) {
		this.timeh = timeh;
	}
	public String getTimed() {
		return timed;
	}
	public void setTimed(String timed) {
		this.timed = timed;
	}
	
}
