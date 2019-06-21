package com.venux.redis.stream.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

import com.venux.redis.stream.StreamConstant;

/**
 * @ClassName: DefaultSink 
 * @Description: ZRedis中间件默认输入流
 * @author liuzy
 * @date 2019年3月18日 下午2:39:27
 */
@Deprecated
public interface DefaultSink {

	String INPUT_DEFAULT = "input-" + StreamConstant.DEFAULT_PROCESSOR;
	
	@Input(DefaultSink.INPUT_DEFAULT)
	SubscribableChannel inputDefault();
}
