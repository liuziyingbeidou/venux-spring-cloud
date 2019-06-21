package com.venux.redis.service;

import com.venux.redis.dto.param.PubSubApiParam;

/** 
 * @Title: ZJS 
 * @ClassName: Receiver 
 * @Description: 消息接收处理类
 * @author liuzy
 * @date 2019年3月18日 下午5:39:08  
 */
public interface Receiver {

	public void receiveMessage(PubSubApiParam<String, String> pubSubApiParam) throws Exception;
}
