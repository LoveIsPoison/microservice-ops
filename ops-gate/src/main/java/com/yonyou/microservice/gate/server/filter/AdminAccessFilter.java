package com.yonyou.microservice.gate.server.filter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.yonyou.cloud.common.jwt.IJwtInfo;
import com.yonyou.cloud.common.service.utils.ClientUtil;
import com.yonyou.microservice.auth.client.config.ServiceAuthConfig;
import com.yonyou.microservice.auth.client.config.UserAuthConfig;
import com.yonyou.microservice.auth.client.exception.JwtTokenExpiredException;
import com.yonyou.microservice.auth.client.jwt.ServiceAuthUtil;
import com.yonyou.microservice.gate.common.context.BaseContextHandler;
import com.yonyou.microservice.gate.common.msg.TokenErrorResponse;
import com.yonyou.microservice.gate.common.msg.TokenForbiddenResponse;
import com.yonyou.microservice.gate.common.msg.TokenKickOutResponse;
import com.yonyou.microservice.gate.common.vo.authority.IgnoreUriInfo;
import com.yonyou.microservice.gate.common.vo.authority.PermissionInfo;
import com.yonyou.microservice.gate.common.vo.log.LogInfo;
import com.yonyou.microservice.gate.common.vo.user.UserInfo;
import com.yonyou.microservice.gate.server.exception.UserTokenExpiredResponse;
import com.yonyou.microservice.gate.server.feign.IAuthService;
import com.yonyou.microservice.gate.server.feign.IIgnoreUriService;
import com.yonyou.microservice.gate.server.feign.ILogService;
import com.yonyou.microservice.gate.server.service.CacheService;
import com.yonyou.microservice.gate.server.utils.DbLog;

import lombok.extern.slf4j.Slf4j;

/**
 * gateway filter
 * filter type pre
 * 可以通过配置 gate.ignore.startWith 来忽略经过过滤器的请求
 *
 * @author joy
 * @create 2017-06-23 8:25
 */
@Component
@Slf4j
public class AdminAccessFilter extends ZuulFilter {
	private static final String USER_HEAD_ID="userId";
	private static final String USER_HEAD_NAME="userName";
	private static final String USER_HEAD_REMARK="remark";
	private static final String REPBODY_DEALER_NAME = "dealerName";
	private static final String REPBODY_DEALER_CODE = "dealerCode";
	private static final String REPBODY_TELPHONE = "telPhone";
	private static final String PERMISSION="permission";
	private static final String HTTP_GET="GET";
	private static final String LOG_OUT="autht/invalid";
	private static final String SPEC_URI_INFO="/admin/user/front/info,/admin/menu/all,/admin/user/front/menus";
	private static final String SPEC_URI_MENUS="admin/user/front/menus";
	private static Logger logger=Logger.getLogger(AdminAccessFilter.class);
    
    @Autowired
    private ILogService logService;

    @Value("${gate.ignore.startWith}")
    private String startWith;

    @Value("${zuul.prefix}")
    private String zuulPrefix;
    @Value("${gate.ignore.addHeader:false}")
    private boolean ignoreUrlAddHeader;
    @Autowired
    private ServiceAuthConfig serviceAuthConfig;

    @Autowired
    private UserAuthConfig userAuthConfig;

    @Autowired
    private ServiceAuthUtil serviceAuthUtil;
    
    @Autowired
    RequestMappingHandlerMapping mapping1;
    
    @Autowired
    IIgnoreUriService iIgnoreUriService;
    @Autowired
    CacheService cacheService;
    @Autowired
    IAuthService authService;
    
