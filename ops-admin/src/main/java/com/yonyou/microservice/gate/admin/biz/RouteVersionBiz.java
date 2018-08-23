package com.yonyou.microservice.gate.admin.biz;

import org.springframework.stereotype.Service;

import com.yonyou.cloud.common.service.BaseService;
import com.yonyou.microservice.gate.admin.entity.RouteVersion;
import com.yonyou.microservice.gate.admin.mapper.RouteVersionMapper;

/**
 * ${DESCRIPTION}
 *
 * @author joy
 * @create 2017-06-08 16:23
 */
@Service
public class RouteVersionBiz extends BaseService<RouteVersionMapper,RouteVersion> {

    @Override
    public void insertSelective(RouteVersion entity) {
        super.insertSelective(entity);
    }

    @Override
    public void updateSelectiveById(RouteVersion entity) {
        super.updateSelectiveById(entity);
    }



}
