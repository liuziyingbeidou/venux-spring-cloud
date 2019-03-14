package com.venux.consumer.rabbitMQ.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import com.venux.consumer.rabbitMQ.service.ReceviceMsg;

@EnableBinding(value = {Sink.class})
public class ReceviceMsgImpl implements ReceviceMsg {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@StreamListener(Sink.INPUT)
	@Override
	public void receive(String payload) {
		logger.info("接收消息："+payload);
	}

}
