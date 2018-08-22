package com.yonyou.cloud.ops.alert.ops.alert;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.yonyou.cloud.ops.alert.ops.alert.entity.UserInfo;

public class cc {
	public static void main(String[] args) {
		List<UserInfo> userlist = new ArrayList<>();
		UserInfo u=new UserInfo();
		u.setEmail("daniell_hu@163.com");
		userlist.add(u);
		
		UserInfo u1=new UserInfo();
		u1.setEmail("daniell2012@163.com");
		userlist.add(u1);
		StringBuffer toAddressList = new StringBuffer();
		int i=userlist.size();
		while (i>0) {
			--i;
			toAddressList.append("<"+userlist.get(i).getEmail()+">;");
			
		}
		
		
		toAddressList.append(StringUtils.isEmpty(StringUtils.join(userlist, ",")) ? "" : "," + StringUtils.join(userlist, ","));
		toAddressList.toString(); 
	}
}
