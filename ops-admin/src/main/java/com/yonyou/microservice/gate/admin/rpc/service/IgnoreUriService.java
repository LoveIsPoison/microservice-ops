package com.yonyou.microservice.gate.admin.rpc.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.microservice.gate.admin.biz.IgnoreUriBiz;
import com.yonyou.microservice.gate.admin.biz.RouteVersionBiz;
import com.yonyou.microservice.gate.admin.entity.IgnoreUri;
import com.yonyou.microservice.gate.admin.entity.RouteVersion;
import com.yonyou.microservice.gate.common.vo.authority.IgnoreUriInfo;

/**
 * @author joy
 */
@Service
public class IgnoreUriService {
	private static Logger logger=Logger.getLogger(IgnoreUriService.class);
    @Autowired
    private IgnoreUriBiz ignoreUriBiz;
    @Autowired
    private RouteVersionBiz routeVersionBiz;
    
    public  List<IgnoreUriInfo> getIgnoreUris() {
    	List<IgnoreUri> list=ignoreUriBiz.selectListAll();
    	List<RouteVersion> versions=routeVersionBiz.selectListAll();
    	List<IgnoreUriInfo> r=new ArrayList();
    	for(IgnoreUri p:list){
    		IgnoreUriInfo i=new IgnoreUriInfo();
    		i.setId(p.getId());
    		i.setUri(p.getUri());
    		i.setServiceId(p.getServiceId());
    		r.add(i);
    		if(p.getServiceId()!=null){
    			versions.forEach(item->{
    				if(item.getServiceId().equals(p.getServiceId())){
    					IgnoreUriInfo j=new IgnoreUriInfo();
    		    		j.setId(item.getId());
    		    		if(p.getUri().charAt(0)=='/'){
        		    		j.setUri("/"+item.getVersion()+p.getUri());
    		    		}else{
        		    		j.setUri("/"+item.getVersion()+"/"+p.getUri());
    		    		}
    		    		j.setServiceId(p.getServiceId());
    		    		logger.info("--dynVersion serviceId="+j.getServiceId()+",uri="+j.getUri());
    		    		r.add(j);
    				}
    			});
    		}
    	}
        return r;
    }
}
