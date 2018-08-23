package com.yonyou.cloud.ops.mq.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.yonyou.cloud.ops.mq.entity.MqConsumer;
import com.yonyou.cloud.ops.mq.repository.MqConsumerRepository;

@Service
public class MqConsumerService{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MqConsumerRepository mqConsumerRepository;
	
	public void save(MqConsumer mqConsumer) throws Exception{
		
		if(Boolean.valueOf(mqConsumer.getSuccess())){
			mqConsumer.setSuccessTime(mqConsumer.getOccurTime());
		}else{
			mqConsumer.setFailTimes(mqConsumer.getFailTimes() + 1);
		}
		
		MqConsumer msgExample = new MqConsumer();
		msgExample.setMsgKey(mqConsumer.getMsgKey());
		msgExample.setConsumerId(mqConsumer.getConsumerId());
        Example<MqConsumer> example = Example.of(msgExample);
        Optional<MqConsumer> consumer = mqConsumerRepository.findOne(example);
//        MqConsumer consumer = mqConsumerRepository.findOne(example);
        
//		MqConsumer consumer = selectOne(MqOpsConstant.INDEX, "msgKey:" + mqConsumer.getMsgKey() + " AND " + "consumerId:" + mqConsumer.getConsumerId());
		if(!consumer.isPresent()){
			mqConsumerRepository.save(mqConsumer);
//			insert(MqOpsConstant.INDEX, mqConsumer);
		} else if(Boolean.valueOf(consumer.get().getSuccess())){
			logger.error("this message has been consumed==========>>>>>>msgkey:{}  consmerId{}", consumer.get().getMsgKey(), consumer.get().getConsumerId());
		}  else {
//			update(MqOpsConstant.INDEX, mqConsumer, consumer.getId());
		}
	}
}
