package com.venux.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @Title: ZJS 
 * @ClassName: DefaultSink 
 * @Description: 默认输入流
 * @author liuzy
 * @date 2019年3月18日 下午2:39:27
 */
public interface DefaultSink {

	String INPUT_DEFAULT = "input-" + StreamConstant.DEFAULT_PROCESSOR;
	
	@Input(DefaultSink.INPUT_DEFAULT)
	SubscribableChannel inputDefault();
}