    private List<IgnoreUriInfo> startWithList;
    
    
    public AdminAccessFilter(){
    	logger.info("--AdminAccessFilter对象创建");
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    private void addHeader(RequestContext ctx, IJwtInfo user){
    	// 申请客户端密钥头
        ctx.addZuulRequestHeader(serviceAuthConfig.getTokenHeader(),serviceAuthUtil.getClientToken());
        ctx.addZuulRequestHeader(USER_HEAD_ID,user.getId());
        String name=URLEncoder.encode(user.getName());
        ctx.addZuulRequestHeader(USER_HEAD_NAME,name);
        String remark=URLEncoder.encode(user.getRemark());
        ctx.addZuulRequestHeader(USER_HEAD_REMARK,remark);
        ctx.addZuulRequestHeader(REPBODY_DEALER_CODE,user.getDealerCode());
        String dealerName=URLEncoder.encode(user.getDealerName());
        ctx.addZuulRequestHeader(REPBODY_DEALER_NAME,dealerName);
        ctx.addZuulRequestHeader(REPBODY_TELPHONE,user.getTelPhone());
        
        String tmp="";
        Map<String,String> map =user.getParam();
        for(Entry<String,String> entry:map.entrySet()){
            ctx.addZuulRequestHeader(entry.getKey(),entry.getValue());
            tmp=tmp+","+entry.getKey()+"="+entry.getValue();
        }
    	logger.info("--添加头信息,userid="+user.getId()+
    			",username="+user.getName()+
    			",encode(username)="+name+
    			",dealercode="+user.getDealerCode()+
    			",dealername="+user.getDealerName()+
    			",encode(dealername)="+dealerName+
    			",telphone="+user.getTelPhone()+
    			",kickOut="+user.getKickout()+
    			",remark="+remark+tmp);
        BaseContextHandler.remove();
    }
    private void dealIgnoreUrlRequest(HttpServletRequest request, RequestContext ctx){
    	IJwtInfo user=null;
        try {
        	//从JWT中解析出用户信息
            user = getJWTUser(request,ctx);
            if(user==null){
            	logger.info("--is ignoreUrl,user is null");
                return ;
            }
        } catch (Exception e) {
        	logger.error("--is ignore Url,parse jwt exception,"+e.getMessage());
            return ;
        }
    	logger.info("--enter ignoreUrl,add user header");
        addHeader(ctx, user);
    }
    
    @Override
    public Object run() {
    	logger.info("--进入网关");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
    	logger.info("--requestUri="+request.getRequestURI());
        final String requestUri = request.getRequestURI().substring(zuulPrefix.length());
        final String method = request.getMethod();
        BaseContextHandler.setToken(null);
        // 不进行拦截的地址
        if (isStartWith(requestUri)) {
        	logger.info("--忽略身份认证");
        	if(ignoreUrlAddHeader){
        		this.dealIgnoreUrlRequest( request,  ctx);
                return null;
        	}
            return null;
        }
        if(requestUri.contains(LOG_OUT)){
        	cleanSession(request);
        }
        IJwtInfo user=this.dealControledUrlRequest( request,  ctx);

        if(user!=null && !SPEC_URI_INFO.contains(requestUri)){
        	logger.info("--AdminAccessFilter.run(),开始uri访问权限检查");
            List<PermissionInfo> permissionInfos = cacheService.getAllPermissionInfo();
            // 判断资源是否启用权限约束
            Collection<PermissionInfo> result = getPermissionInfos(requestUri, method, permissionInfos);
            if(result.size()>0){
    			checkAllow(requestUri, method, ctx, user.getUniqueName());
            }
        }
      return null;
      
    }
    
    private IJwtInfo dealControledUrlRequest(HttpServletRequest request, RequestContext ctx){
        IJwtInfo user = null;
        try {
        	//从JWT中解析出用户信息
            user = getJWTUser(request,ctx);
            if(user==null){
                setFailedRequest(JSON.toJSONString(new TokenKickOutResponse("user was kicked out")),200);
            	logger.info("--user为空，退出网关");
                return null;
            }
        } catch (JwtTokenExpiredException e) {
        	//如果jwt中的用户信息获取失败，这块返回信息可能要改成统一的response格式
            setFailedRequest(JSON.toJSONString(new UserTokenExpiredResponse("Please login again")),200);
        	logger.error("--User token expired,"+e.getMessage());
            return null;
        } catch (Exception e) {
        	//如果jwt中的用户信息获取失败，这块返回信息可能要改成统一的response格式
            setFailedRequest(JSON.toJSONString(new TokenErrorResponse("Please login again")),200);
        	logger.error("--解析jwt出现异常,"+e.getMessage());
            return null;
        }
        addHeader(ctx, user);
        return user;
    }    

    /**
     * 获取目标权限资源
     * @param requestUri
     * @param method
     * @param serviceInfo
     * @return cache
     */
    private Collection<PermissionInfo> getPermissionInfos(final String requestUri, final String method, List<PermissionInfo> serviceInfo) {
        return Collections2.filter(serviceInfo, new Predicate<PermissionInfo>() {
                @Override
                public boolean apply(PermissionInfo permissionInfo) {
                    String url = permissionInfo.getUri();
                    String uri = url.replaceAll("\\{\\*\\}", "[a-zA-Z\\\\d]+");
                    String regEx = "^" + uri + "$";
                    boolean b1=Pattern.compile(regEx).matcher(requestUri).find();
                    boolean b2=requestUri.startsWith(url + "/");
                    boolean b3=method.equals(permissionInfo.getMethod());
                    return (b1 || b2)
                            && b3;
                }
            });
    }

    private void setCurrentUserInfoAndLog(RequestContext ctx, String username, PermissionInfo pm) {
        UserInfo info = cacheService.getUserByUsername(username);
        String host =  ClientUtil.getClientIp(ctx.getRequest());
        ctx.addZuulRequestHeader("userId", info.getId());
        try {
        	String name=URLEncoder.encode(info.getName(),"utf-8");
			ctx.addZuulRequestHeader("userName", name);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
        ctx.addZuulRequestHeader("userHost", ClientUtil.getClientIp(ctx.getRequest()));
        LogInfo logInfo = new LogInfo(pm.getMenu(),pm.getName(),pm.getUri(),new Date(),info.getId(),info.getName(),host);
        DbLog.getInstance().setLogService(logService).offerQueue(logInfo);
    }

    /**
     * 从header中取到JWT
     * 优先从header中取
     * 没有就从参数中取token参数
     * 
     * @param request
     * @param ctx
     * @return
     */
    private IJwtInfo getJWTUser(HttpServletRequest request,RequestContext ctx) throws Exception {
        String authToken = request.getHeader(userAuthConfig.getTokenHeader());
        logger.info("--Authorization:"+authToken);
        if(authToken==null || "".equals(authToken)){
        	throw new Exception("jwt is null");
        }
        if(StringUtils.isBlank(authToken)){
            authToken = request.getParameter("Authorization");
        }
        ctx.addZuulRequestHeader(userAuthConfig.getTokenHeader(),authToken);
        //将token放到threadlocal中
        BaseContextHandler.setToken(authToken);
//        JwtInfo info=authService.getUserInfo(authToken);
//        return info;
        return cacheService.getInfoFromToken(authToken);
    }

    /**
     * 读取权限
     * @param request
     * @param username
     * @return
     */
    private List<PermissionInfo> getPermissionInfos(HttpServletRequest request, String username) {
        List<PermissionInfo> permissionInfos;
//        if (request.getSession().getAttribute(PERMISSION) == null) {
            permissionInfos = cacheService.getPermissionByUsername(username);
//            request.getSession().setAttribute("permission", permissionInfos);
//        } else {
//            permissionInfos = (List<PermissionInfo>) request.getSession().getAttribute("permission");
//        }
        return permissionInfos;
    }

    /**
     * 权限校验
     * @param requestUri
     * @param method
     * @throws UnsupportedEncodingException
     */
    private boolean checkAllow(final String requestUri, final String method ,RequestContext ctx,String username) {
        log.debug("uri：" + requestUri + "----method：" + method);
        List<PermissionInfo> permissionInfos = getPermissionInfos(ctx.getRequest(), username) ;
        Collection<PermissionInfo> result = getPermissionInfos(requestUri, method, permissionInfos);
        if (result.size() <= 0) {
        	logger.info("--AdminAccessFilter.run(),访问拒绝,uri="+requestUri);
            setFailedRequest(JSON.toJSONString(new TokenForbiddenResponse("access was denied!")), 200);
        } else{
            PermissionInfo[] pms =  result.toArray(new PermissionInfo[]{});
            PermissionInfo pm = pms[0];
            //if(!HTTP_GET.equals(method)){
                setCurrentUserInfoAndLog(ctx, username, pm);
            //}
        }
        return true;
    }


    /**
     * URI是否以什么打头
     * @param requestUri
     * @return cache
     */
    public boolean isStartWith(String requestUri) {
        boolean flag = false;
        startWithList=cacheService.getIgnoreUris();
        for (IgnoreUriInfo s : startWithList) {
            if (requestUri.startsWith(s.getUri())) {
                return true;
            }
        }
        return flag;
    }
    /**
     * Reports an error message given a response body and code.
     *
     * @param body
     * @param code
     */
    private void setFailedRequest(String body, int code) {
        log.debug("Reporting error ({}): {}", code, body);
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.setResponseStatusCode(code);
        if (ctx.getResponseBody() == null) {
            ctx.setResponseBody(body);
            ctx.setSendZuulResponse(false);
//            throw new RuntimeException("Code: " + code + ", " + body); //optional
        }
    }
    private void cleanSession(HttpServletRequest request){
    	  Enumeration em = request.getSession().getAttributeNames();
    	  while(em.hasMoreElements()){
    	   request.getSession().removeAttribute(em.nextElement().toString());
    	  }
   }
}
