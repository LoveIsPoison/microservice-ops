package com.yonyou.microservice.gate.admin.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.cloud.common.controller.BaseController;
import com.yonyou.microservice.gate.admin.biz.RouteVersionBiz;
import com.yonyou.microservice.gate.admin.entity.RouteVersion;
/**
 * @author joy
 */
@RestController
@RequestMapping("routeVersions")
public class RouteVersionController extends BaseController<RouteVersionBiz,RouteVersion> {

}
