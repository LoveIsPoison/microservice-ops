package com.yonyou.microservice.gate.server.exception;

import com.yonyou.cloud.common.beans.ResultBean;
import com.yonyou.microservice.gate.common.constant.RestCodeConstants;

public class UserTokenExpiredResponse extends ResultBean {
	
	private String errCode;
	
    public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public UserTokenExpiredResponse(String message) {
        super(998, message);
		this.errCode="998";
    }
}
