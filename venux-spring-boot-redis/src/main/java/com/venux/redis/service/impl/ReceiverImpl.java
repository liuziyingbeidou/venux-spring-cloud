package com.venux.redis.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.venux.redis.dto.ConsumerDto;
import com.venux.redis.dto.param.PubSubApiParam;
import com.venux.redis.service.ConsumerService;
import com.venux.redis.service.Receiver;
import com.venux.redis.service.strategy.CommunicationMode;

/** 
 * @ClassName: ReceiverImpl 
 * @Description: 消息接收处理类
 * @author liuzy
 * @date 2019年3月18日 下午5:39:53  
 */
@Service
@SuppressWarnings("unchecked")
public class ReceiverImpl implements Receiver {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ConsumerService consumerService;
	
	private Map<Integer,CommunicationMode> mapMode = Maps.newHashMap();
	
	/**
	 * 构造注入
	 */
	public ReceiverImpl(List<CommunicationMode> communicationModes){
		for (CommunicationMode mode : communicationModes) {
			mapMode.put(mode.getCommunicationMode(), mode);
		}
	}
	
    /**
     * 接收消息
     * @param message
     * @throws Exception
     * @return void
     */
    public void receiveMessage(PubSubApiParam<String, String> pubSubApiParam) throws Exception{
		 //根据key获取消费者列表
  	  	List<ConsumerDto> comsumers = consumerService.getComsumer(pubSubApiParam.getKey());
  	  	for (ConsumerDto consumer : comsumers) {
			  CommunicationMode communicationMode = mapMode.get(consumer.getCommunicationMode());
			  communicationMode.doCommunication(consumer);
  	  	}
    }

}
