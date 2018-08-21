package com.youyou.microservice.auth.server.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.yonyou.cloud.common.jwt.IJwtInfo;
import com.yonyou.cloud.common.jwt.JwtInfo;
import com.yonyou.microservice.gate.common.constant.CommonConstants;
import com.yonyou.microservice.gate.common.vo.authority.PermissionInfo;
import com.yonyou.microservice.gate.common.vo.user.UserInfo;
import com.youyou.microservice.auth.server.feign.IUserService;
import com.youyou.microservice.auth.server.service.AuthService;
import com.youyou.microservice.auth.server.service.AuthUtilService;
import com.youyou.microservice.auth.server.util.user.JwtTokenUtil;
import com.youyou.microservice.auth.server.vo.FrontUser;


/**
 *  @author joy
 */
@Service
public class AuthServiceImpl implements AuthService {
	private static final String TEST_CAPTCHA="8888";
	private static Logger logger=LoggerFactory.getLogger(AuthServiceImpl.class);

    private JwtTokenUtil jwtTokenUtil;
    private IUserService userService;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    private AuthUtilService authUtilService;
    @Autowired
    private RedisTemplate redisTemplate;
	@Value("${redis.expire}")
    private int expire;
    
    @Autowired
    public AuthServiceImpl(
            JwtTokenUtil jwtTokenUtil,
            IUserService userService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @Override
    public String login(String username, String password) throws Exception {
        UserInfo info = userService.getUserByUsername(username);
        String token = "";
        if (encoder.matches(password, info.getPassword())) {
            token = jwtTokenUtil.generateToken(new JwtInfo(info.getUsername(), info.getId() + "", info.getName(),"","",info.getTelPhone(),false,null,""));
        }
        return token;
    }
    @Override
    public String loginPhone(String phone, String captcha) throws Exception{
    	logger.info("--AuthServiceImpl,phone="+phone+",captcha="+captcha);
        UserInfo info = userService.getUserByPhone(phone);
        String token = "";
        if (info!=null&& TEST_CAPTCHA.equals(captcha)) {
            token = jwtTokenUtil.generateToken(new JwtInfo(info.getUsername(), info.getId() + "", info.getName(),"","","",false,null,""));
        }
        return token;
    }

    @Override
    public void validate(String token) throws Exception {
        jwtTokenUtil.getInfoFromToken(token);
    }

    @Override
    public FrontUser getUserInfo(String token) throws Exception {
        String username = jwtTokenUtil.getInfoFromToken(token).getUniqueName();
        if (username == null) {
            return null;
        }
        UserInfo user = userService.getUserByUsername(username);
        FrontUser frontUser = new FrontUser();
        BeanUtils.copyProperties(user, frontUser);
        List<PermissionInfo> permissionInfos = userService.getPermissionByUsername(username);
        Stream<PermissionInfo> menus = permissionInfos.parallelStream().filter((permission) -> {
            return permission.getType().equals(CommonConstants.RESOURCE_TYPE_MENU);
        });
        frontUser.setMenus(menus.collect(Collectors.toList()));
        Stream<PermissionInfo> elements = permissionInfos.parallelStream().filter((permission) -> {
            return !permission.getType().equals(CommonConstants.RESOURCE_TYPE_MENU);
        });
        frontUser.setElements(elements.collect(Collectors.toList()));
        return frontUser;
    }

    @Override
    public Boolean invalid(String token) {
        // TODO: 2017/9/11 注销token
        return null;
    }

    @Override
    public String refresh(String oldToken) {
        // TODO: 2017/9/11 刷新token
        return null;
    }
    

    @Override
    public IJwtInfo getUserInfoByJwt(String jwtToken) throws Exception {
    	logger.info("--getInfoFromToken from jwtUtil,"+jwtToken);
    	IJwtInfo result=null;
    	try{
        	//根据jwt取jwtInfo
        	Object jwtInfo=redisTemplate.opsForValue().get(jwtToken);
        	if(jwtInfo!=null){
        		//取得登录账号
        		String loginName=((IJwtInfo)jwtInfo).getUniqueName();
        		logger.info("--jwt,loginName="+loginName);
        		//根据账号取得有效的jwt
            	Object jwt=redisTemplate.opsForValue().get(loginName);
            	if(jwt!=null){
            		//当前jwt与有效jwt是否匹配
            		boolean r=jwtToken.equals(jwt);
            		boolean kickOut=((IJwtInfo)jwtInfo).getKickout();
            		//当前jwt与有效jwt匹配，或者不互踢
            		if(r||!kickOut){
            			//设置jwt有效期
            			redisTemplate.expire(jwtToken, expire, TimeUnit.SECONDS); 
            			//设置loginName有效期
            			redisTemplate.expire(loginName, expire, TimeUnit.SECONDS); 
            			result=(IJwtInfo)jwtInfo;
                		logger.info("--延长jwt和登录账号有效期"+loginName);
            		}else{
                		logger.info("--jwt已经被踢掉,loginName="+loginName+",jwt="+jwtToken+",currentJwt="+jwt);
            		}
            	}else{//根据账号没有找到有效的jwt，则认为当前这个jwt即为有效的jwt
            		logger.info("--根据登录账号没有找到有效的jwt");
            		//缓存登录账号有效的jwt
            		redisTemplate.opsForValue().set(loginName, jwtToken, expire, TimeUnit.SECONDS); 
        			//设置jwt有效期
        			redisTemplate.expire(jwtToken, expire, TimeUnit.SECONDS); 
        			result=(IJwtInfo)jwtInfo;
            		logger.info("--设置账号有效的jwt,loginName="+loginName+",jwt="+jwtToken);
            	}
        	}else{//如果jwt在缓存找不到，则认为这个jwt是新的有效jwt
        		logger.info("--没有jwt缓存信息");
        		IJwtInfo info=authUtilService.getInfoFromToken(jwtToken);
        		//根据jwt缓存用户信息
        		redisTemplate.opsForValue().set(jwtToken, info, expire, TimeUnit.SECONDS); 
        		logger.info("--缓存信息jwt,"+jwtToken);
        		String loginName=info.getUniqueName();
        		//根据用户账号缓存有效的jwt，踢掉其它的jwt
        		redisTemplate.opsForValue().set(loginName, jwtToken, expire, TimeUnit.SECONDS); 
        		logger.info("--用登录账号缓存有效的jwt,loginName="+loginName);
        		result= info;
        	}
    	}catch(Exception e){
    		logger.info("--redis发生异常，从jwt取信息");
    		IJwtInfo info=authUtilService.getInfoFromToken(jwtToken);
    		//根据jwt缓存用户信息
    		redisTemplate.opsForValue().set(jwtToken, info, expire, TimeUnit.SECONDS); 
    		result= info;
    	}
    	return result;
    }
}
