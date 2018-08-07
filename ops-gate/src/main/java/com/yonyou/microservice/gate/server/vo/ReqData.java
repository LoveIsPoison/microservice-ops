package com.yonyou.microservice.gate.server.vo;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.boot.SpringApplication;

import com.yonyou.microservice.gate.server.GateBootstrap;
import com.yonyou.microservice.gate.server.utils.DbLog;

public class ReqData {
    public static String msgfmt = "%2$12d %3$12d %4$12d %5$12d    %1$s  %6$s   %7$s\r\n";
    public static String msgTitle = "#%2$11s %3$12s %4$12s %5$12s    %1$s\r\n";
	long startTime=0;
	long endTime=0;
    String uri;
    AtomicLong count =new AtomicLong(0);
    AtomicLong useTime = new AtomicLong(0);
    long min=Long.MAX_VALUE;
    long max=Long.MIN_VALUE;
    
    @Override
    public String toString() {
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    	if(count.get()==0){
            return String.format(msgfmt, uri, count.get(), min, 0, max,formatter.format(new Date(startTime)),formatter.format(new Date(endTime)));
    	}
        return String.format(msgfmt, uri, count.get(), min, (long)(useTime.get()/count.get()), max,formatter.format(new Date(startTime)),formatter.format(new Date(endTime)));
    }

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public AtomicLong getCount() {
		return count;
	}

	public void setCount(AtomicLong count) {
		this.count = count;
	}

	public AtomicLong getUseTime() {
		return useTime;
	}

	public void setUseTime(AtomicLong useTime) {
		this.useTime = useTime;
	}

	public long getMin() {
		return min;
	}

	public void setMin(long min) {
		this.min = min;
	}

	public long getMax() {
		return max;
	}

	public void setMax(long max) {
		this.max = max;
	}
	
	public ReqData clone(){
		ReqData r=new ReqData();
		r.count.getAndSet(this.getCount().get());
//		r.setCount(this.getCount());
		r.setEndTime(this.endTime);
		r.setMax(this.max);
		r.setMin(min);
		r.setStartTime(startTime);
		r.setUri(uri);
		r.setUseTime(useTime);
		return r;
	}

//    public static void main(String[] args) {
//    	ReqData d=new ReqData();
//    	System.out.print(d.toString());
//    }
}
