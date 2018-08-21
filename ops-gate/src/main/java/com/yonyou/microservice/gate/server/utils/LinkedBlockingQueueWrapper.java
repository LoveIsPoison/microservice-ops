package com.yonyou.microservice.gate.server.utils;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWrapper<E> extends LinkedBlockingQueue<E>{

	public LinkedBlockingQueueWrapper(int size){
		super(size);
	}
	public E takeX() throws InterruptedException {
		
		return super.take();
	}
	

    public boolean offerX(E e)  throws InterruptedException {
    	while(this.remainingCapacity()<=0){
    		this.take();
    	}
    	return super.offer(e);
    }
    
    public static void main(String[] args) throws InterruptedException{
    	LinkedBlockingQueueWrapper<String> l=new LinkedBlockingQueueWrapper(5);
    	l.offerX("1");
    	l.offerX("2");
    	l.offerX("3");
    	l.offerX("4");
    	l.offerX("5");
    	l.offerX("6");
    	l.offerX("7");
    	Iterator<String> ior=l.iterator();
    	while(ior.hasNext()){
    		String s=ior.next();
    		System.out.println(s);
    	}
    }
    	   
}
