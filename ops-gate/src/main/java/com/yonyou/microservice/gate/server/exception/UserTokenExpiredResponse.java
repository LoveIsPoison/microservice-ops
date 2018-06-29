package com.yonyou.microservice.gate.server.exception;

import com.yonyou.cloud.common.beans.ResultBean;
import com.yonyou.microservice.gate.common.constant.RestCodeConstants;

public class UserTokenExpiredResponse extends ResultBean {
    public UserTokenExpiredResponse(String message) {
        super(998, message);
    }
}
