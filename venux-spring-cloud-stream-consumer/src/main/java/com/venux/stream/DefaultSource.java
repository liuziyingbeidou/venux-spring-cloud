package com.venux.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @Title: ZJS 
 * @ClassName: DefaultSource 
 * @Description: 默认输出流
 * @author liuzy
 * @date 2019年3月18日 下午2:36:34
 */
public interface DefaultSource {

	String OUTPUT_DEFAULT = "output-" + StreamConstant.DEFAULT_PROCESSOR;
	
	@Output(DefaultSource.OUTPUT_DEFAULT)
	MessageChannel outputDefault();
}
