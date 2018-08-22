package com.yonyou.cloud.ops.task.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {

	/**
	 * 
	 * @param TZtime
	 * @return
	 */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        System.out.println(res);
        return res;
    } 
}
