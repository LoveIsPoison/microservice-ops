package com.yonyou.microservice.gate.admin.rpc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.microservice.gate.admin.biz.RouteVersionBiz;
import com.yonyou.microservice.gate.admin.entity.IgnoreUri;
import com.yonyou.microservice.gate.admin.entity.RouteVersion;
import com.yonyou.microservice.gate.common.vo.gate.RouteVersionInfo;

/**
 * @author joy
 */
@Service
public class RouteVersionService {
    @Autowired
    private RouteVersionBiz routeVersionBiz;
    
    public  List<RouteVersionInfo> getRouteVersions() {
    	List<RouteVersion> list=routeVersionBiz.selectListAll();
    	List<RouteVersionInfo> r=new ArrayList();
    	for(RouteVersion p:list){
    		RouteVersionInfo i=new RouteVersionInfo();
    		i.setId(p.getId());
    		i.setVersion(p.getVersion());
    		i.setServiceId(p.getServiceId());
    		r.add(i);
    	}
        return r;
    }
}
