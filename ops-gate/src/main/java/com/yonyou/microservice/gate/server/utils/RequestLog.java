package com.yonyou.microservice.gate.server.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.alibaba.fastjson.JSONObject;
import com.yonyou.microservice.gate.common.vo.log.LogInfo;
import com.yonyou.microservice.gate.server.vo.UrlRequestVO;

import lombok.extern.slf4j.Slf4j;

/**
 * ${DESCRIPTION}
 *
 * @author joy
 * @create 2017-07-01 15:28
 */
@Slf4j
public class RequestLog extends Thread {
	/**
     * 测试索引
     */
    private static String indexName="gaterequest";

    /**
     * 类型
     */
    private String esType="external";
    private static RequestLog requestlog = null;
    private static BlockingQueue<UrlRequestVO> requestQueue = new LinkedBlockingQueue<UrlRequestVO>(1024);


    public static synchronized RequestLog getInstance() {
        if (requestlog == null) {
        	if(!ElasticsearchUtil.isIndexExist(indexName)) {
                ElasticsearchUtil.createIndex(indexName);
            }
        	requestlog = new RequestLog();
        }
        return requestlog;
    }

    private RequestLog() {
        super("CLogOracleWriterThread");
    }

    public void offerQueue(UrlRequestVO urlRequestVO) {
        try {
        	requestQueue.offer(urlRequestVO);
        } catch (Exception e) {
            log.error("请求日志写入失败", e);
        }
    }

    @Override
    public void run() {
    	// 缓冲队列
        List<UrlRequestVO> bufferedLogList = new ArrayList<UrlRequestVO>(); 
        while (true) {
            try {
                bufferedLogList.add(requestQueue.take());
                requestQueue.drainTo(bufferedLogList);
                if (bufferedLogList != null && bufferedLogList.size() > 0) {
                    // 写入日志
                    for(UrlRequestVO vo:bufferedLogList){
                        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(vo);
                        log.info("--send data to es,data="+jsonObject.toJSONString());
                        String id = ElasticsearchUtil.addData(jsonObject, indexName, esType);
                    	log.info("--send data to es,id="+id);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                // 防止缓冲队列填充数据出现异常时不断刷屏
                try {
                    Thread.sleep(1000);
                } catch (Exception eee) {
                }
            } finally {
                if (bufferedLogList != null && bufferedLogList.size() > 0) {
                    try {
                        bufferedLogList.clear();
                    } catch (Exception e) {
                    }
                }
            }
        }
    }
}