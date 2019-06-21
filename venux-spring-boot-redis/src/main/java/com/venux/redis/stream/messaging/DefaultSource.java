package com.venux.redis.stream.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.xnio.channels.MessageChannel;

import com.venux.redis.stream.StreamConstant;

/**
 * @ClassName: DefaultSource 
 * @Description: ZRedis中间件默认输出流
 * @author liuzy
 * @date 2019年3月18日 下午2:36:34
 */
@Deprecated
public interface DefaultSource {

	String OUTPUT_DEFAULT = "output-" + StreamConstant.DEFAULT_PROCESSOR;
	
	@Output(DefaultSource.OUTPUT_DEFAULT)
	MessageChannel outputDefault();
}
