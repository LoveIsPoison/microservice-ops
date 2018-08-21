package com.yonyou.microservice.gate.server.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.yonyou.cloud.common.jwt.IJwtInfo;
import com.yonyou.microservice.auth.client.jwt.UserAuthUtil;
import com.yonyou.microservice.gate.common.vo.authority.IgnoreUriInfo;
import com.yonyou.microservice.gate.common.vo.authority.PermissionInfo;
import com.yonyou.microservice.gate.common.vo.user.UserInfo;
import com.yonyou.microservice.gate.server.feign.IIgnoreUriService;
import com.yonyou.microservice.gate.server.feign.IUserService;
import com.yonyou.microservice.gate.server.filter.AdminAccessFilter;

/**
 * 
 * @author joy
 * @create 2018-01-9 缓存服务类，对特定数据做缓存
 */
@Service
public class CacheService {
	private Logger logger = LoggerFactory.getLogger(AdminAccessFilter.class);
	@Autowired
	IIgnoreUriService iIgnoreUriService;
	@Autowired
	private IUserService userService;
	@Autowired
	private UserAuthUtil userAuthUtil;
	@Autowired
	private RedisTemplate redisTemplate;
	@Value("${redis.expire}")
	private int expire;

	@Cacheable(value = "gate", key = "'gate.ignoreuri'")
	public List<IgnoreUriInfo> getIgnoreUris() {
		logger.info("--getIgnoreUris from db");
		return this.iIgnoreUriService.getIgnoreUris();
	}

	/**
	 * 获取所有授权信息
	 * 
	 * @return cache
	 */
	@Cacheable(value = "gate", key = "'gate.allpermission'")
	public List<PermissionInfo> getAllPermissionInfo() {
		logger.info("--getAllPermissionInfo from db");
		return userService.getAllPermissionInfo();
	}

	/**
	 * 根据jwt解析用户信息
	 * 
	 * @param authToken
	 * @return cache
	 * @throws Exception
	 */
	public IJwtInfo getInfoFromToken(String authToken) throws Exception {
		logger.info("--getInfoFromToken from jwtUtil," + authToken);
		IJwtInfo result = null;
		try {
			// 根据jwt取jwtInfo
			Object jwtInfo = redisTemplate.opsForValue().get(authToken);
			if (jwtInfo != null) {
				// 取得登录账号
				String loginName = ((IJwtInfo) jwtInfo).getUniqueName();
				logger.info("--jwt,loginName=" + loginName);
				// 根据账号取得有效的jwt
				Object jwt = redisTemplate.opsForValue().get(loginName);
				if (jwt != null) {
					// 当前jwt与有效jwt是否匹配
					boolean r = authToken.equals(jwt);
					boolean kickOut = ((IJwtInfo) jwtInfo).getKickout();
					logger.info("--curjwt=" + authToken);
					logger.info("--redis jwt=" + jwt);
					logger.info("--curjwt == redis jwt=" + r);
					logger.info("--kickOut=" + kickOut);
					// 当前jwt与有效jwt匹配，或者不互踢
					if (r || !kickOut) {
						// 设置jwt有效期
						redisTemplate.expire(authToken, expire, TimeUnit.SECONDS);
						// 设置loginName有效期
						redisTemplate.expire(loginName, expire, TimeUnit.SECONDS);
						result = (IJwtInfo) jwtInfo;
						logger.info("--延长jwt和登录账号有效期" + loginName);
					} else {
						logger.info("--jwt已经被踢掉,loginName=" + loginName + ",jwt=" + authToken + ",currentJwt=" + jwt);
					}
				} else {// 根据账号没有找到有效的jwt，则认为当前这个jwt即为有效的jwt
					logger.info("--根据登录账号没有找到有效的jwt");
					// 缓存登录账号有效的jwt
					redisTemplate.opsForValue().set(loginName, authToken, expire, TimeUnit.SECONDS);
					// 设置jwt有效期
					redisTemplate.expire(authToken, expire, TimeUnit.SECONDS);
					result = (IJwtInfo) jwtInfo;
					logger.info("--设置账号有效的jwt,loginName=" + loginName + ",jwt=" + authToken);
				}
			} else {// 如果jwt在缓存找不到，则认为这个jwt是新的有效jwt
				logger.info("--没有jwt缓存信息");
				IJwtInfo info = userAuthUtil.getInfoFromToken(authToken);
				// 根据jwt缓存用户信息
				redisTemplate.opsForValue().set(authToken, info, expire, TimeUnit.SECONDS);
				logger.info("--缓存信息jwt," + authToken);
				String loginName = info.getUniqueName();
				// 根据用户账号缓存有效的jwt，踢掉其它的jwt
				redisTemplate.opsForValue().set(loginName, authToken, expire, TimeUnit.SECONDS);
				logger.info("--用登录账号缓存有效的jwt,loginName=" + loginName);
				result = info;
			}
		} catch (Exception e) {
			logger.error("--redis发生异常，从jwt取信息", e);
			IJwtInfo info = userAuthUtil.getInfoFromToken(authToken);
			try {
				// 根据jwt缓存用户信息
				redisTemplate.opsForValue().set(authToken, info, expire, TimeUnit.SECONDS);
			} catch (Exception f) {
				logger.error("--设置redis缓存发生异常", e);
			}
			result = info;
		}
		return result;
		// return null;
	}

	@Cacheable(value = "gate", key = "'gate.permission.user.'+#username")
	public List<PermissionInfo> getPermissionByUsername(String username) {
		logger.info("--getPermissionByUsername from db");
		return userService.getPermissionByUsername(username);
	}

	@Cacheable(value = "gate", key = "'gate.userInfo.'+#username")
	public UserInfo getUserByUsername(String username) {
		logger.info("--getUserInfoByUsername from db,username=" + username);
		return userService.getUserByUsername(username);
	}
}
