package com.venux.redis.stream.processor.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

import com.alibaba.fastjson.JSON;
import com.venux.redis.dto.param.PubSubApiParam;
import com.venux.redis.service.Receiver;
import com.venux.redis.stream.StreamConstant;
import com.venux.redis.stream.messaging.DefaultSource;
import com.venux.redis.stream.processor.DefaultHandler;

/** 
 * @Title: ZJS 
 * @ClassName: DefaultHandlerImpl 
 * @Description: 输入输出流处理
 * @author liuzy
 * @date 2019年3月18日 下午3:37:14  
 */
@Deprecated
//@EnableBinding(value = {DefaultProcessor.class})
public class DefaultHandlerImpl implements DefaultHandler {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier(value=DefaultSource.OUTPUT_DEFAULT)
    private MessageChannel output;
	@Autowired
	private Receiver receiver;
	
	@Override
	public String getChannel() {
		return StreamConstant.DEFAULT_PROCESSOR;
	}

	@Override
	public boolean publishMessage(String message) {
		logger.info("发布消息: " + message);
		return output.send(MessageBuilder.withPayload(message).build());
	}

	//@StreamListener(DefaultProcessor.INPUT_DEFAULT)
	@Override
	public void receiveMessage(String message) throws Exception {
		logger.info("{} 接收到消息: {}", this.getChannel(),message);
		if(message == null){
			return ;
		}
		PubSubApiParam<String, String> pubSubApiParam = JSON.parseObject(message, PubSubApiParam.class);
		receiver.receiveMessage(pubSubApiParam);
	}
	
}
