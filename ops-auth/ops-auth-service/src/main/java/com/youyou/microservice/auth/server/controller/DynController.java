package com.youyou.microservice.auth.server.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.xiaoleilu.hutool.json.JSONObject;
import com.yonyou.cloud.common.beans.RestResultResponse;
import com.youyou.microservice.auth.server.dynauth.DynAuthService;
import com.youyou.microservice.auth.server.entity.AuthProvider;
import com.youyou.microservice.auth.server.util.user.JwtAuthenticationDataResponse;



/** 
 * 第三方认证动态Controller
 * 
 * @author joy
 * @author BENJAMIN
 */
public class DynController implements Controller {
	private static Logger logger=LoggerFactory.getLogger(DynController.class);
	public static final String ACCEPT_USER = "user";
	public static final String ACCEPT_USER_PASSWORD = "userAndPassword";
	private List<AuthProvider> providers;

	private AuthProvider getService(String name) {
		for (AuthProvider i : providers) {
			if (name.contains(i.getSrcUrl())) {
				return i;
			}
		}
		return null;
	}
	
	@Autowired
	@Qualifier("dynAuthServiceHttpImpl")
	private DynAuthService dynAuthService;
	
	/**
	 * 处理动态配置的自定认证来的请求
	 * @throws Exception 
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest p0, HttpServletResponse p1) throws Exception {
		logger.info("--DynController.handleRequest");
		String uri = p0.getRequestURI();
		logger.info("--DynController,uri=" + uri);
//    	byte[] bytes = getRequestPostBytes(p0);
//    	bytes.toString();
//    	String s=new String(bytes, "UTF-8"); 
		//对请求进行解析
		AuthProvider pInfo = this.getService(uri);
		//判断是否在处理范围中
		if (pInfo != null && !"".equals(pInfo.getAuthService())) {
			logger.info("--DynController,service=" + pInfo.getAuthService());
			try {
				JwtAuthenticationDataResponse jwtAuth =  dynAuthService.auth(p0, pInfo);
				JSONObject json = new JSONObject(new RestResultResponse<JwtAuthenticationDataResponse>().data(jwtAuth).success(true));
				p1.getOutputStream().write(json.toString().getBytes());
			}catch (Exception e) {
				logger.error("动态认证验证失败失败",e);
				
			}
		}
		return null;
	}

	public List<AuthProvider> getProviders() {
		return providers;
	}

	public void setProviders(List<AuthProvider> providers) {
		this.providers = providers;
	}
}
