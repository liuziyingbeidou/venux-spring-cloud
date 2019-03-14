package com.venux.provider.rabbitMQ.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.GenericMessage;

import com.venux.provider.rabbitMQ.service.SendMsg;

@EnableBinding(value={Source.class})
public class SendMsgImpl implements SendMsg {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Integer i=0;
	
	@Bean
	@InboundChannelAdapter(value = Source.OUTPUT, poller = @Poller(fixedDelay = "2000", maxMessagesPerPoll = "1") )
	@Override
	public MessageSource<Integer> timerMessageSource() {
		logger.info("发送消息："+i++);
		return () -> new GenericMessage<>(i++);
	}

}
