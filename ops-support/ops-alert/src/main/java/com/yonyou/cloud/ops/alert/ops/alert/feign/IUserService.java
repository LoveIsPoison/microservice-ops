package com.yonyou.cloud.ops.alert.ops.alert.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yonyou.cloud.common.beans.RestResultResponse;
import com.yonyou.cloud.ops.alert.ops.alert.domain.dto.GroupUsers;

/**
 * 
 * @author daniell
 *
 */
@FeignClient(value = "ops-admin",configuration = {})
public interface IUserService {

	/** 获取用户组下的用户 
	 * 
	 * @param id
	 * @return
	 */
	  @RequestMapping(value = "api/{id}/group", method = RequestMethod.GET)
	  public RestResultResponse<GroupUsers> getUsers(@PathVariable("id") int id);
}
